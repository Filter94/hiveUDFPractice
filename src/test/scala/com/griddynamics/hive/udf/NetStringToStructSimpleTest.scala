package com.griddynamics.hive.udf

import org.scalatest.FunSuite

class NetStringToStructSimpleTest extends FunSuite{
  test("generateMask works for valid input") {  //                                  .       .       .       .
    assert(NetStringToStructSimple.generateMask(1) ==  Integer.parseUnsignedInt("10000000000000000000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(2) ==  Integer.parseUnsignedInt("11000000000000000000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(3) ==  Integer.parseUnsignedInt("11100000000000000000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(4) ==  Integer.parseUnsignedInt("11110000000000000000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(8) ==  Integer.parseUnsignedInt("11111111000000000000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(10) == Integer.parseUnsignedInt("11111111110000000000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(16) == Integer.parseUnsignedInt("11111111111111110000000000000000", 2))
    assert(NetStringToStructSimple.generateMask(32) == Integer.parseUnsignedInt("11111111111111111111111111111111", 2))
  }

  test("evaluate works for valid input") {
    val testedObj = NetStringToStructSimple()
    assert(testedObj.evaluate("0.0.0.128/25").net == Integer.parseUnsignedInt("00000000000000000000000010000000", 2))
    assert(testedObj.evaluate("0.0.0.1/24").net ==   Integer.parseUnsignedInt("00000000000000000000000000000000", 2))
    assert(testedObj.evaluate("0.0.1.128/25").net == Integer.parseUnsignedInt("00000000000000000000000110000000", 2))
    assert(testedObj.evaluate("0.0.1.128/24").net == Integer.parseUnsignedInt("00000000000000000000000100000000", 2))
    assert(testedObj.evaluate("255.0.0.0/8").net ==  Integer.parseUnsignedInt("11111111000000000000000000000000", 2))
    assert(testedObj.evaluate("255.0.0.1/8").net ==  Integer.parseUnsignedInt("11111111000000000000000000000000", 2))
    assert(testedObj.evaluate("255.0.0.1/7").net ==  Integer.parseUnsignedInt("11111110000000000000000000000000", 2))
  }


  test("Mask works for valid input") {
    val testedObject = NetStringToStructSimple()
    assert(testedObject.evaluate("126.123.111.23/1").mask ==  Integer.parseUnsignedInt("10000000000000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/2").mask ==  Integer.parseUnsignedInt("11000000000000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/3").mask ==  Integer.parseUnsignedInt("11100000000000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/10").mask == Integer.parseUnsignedInt("11111111110000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/16").mask == Integer.parseUnsignedInt("11111111111111110000000000000000", 2))
    assert(testedObject.evaluate("0.0.111.23/32").mask == Integer.parseUnsignedInt("11111111111111111111111111111111", 2))
  }
}
