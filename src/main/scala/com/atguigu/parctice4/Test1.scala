package com.atguigu.parctice4

import org.apache.spark.rdd.RDD
import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object Test1 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setAppName("Test1").setMaster("local[*]")

    val sc = new SparkContext(conf)

    val rdd: RDD[Int] = sc.makeRDD(1 to 5)

    //rdd.reduce(_+_)
 //共享变量传递Executor执行，但spark无法将变量值传回Driver
    //采用特殊结构，累加器
    //var sum=0

    //告诉spark， sum这个变量要返回
  val sum: LongAccumulator = sc.longAccumulator("sum")
    rdd.foreach( x=>{
//      sum +=x
//      println("inner sum="+sum)
      sum.add(x)
    })
 println("sum="+sum.value)
  }
}
