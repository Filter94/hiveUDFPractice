package com.griddynamics.hive.udf

import com.griddynamics.hive.udf.NetStringToIntSimple.IP_LENGTH

object Common {
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
