package com.atguigu.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.expressions.Aggregator

object TestSparkSQL_ClassUDAF {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL_ClassUDAF")

    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()

    import spark.implicits._

    //构建UDAF函数
    val ageAvgCl = new AgeAvgClassUDAF
    val ageAvgColumn: TypedColumn[User, Double] = ageAvgCl.toColumn.name("avgAge")

    val dataFrame: DataFrame = spark.read.json("input/a.json")
    val userDs: Dataset[User] = dataFrame.as[User]

    //查询数据
    userDs.select(ageAvgColumn).show()
    spark.close()
  }
}

case class User(name: String, age: Long)

case class AvgBuffer(var sum: Long, var count: Long)

/*
* 强类型的聚合函数
*
* */
class AgeAvgClassUDAF extends Aggregator[User, AvgBuffer, Double] {
  //初始化缓冲对象
  override def zero: AvgBuffer = AvgBuffer(0L, 0L)

  //同一个节点缓冲更新
  override def reduce(b: AvgBuffer, a: User): AvgBuffer = {
    b.sum = b.sum + a.age
    b.count = b.count + 1
    b
  }

  override def merge(b1: AvgBuffer, b2: AvgBuffer): AvgBuffer = {
    b1.sum = b1.sum + b2.sum
    b1.count = b1.count + b2.count
    b1
  }

  override def finish(reduction: AvgBuffer): Double = {
    reduction.sum.toDouble / reduction.count
  }

  override def bufferEncoder: Encoder[AvgBuffer] = Encoders.product

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}
