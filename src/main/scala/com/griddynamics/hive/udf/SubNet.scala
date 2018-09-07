package com.griddynamics.hive.udf

import org.apache.hadoop.hive.ql.exec.{UDFArgumentLengthException, UDFArgumentTypeException}
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory
import org.apache.hadoop.hive.serde2.objectinspector.{ObjectInspector, ObjectInspectorConverters, PrimitiveObjectInspector}
import org.apache.hadoop.io.BooleanWritable

import scala.math.Integral.Implicits._

private[udf] object SubNet {
  /**
    * Assuming net mask will not have redundant bytes e.g. 127.0.0.0/6
    * @param ip  String like "127.0.0.1"
    * @param net String like "126.0.0.0/7"
    * @return if ip is in net
    */
  def ipInSubnet(ip: String, net: String): Boolean = {
    val slashPos = net.indexOf('/')
    val (ipString, bytes) = (net.substring(0, slashPos), net.substring(slashPos + 1).toInt)
    val ipPortions = ip.split('.')
    val netPortions = ipString.split('.')
    val (quot, rem) = bytes /% 8
    for {
      i <- 0 until quot
    } {
      if (ipPortions(i) != netPortions(i))
        return false
    }
    if (rem > 0) {
      val questionableIpByte = ipPortions(quot).toInt
      val questionableNetByte = netPortions(quot).toInt
      (questionableIpByte & questionableNetByte) == questionableNetByte
    } else {
      true
    }
  }

  /**
    * True if a 'net' is a subnet of 'subnet'
    * @param net    String like "126.0.0.0/7"
    * @param subnet String like "126.0.0.0/7"
    * @return
    */
  def subnetOfSubnet(net: String, subnet: String): Boolean = {
    val slashPos = net.indexOf('/')
    val ipString = net.substring(0, slashPos)
    ipInSubnet(ipString, subnet)
  }
}

class SubNet extends GenericUDF {
  private val result: BooleanWritable = new BooleanWritable()
  private var converters: Array[ObjectInspectorConverters.Converter] = _

  override def getDisplayString(children: Array[String]): String =
    getStandardDisplayString("in_sub_net", children)

  override def initialize(arguments: Array[ObjectInspector]): PrimitiveObjectInspector = {
    if (arguments.length != 2)
      throw new UDFArgumentLengthException("IpInNet accepts exactly two arguments.")
    arguments.zipWithIndex.foreach {
      case (arg, i) =>
        val argType = arg.asInstanceOf[PrimitiveObjectInspector].getPrimitiveCategory
        if (argType != PrimitiveCategory.STRING)
          throw new UDFArgumentTypeException(i,
            "Argument #%d to IpToNet should be String but %s is found."
              .format(i, argType))
    }
    converters = new Array[ObjectInspectorConverters.Converter](arguments.length)
    converters = arguments.map{ arg =>
      ObjectInspectorConverters.getConverter(arg, PrimitiveObjectInspectorFactory.writableStringObjectInspector)
    }
    PrimitiveObjectInspectorFactory.getPrimitiveWritableObjectInspector(PrimitiveCategory.BOOLEAN)
  }


  override def evaluate(arguments: Array[GenericUDF.DeferredObject]): BooleanWritable = {
    val ipOrNet: String = converters(0).convert(arguments(0).get()).toString
    val net: String = converters(1).convert(arguments(1).get()).toString

    if (ipOrNet.indexOf('/') > 0) {
      result.set(SubNet.subnetOfSubnet(ipOrNet, net))
      result
    }
    else {
      result.set(SubNet.ipInSubnet(ipOrNet, net))
      result
    }
  }
}
