package com.atguigu.parctice3

import java.sql.{Connection, DriverManager}

import org.apache.spark.rdd.JdbcRDD
import org.apache.spark.{SparkConf, SparkContext, rdd}

object MysqlRDD {
  def main(args: Array[String]): Unit = {
    //创建spark配置信息
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("JdbcRDD")
    //创建SparkContext
    val sc = new SparkContext(sparkConf)

    //定义连接mysql的参数
    val driver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://hadoop102:3306/company"
    val userName = "root"
    val passWd = "atguigu"

    //创建JdbcRDD
/*    val rdd = new JdbcRDD(
      sc,
      () => {
      Class.forName(driver)
      DriverManager.getConnection(url, userName, passWd)
    },
      "select * from `staff` where `id` >= ? and `id` <= ? ;",
      1,
      10,
      1,

      //回调函数
      r => (r.getInt(1), r.getString(2),r.getString(3))

    )
    //打印最后结果
    println(rdd.count())
    rdd.foreach(println)*/

   /* sc.makeRDD(Array((1,"lisi","FeMale"),(2,"wangwu","FeMale"),(3,"zhaoliu","Male"))).foreachPartition(
      //JDBC
      //Connection
      val connection: Connection = DriverManager.getConnection(url, userName, passWd)

    )*/
    sc.stop()
  }
}
