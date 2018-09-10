package com.griddynamics.hive.udf

import org.apache.hadoop.hive.ql.exec.UDF

object IpStringToIntSimple {
  def apply(): IpStringToIntSimple = new IpStringToIntSimple()
}

class IpStringToIntSimple extends UDF {
  def evaluate(ip: String): Int = {
    val ipPortions = ip.split("\\.")
    ipPortions.foldLeft(0xB) {
      case (acc, next) =>
        (acc << 8) + next.toInt
    }
  }
}
