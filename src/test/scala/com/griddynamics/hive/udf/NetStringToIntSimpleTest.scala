package com.griddynamics.hive.udf

import org.scalatest.FunSuite

class NetStringToIntSimpleTest extends FunSuite{
  test("generateMask works for valid input") {  //                                  .       .       .       .
    assert(Common.generateMask(1) ==  Integer.parseUnsignedInt("10000000000000000000000000000000", 2))
    assert(Common.generateMask(2) ==  Integer.parseUnsignedInt("11000000000000000000000000000000", 2))
    assert(Common.generateMask(3) ==  Integer.parseUnsignedInt("11100000000000000000000000000000", 2))
    assert(Common.generateMask(4) ==  Integer.parseUnsignedInt("11110000000000000000000000000000", 2))
    assert(Common.generateMask(8) ==  Integer.parseUnsignedInt("11111111000000000000000000000000", 2))
    assert(Common.generateMask(10) == Integer.parseUnsignedInt("11111111110000000000000000000000", 2))
    assert(Common.generateMask(16) == Integer.parseUnsignedInt("11111111111111110000000000000000", 2))
    assert(Common.generateMask(32) == Integer.parseUnsignedInt("11111111111111111111111111111111", 2))
  }

  test("evaluate works for valid input") {
    val testedObj = NetStringToIntSimple()
    assert(testedObj.evaluate("0.0.0.128/25") == Integer.parseUnsignedInt("00000000000000000000000010000000", 2))
    assert(testedObj.evaluate("0.0.0.1/24") ==   Integer.parseUnsignedInt("00000000000000000000000000000000", 2))
    assert(testedObj.evaluate("0.0.1.128/25") == Integer.parseUnsignedInt("00000000000000000000000110000000", 2))
    assert(testedObj.evaluate("0.0.1.128/24") == Integer.parseUnsignedInt("00000000000000000000000100000000", 2))
    assert(testedObj.evaluate("255.0.0.0/8") ==  Integer.parseUnsignedInt("11111111000000000000000000000000", 2))
    assert(testedObj.evaluate("255.0.0.1/8") ==  Integer.parseUnsignedInt("11111111000000000000000000000000", 2))
    assert(testedObj.evaluate("255.0.0.1/7") ==  Integer.parseUnsignedInt("11111110000000000000000000000000", 2))
  }
}
