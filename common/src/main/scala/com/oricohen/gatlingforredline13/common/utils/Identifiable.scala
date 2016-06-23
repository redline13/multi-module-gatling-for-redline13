/**
 * Created by oric on 04/08/2015.
 */
package com.oricohen.gatlingforredline13.common.utils

trait Identifiable {
  implicit lazy val className = ClassName(getClass.getName)
}