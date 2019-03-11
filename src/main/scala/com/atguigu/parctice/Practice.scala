package com.atguigu.parctice

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}





object Practice {
  def main(args: Array[String]): Unit = {
    //准备spark配置信息
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Practice")
    //创建spark上下文对象
    val sc: SparkContext = new SparkContext(conf)

    //统计出每一个省份广告被点击的数量次数TOP3
    //读取广告点击数据文件
    val line: RDD[String] = sc.textFile("input/agent.log")
    //将数据转换为特定的格式((省份，广告)，数量)
    //flatMap算子对一行数据进行扁平化操作，也就是将一行数据不作为整体
    //map可以将一行数据当成一个整体
    val mapRDD: RDD[((String, String), Int)] = line.map(line => {
      val datas: Array[String] = line.split(" ")
      val province: String = datas(1)
      val advert: String = datas(4)
      ((province, advert), 1)
    })

    //统计同一个省份中广告点击的数量（（省份，广告），点击次数）
    val reduceRDD: RDD[((String, String), Int)] = mapRDD.reduceByKey(_+_)

    //将数据结构进行转换，（省份，（广告，点击次数））
    val pToASRDD: RDD[(String, (String, Int))] = reduceRDD.map{case (pa:(String,String),sumcount:Int)=>(pa._1,(pa._2,sumcount))}

    //同一个省份中，按照点击次数对广告进行排序
    val groupRDD: RDD[(String, Iterable[(String, Int)])] = pToASRDD.groupByKey()

    val resultRDD: RDD[(String, List[(String, Int)])] = groupRDD.mapValues(v => {
      //排完序后取前三个
      v.toList.sortWith((t1, t2) => {
        t1._2 > t2._2
      }).take(3)
    })

    //取前三个
    //执行作业
   resultRDD.foreach(t=>{
     t._2.foreach(t1=>{
       println(t._1+","+t1._1+","+t1._2)
     })

   })
    //关闭资源
    sc.stop()
  }
}
