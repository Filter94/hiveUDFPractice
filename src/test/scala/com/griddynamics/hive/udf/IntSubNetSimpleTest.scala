package com.griddynamics.hive.udf

import org.scalatest.FunSuite

class IntSubNetSimpleTest extends FunSuite{
  test("Mask works for valid input") {
    val testedObject = IntSubNetSimple()
    val initialIp = Integer.parseUnsignedInt("00000000000000000000000111100101", 2)
    val net =       Integer.parseUnsignedInt("00000000000000000000000110000000", 2) //  0.0.1.128
    var mask =      Integer.parseUnsignedInt("11111111111111111111111110000000", 2) //  /25
    var ip = initialIp
    assert(testedObject.evaluate(ip, net, mask) ==  true)
    ip = Integer.parseUnsignedInt("00000000001000000000000110000001", 2)
    assert(testedObject.evaluate(ip, net, mask) ==  false)
    ip = initialIp
    mask = Integer.parseUnsignedInt("11111111111111111111111111000000", 2)  // + 1 more bit
    assert(testedObject.evaluate(ip, net, mask) ==  false)
  }
}
