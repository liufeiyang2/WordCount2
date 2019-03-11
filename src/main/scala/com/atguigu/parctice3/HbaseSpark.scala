package com.atguigu.parctice3

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration}
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapred.JobConf
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object HbaseSpark {
  def main(args: Array[String]): Unit = {

    //创建spark配置信息
    val hBaseAppConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("HBaseApp")
    //创建SparkContext
    val sc = new SparkContext(hBaseAppConf)

    //构建HBase的配置信息
    val configuration: Configuration = HBaseConfiguration.create()
   // configuration.set("hbase.zookeeper.quorum", "hadoop102,hadoop103,hadoop104")

    //获取表名
   // configuration.set(TableInputFormat.INPUT_TABLE, "student")

    //从HBase读取数据形成RDD
//    val hbaseRDD: RDD[(ImmutableBytesWritable, Result)] = sc.newAPIHadoopRDD(
//      configuration,
//      classOf[TableInputFormat],
//      classOf[ImmutableBytesWritable],
//      classOf[Result]
//    )
//    val count: Long = hbaseRDD.count()
//    println(count)

    //对hbaseRDD进行处理
//    hbaseRDD.foreach {
//      case (_, result) =>
//        val key: String = Bytes.toString(result.getRow)
//        val name: String = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("name")))
//        val color: String = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("color")))
//
//        println("RowKey:" + key + ",Name:" + name + ",Color:" + color)
//
//    }
    //读取数据
//    hbaseRDD.foreach(t=>{
//      val rowkey=t._1
//      val row =t._2
//      row.rawCells().foreach(cell=>{
//        println(Bytes.toString(CellUtil.cloneValue(cell)))
//      })
//
//
//    })

    //写数据
      val rdd: RDD[(String, String, String)] = sc.makeRDD(Array(("1003","wangwu","male")))
    //ImmutableBytesWritable,Put

    val hbaseRDD: RDD[(ImmutableBytesWritable, Put)] = rdd.map(t => {
      val rowkey = Bytes.toBytes(t._1)
      val put = new Put(rowkey)
      put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(t._2))
      put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("male"), Bytes.toBytes(t._3))
      (new ImmutableBytesWritable(rowkey), put)

    })

    val cfg = new JobConf(configuration)
    cfg.setOutputFormat(classOf[TableOutputFormat])
    //设置表名
    cfg.set(TableOutputFormat.OUTPUT_TABLE,"student")

    hbaseRDD.saveAsHadoopDataset(cfg)
    sc.stop()

  }

}
