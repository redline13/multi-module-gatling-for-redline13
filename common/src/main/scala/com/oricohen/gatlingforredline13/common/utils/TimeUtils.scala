/**
 * Created by oric on 15/07/2015.
 */

package com.oricohen.gatlingforredline13.common.utils

import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.{Calendar, Locale}

import io.gatling.core.Predef._

import scala.util.Try

object TimeUtils extends Identifiable with Loggable {
  def millis = Calendar.getInstance.getTimeInMillis

  def seconds = {
    val millisString = Calendar.getInstance.getTimeInMillis.toString
    millisString.substring(0, millisString.length-3)
  }

  /**
   * return log-formatted string of the current time
*
   * @return log-formatted string of the current time
   */
  def logFormat: String = {
    implicit val methodName = MethodName("logFormat")
    log

    val time = Calendar.getInstance().getTime
    new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS").format(time)
  }

  def setTimeStampToSessionValue(session: Session, sessionKey: String): Session =
    session.set(sessionKey, millis.toString)

  def stringToDateObject(str: String, format: String): Try[OffsetDateTime] = {
    implicit val methodName = MethodName("stringToDateObject")
    log(s"Start str: $str ; format: $format")

    val dateTimeFormatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
    Try(OffsetDateTime.parse(str, dateTimeFormatter))
  }
}