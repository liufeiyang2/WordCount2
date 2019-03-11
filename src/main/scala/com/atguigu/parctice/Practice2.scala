package com.atguigu.parctice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Practice2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Practice")
    //创建spark上下文对象
    val sc: SparkContext = new SparkContext(conf)

    val line: RDD[String] = sc.textFile("input/word.log",3)
    line.saveAsTextFile("output")

    sc.stop()
  }
}
