package com.atguigu.parctice3

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, Partitioner, SparkConf, SparkContext}


object TestPartitoner {
  def main(args: Array[String]): Unit = {
    //初始化配置信息
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[(String, Int)] = sc.makeRDD(Array(("a", 1), ("b", 2), ("c", 3), ("d", 4)), 4)
    val parRDD: RDD[(String, Int)] = rdd.partitionBy(new CustomPartitioner(4))
    parRDD.mapPartitionsWithIndex((index, items) => {
      items.map((index, _))


    }).foreach(println)

    sc.stop()
  }
}

class CustomPartitioner(partitions: Int) extends Partitioner {
  require(partitions >= 0, s"Number of partitions ($partitions) cannot be negative.")

  //获取分区数
  def numPartitions: Int = partitions

  //获取分区号
  def getPartition(key: Any): Int = 1

  override def equals(other: Any): Boolean = other match {
    case h: HashPartitioner =>
      h.numPartitions == numPartitions
    case _ =>
      false
  }

  override def hashCode: Int = numPartitions
}