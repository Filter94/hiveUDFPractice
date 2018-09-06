package com.griddynamics.hive.udf

import org.scalatest.{FunSuite, PrivateMethodTester}

class SubNetTest extends FunSuite with PrivateMethodTester {
  test("netStringToLong works for some simple positive cases") {
    assert(SubNet.ipInSubnet("127.128.129.130", "127.128.129.0/24"))
    //                                   ...11.b                  ...10.b
    assert(SubNet.ipInSubnet("127.128.3.130", "127.128.2.0/23"))
  }

  test("netStringToLong works for some simple negative cases") {
    assert(!SubNet.ipInSubnet("127.128.129.130", "127.128.2.0/23"))
    assert(!SubNet.ipInSubnet("127.122.129.130", "127.128.129.0/24"))
  }

  test("subnetOfSubnet works for some simple positive cases") {
    assert(SubNet.ipInSubnet("127.128.129.0/24", "127.128.129.0/24"))
    //                                   ...11.b                  ...10.b
    assert(SubNet.ipInSubnet("127.128.3.0/25", "127.128.2.0/23"))
  }

  test("subnetOfSubnet works for some simple negative cases") {
    assert(!SubNet.ipInSubnet("127.128.129.0/24", "127.128.2.0/23"))
    assert(!SubNet.ipInSubnet("127.122.129.130/32", "127.128.129.0/24"))
  }

  test("subnetOfSubnet relation is not symmetric") {
    assert(!SubNet.ipInSubnet("127.128.2.0/23", "127.128.3.0/25"))
  }
}
