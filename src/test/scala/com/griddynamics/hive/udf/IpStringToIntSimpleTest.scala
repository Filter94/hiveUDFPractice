package com.griddynamics.hive.udf

import org.scalatest.FunSuite

class IpStringToIntSimpleTest extends FunSuite{
  test("ipStringToLong works for valid input") {
    assert(IpStringToIntSimple().evaluate("0.0.0.1") == 1L)
    assert(IpStringToIntSimple().evaluate("0.0.1.1") == 257L)
    assert(IpStringToIntSimple().evaluate("0.0.2.1") == 513L)
  }
}
