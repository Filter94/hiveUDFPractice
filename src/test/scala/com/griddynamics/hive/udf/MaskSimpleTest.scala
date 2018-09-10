package com.griddynamics.hive.udf

import org.scalatest.FunSuite

class MaskSimpleTest extends FunSuite{
  test("Mask works for valid input") {
    val testedObject = MaskSimple()
    assert(testedObject.evaluate("126.123.111.23/1") ==  Integer.parseUnsignedInt("10000000000000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/2") ==  Integer.parseUnsignedInt("11000000000000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/3") ==  Integer.parseUnsignedInt("11100000000000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/10") == Integer.parseUnsignedInt("11111111110000000000000000000000", 2))
    assert(testedObject.evaluate("126.123.111.23/16") == Integer.parseUnsignedInt("11111111111111110000000000000000", 2))
    assert(testedObject.evaluate("0.0.111.23/32") == Integer.parseUnsignedInt("11111111111111111111111111111111", 2))
  }
}
