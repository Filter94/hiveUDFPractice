package com.griddynamics.hive.udf

import org.apache.hadoop.hive.ql.exec.UDF

object NetStringToStructSimple {
  val IP_LENGTH = 32

  def apply(): NetStringToStructSimple = new NetStringToStructSimple()

  def generateMask(maskLen: Int): Int = {
    var mask: Int = 0
    for {
      _ <- 0 until maskLen
    } {
      mask = (mask << 1) + 1
    }
    // same as pow(2, maskLen) - 1
    for {
      _ <- 0 until IP_LENGTH - maskLen
    } {
      mask = (mask << 1) + 0
    }
    mask
  }
}

case class Net(net: Int, mask: Int)

class NetStringToStructSimple extends UDF {
  def evaluate(net: String): Net = {
    val slashPos = net.indexOf('/')
    val ipString = net.substring(0, slashPos)
    val maskLength = net.substring(slashPos + 1).toInt
    val mask = NetStringToStructSimple.generateMask(maskLength)
    Net(IpStringToIntSimple().evaluate(ipString) & mask, mask)
  }
}
