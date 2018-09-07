package com.griddynamics.hive.udf

import scala.math.Integral.Implicits._

import org.apache.hadoop.hive.ql.exec.UDF

private[udf] object SubNetSimple {
  /**
    * Assuming net mask will not have redundant bytes e.g. 127.0.0.0/6
    * @param ip  String like "127.0.0.1"
    * @param net String like "126.0.0.0/7"
    * @return if ip is in net
    */
  def ipInSubnet(ip: String, net: String): Boolean = {
    val slashPos = net.indexOf('/')
    val (ipString, bytes) = (net.substring(0, slashPos), net.substring(slashPos + 1).toInt)
    val ipPortions = ip.split('.')
    val netPortions = ipString.split('.')
    val (quot, rem) = bytes /% 8
    for {
      i <- 0 until quot
    } {
      if (ipPortions(i) != netPortions(i))
        return false
    }
    if (rem > 0) {
      val questionableIpByte = ipPortions(quot).toInt
      val questionableNetByte = netPortions(quot).toInt
      (questionableIpByte & questionableNetByte) == questionableNetByte
    } else {
      true
    }
  }

  /**
    * True if a 'net' is a subnet of 'subnet'
    * @param net    String like "126.0.0.0/7"
    * @param subnet String like "126.0.0.0/7"
    * @return
    */
  def subnetOfSubnet(net: String, subnet: String): Boolean = {
    val slashPos = net.indexOf('/')
    val ipString = net.substring(0, slashPos)
    ipInSubnet(ipString, subnet)
  }
}

class SubNetSimple extends UDF {
  def evaluate(ipOrNet: String, net: String): java.lang.Boolean = {

    if (ipOrNet.indexOf('/') > 0) {
      SubNet.subnetOfSubnet(ipOrNet, net)
    }
    else {
      SubNet.ipInSubnet(ipOrNet, net)
    }
  }
}
