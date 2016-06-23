
/**
 * Created by oric on 04/08/2015.
 */
package com.oricohen.gatlingforredline13.common.utils

class ClassName(val fullName: String) {
  lazy val shortName: String = fullName.split('.').last
  override def toString = shortName
}

object ClassName {
  def apply(name: String): ClassName = new ClassName(name)
}