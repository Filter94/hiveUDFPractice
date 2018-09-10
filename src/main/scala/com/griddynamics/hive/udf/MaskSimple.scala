package com.griddynamics.hive.udf

import org.apache.hadoop.hive.ql.exec.UDF

object MaskSimple {
  def apply(): MaskSimple = new MaskSimple()
}

class MaskSimple extends UDF {
  /**
    * @param net string like 127.0.0.128/25
    * @return
    */
  def evaluate(net: String): Int = {
    val slashPos = net.indexOf('/')
    val maskLength = net.substring(slashPos + 1).toInt
    Common.generateMask(maskLength)
  }
}
