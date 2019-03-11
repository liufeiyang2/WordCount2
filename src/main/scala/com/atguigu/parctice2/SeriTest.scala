package com.atguigu.parctice2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SeriTest {
  def main(args: Array[String]): Unit = {
    //初始化配置信息
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wc")
    val sc = new SparkContext(sparkConf)

    //创建RDD
    val rdd: RDD[String] = sc.parallelize(Array("hadoop", "spark", "hive", "atguigu"))

    //创建一个search对象
    val search = new Search("h")

    //运用过滤函数打印结果
    val match1: RDD[String] = search.getMatch1(rdd)
    match1.collect().foreach(println)

    val match2: RDD[String] = search.getMatch2(rdd)
    match2.foreach(println)

    sc.stop()
  }
}

//需要将Search对象序列化后传递到Executor端
class Search(query: String) extends Serializable {


  def isMatch(s: String): Boolean = {
    s.contains(query)

  }

  def getMatch1(rdd: RDD[String]) = {

    rdd.filter(isMatch)
  }

  def getMatch2(rdd: RDD[String]) = {
    //    val query_ =this.query
    //    rdd.filter(x=>x.contains(query_))
    rdd.filter(x => x.contains(query))
  }

}
