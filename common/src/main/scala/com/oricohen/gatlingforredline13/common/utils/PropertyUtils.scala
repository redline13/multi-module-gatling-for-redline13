/**
 * Created by oric on 21/09/2015.
 */

package com.oricohen.gatlingforredline13.common.utils

import java.util.Properties

import scala.util.{Failure, Success, Try}

object PropertyUtils {
  private lazy val minFileNameStringSize = 5
  private lazy val minConfigNameStringSize = 3
  private def strLenValidationErrMsg(param: String, num: Int) =
    s"$param does not allow a string with less than $num characters"

  def getProperty(fileName: String, configName: String): Try[String] = {
    require(fileName.length   > minFileNameStringSize,   strLenValidationErrMsg("fileName"  , 6))
    require(configName.length > minConfigNameStringSize, strLenValidationErrMsg("configName", 4))

    val prop = new Properties()
    val classLoader = getClass.getClassLoader
    Try(prop.load(classLoader.getResourceAsStream(fileName))).recoverWith {
      case t: Throwable => Failure(new Exception("Could not retrieve resource"))
    }
    Success(prop.getProperty(configName))
  }
}