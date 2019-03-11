package com.atguigu.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, LongType, StructType}

object TestSparkSQL_UDAF {

  //求平均值
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setAppName("SparkSQL").setMaster("local[*]")
    val spark: SparkSession = SparkSession.builder().config(sparkConf).getOrCreate()


    //增加隐式转换
    import spark.implicits._

    val ageAvgUDAF = new AgeAvgUDAF

    //注册函数
    spark.udf.register("ageAvg", ageAvgUDAF)

    //使用UDAF函数
    val dataFrame: DataFrame = spark.read.json("input/a.json")
    dataFrame.createOrReplaceTempView("user")

    spark.sql("select ageAvg(age) from user").show()
    // 释放资源
    spark.stop()

  }
}

//自定义年龄平均值的聚合函数
//1.继承抽象类UserDefinedAggregateFunction
//2.重写方法
class AgeAvgUDAF extends UserDefinedAggregateFunction {
  /*
  * 聚合函数输入的数据结构
  * */
  override def inputSchema: StructType = {
    new StructType().add("age", LongType)
  }

  /*
  *聚合函数处理逻辑（缓冲）的数据结构
  * */
  override def bufferSchema: StructType = {
    new StructType().add("sum", LongType).add("count", LongType)
  }

  override def dataType: DataType = DoubleType

  //聚合函数的稳定性
  override def deterministic: Boolean = true

  //缓存的初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }
  //同一个节点的缓冲更新操作
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0)=buffer.getLong(0)+input.getLong(0)
    buffer(1)=buffer.getLong(1)+1
  }

  /**
    * 不同节点之间的缓存合并
    * @param buffer1
    * @param buffer2
    */
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }
  /**
    * 计算结果
    * @param buffer
    * @return
    */
  override def evaluate(buffer: Row): Any = {
    buffer.getLong(0).toDouble / buffer.getLong(1)
  }
}
