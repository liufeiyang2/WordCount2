package streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object wordCount {
  def main(args: Array[String]): Unit = {
    val streamingConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wordCount")
    //创建流式计算上下文对象
    val ssc: StreamingContext = new StreamingContext(streamingConf,Seconds(5))
    //指定端口采集数据，封装为DStream
    val soketDStream: ReceiverInputDStream[String] = ssc.socketTextStream("hadoop102",9999)

    //分解一行数据为一个单词
    val wordDS: DStream[String] = soketDStream.flatMap(_.split(" "))

    //统计方便，将单词转换结构，变为元组
    val tupleDs: DStream[(String, Int)] = wordDS.map((_,1))

    //聚合统计结果
    val resultDS: DStream[(String, Int)] = tupleDs.reduceByKey(_+_)


    resultDS.print()
    //流式处理
    ssc.start()

    //等待数据
    ssc.awaitTermination()

  }


}
