package com.griddynamics.hive.udf

import org.apache.hadoop.hive.ql.exec.UDF

object IntSubNetSimple {
  def apply(): IntSubNetSimple = new IntSubNetSimple()
}

class IntSubNetSimple extends UDF {
  /**
    *
    * @param ip bitwise representation of ip address
    * @param net bitwise representation of a net
    * @param mask mask to be applied to net
    * @return
    */
  def evaluate(ip: Int, net: Int, mask: Int): java.lang.Boolean = {
    (ip & mask) == (net & mask)
  }
}
