/**
 * Created by oric on 16/07/2015.
 */

package com.oricohen.gatlingforredline13.common.utils

object LogLevels {
  sealed trait LogLevel

  case object TRACE extends LogLevel
  case object DEBUG extends LogLevel
  case object INFO  extends LogLevel
  case object WARN  extends LogLevel
  case object ERROR extends LogLevel
}