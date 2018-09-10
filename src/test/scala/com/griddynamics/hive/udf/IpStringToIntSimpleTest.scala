package com.griddynamics.hive.udf

import org.scalatest.FunSuite

class IpStringToIntSimpleTest extends FunSuite{
  val testedObject = IpStringToIntSimple()
  test("ipStringToLong works for valid input") {
    assert(testedObject.evaluate("0.0.0.1") == 1L)
    assert(testedObject.evaluate("0.0.1.1") == 257L)
    assert(testedObject.evaluate("0.0.2.1") == 513L)
  }
}
