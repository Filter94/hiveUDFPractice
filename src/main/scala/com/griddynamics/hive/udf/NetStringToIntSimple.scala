package com.griddynamics.hive.udf

import org.apache.hadoop.hive.ql.exec.UDF

object NetStringToIntSimple {
  val IP_LENGTH = 32

  def apply(): NetStringToIntSimple = new NetStringToIntSimple()
}

class NetStringToIntSimple extends UDF {
  def evaluate(net: String): Int = {
    val slashPos = net.indexOf('/')
    val ipString = net.substring(0, slashPos)
    val maskLength = net.substring(slashPos + 1).toInt
    IpStringToIntSimple().evaluate(ipString) & Common.generateMask(maskLength)
  }
}
