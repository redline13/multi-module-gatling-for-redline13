/**
 * Created by oric on 04/08/2015.
 */

package com.oricohen.gatlingforredline13.common.utils

import com.typesafe.scalalogging.StrictLogging
import io.gatling.core.session.Session

import scala.language.implicitConversions

trait Loggable extends StrictLogging {
  lazy val logLinePref = "#####"

  def log(logLevel: LogLevels.LogLevel,
          methodName: => MethodName, // Pass parameter by name
          state: => String) // Pass parameter by name
         (implicit className: ClassName): Unit = {
    implicit def MethodNameToString(methodName: MethodName): String = methodName.toString

    logLevel match {
      case LogLevels.TRACE => logger trace text(methodName, state, className)
      case LogLevels.DEBUG => logger debug text(methodName, state, className)
      case LogLevels.INFO  => logger info  text(methodName, state, className)
      case LogLevels.WARN  => logger warn  text(methodName, state, className)
      case LogLevels.ERROR => logger error text(methodName, state, className)
    }
  }

  def log(implicit methodName: MethodName,
                   logLevel: LogLevels.LogLevel,
                   className: ClassName): Unit = {
    log(logLevel, methodName, "Start")
  }

  def log(state: => String) // Pass parameter by name
         (implicit methodName: MethodName,
                   logLevel: LogLevels.LogLevel,
                   className: ClassName): Unit = {
    log(logLevel, methodName, state)
  }

  def log(logLevel: LogLevels.LogLevel,
          state: => String = "Start") // Pass parameter by name
         (implicit methodName: MethodName,
                   className: ClassName): Unit = {
    log(logLevel, methodName, state)
  }

  def logS(state: => String) // Pass parameter by name
         (implicit logLevel: LogLevels.LogLevel,
                   methodName: MethodName,
                   className: ClassName,
                   session: Session): Session = {
    log(logLevel, methodName, s"userId:'${session.userId.split('-').last}' - " + state)
    session
  }

  def logS(logLevel: LogLevels.LogLevel,
           state: => String) // Pass parameter by name
          (implicit methodName: MethodName,
                    className: ClassName,
                    session: Session): Session = {
    log(logLevel, methodName, state)
    session
  }

  private def text(methodName: => String,
                   state: => String,
                   className: ClassName): String = {
    s"$className.$methodName - $state\t"
  }

  implicit val debugLogLevel = LogLevels.DEBUG
}