package com.atguigu.parctice4

import java.util

import org.apache.spark.rdd.RDD
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

object TestAccumulator {

  def main(args: Array[String]): Unit = {

    //使用自定义累加器查找错误单词
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("TestAccumulator")
    val sc = new SparkContext(conf)

    val rdd: RDD[String] = sc.makeRDD(Array("hello", "String", "Hadoop", "hadooop"))

    //声明累加器
    val accumulator = new BlackListAccumulator

    //注册到Driver
    sc.register(accumulator, "blackList")

    rdd.foreach(w => {

      accumulator.add(w)
    })

    //获取累加器的值
    println(accumulator.value)
  }
}


class BlackListAccumulator extends AccumulatorV2[String, util.ArrayList[String]] {
  val blackList = new util.ArrayList[String]

  //是否初始化
  override def isZero: Boolean = {
    blackList.isEmpty
  }

  //复制当前累加器传递给Executor
  override def copy(): AccumulatorV2[String, util.ArrayList[String]] = {
    val acc = new BlackListAccumulator()
    acc
  }

  //累加器清空
  override def reset(): Unit = {
    blackList.clear()

  }

  //累加数据
  override def add(v: String): Unit = {
    if (v.contains("h")) {
      blackList.add(v)
    }

  }
    //合并数据
  override def merge(other: AccumulatorV2[String, util.ArrayList[String]]): Unit = {
    blackList.addAll(other.value)

  }
    //获取累加器的值
  override def value: util.ArrayList[String] = blackList
}
