/**
 * Created by oric on 21/09/2015.
 */

package com.oricohen.gatlingforredline13.common

import java.io.File
import java.nio.charset.Charset
import com.google.common.io.Files
import com.oricohen.gatlingforredline13.common.sprayjson.model.RunTimeConfig
import com.oricohen.gatlingforredline13.common.sprayjson.protocol.RunTimeConfig.JsonEnvironmentConfigRuntimeFormat
import com.oricohen.gatlingforredline13.common.utils._
import spray.json.JsValue
import scala.util.{Failure, Success, Try}

object CurrentRuntimeConfig extends Identifiable with Loggable {
  private lazy val directoryName = "runtime-config/"
  private lazy val propertiesFileName = "! runtimeSelector.properties"
  private lazy val propertyName = "runtimeFileName"

  lazy val instance: RunTimeConfig = {
    implicit val methodName = MethodName("instance")
    log

    val runtimeFileName = getCurrentRuntimeFileName
    getCurrentRuntimeConfig(runtimeFileName)
  }

  private def getCurrentRuntimeFileName: String = {
    implicit val methodName = MethodName("getCurrentRuntimeFileName")
    log

    PropertyUtils.getProperty(fileName = directoryName + propertiesFileName,
      configName = propertyName) match {
      case Failure(t) =>
        throw new Exception(s"Not able to retrieve $propertyName from $propertiesFileName")
      case Success(prop) => prop
    }
  }

  private def getCurrentRuntimeConfig(runtimeFileName: String): RunTimeConfig = {
    implicit val methodName = MethodName("getCurrentRuntimeConfig")
    log

    val runtimeConfFile = getRuntimeConfigFile(runtimeFileName)
    val jsonString      = getStringFromFile(runtimeConfFile)
    val jsonObject      = getJsonObjectFromString(jsonString)
    convertJsonObjToRuntimeConfigObj(jsonObject)
  }

  private def getRuntimeConfigFile(runtimeFileName: String): File = {
    implicit val methodName = MethodName("getRuntimeConfigFile")
    log

    val classLoader = getClass.getClassLoader
    Try(new File(classLoader.getResource(directoryName + runtimeFileName).getFile)) match {
      case Failure(t) =>
        throw new Exception("Unable to get the following resource: " + directoryName + runtimeFileName)
      case Success(returnFile) => returnFile
    }
  }

  private def getStringFromFile(file: File, charset: Charset = Charset.defaultCharset()): String = {
    implicit val methodName = MethodName("getStringFromFile")
    log

    Try(Files.toString(file, Charset.defaultCharset)) match {
      case Failure(t) =>
        throw new Exception("Unable to read the contents of the selected RuntimeConfig file")
      case Success(content) => content
    }
  }

  private def getJsonObjectFromString(fileContent: String): JsValue = {
    implicit val methodName = MethodName("getJsonObjectFromString")
    log

    JsonUtils.getJsonObject(fileContent) match {
      case Failure(t) =>
        throw new Exception("Unable to convert the selected RuntimeConfig file contents to a Json object")
      case Success(jsonObj) => jsonObj
    }
  }

  private def convertJsonObjToRuntimeConfigObj(json: JsValue): RunTimeConfig = {
    implicit val methodName = MethodName("convertJsonObjToRuntimeConfigObj")
    log

    Try(json.convertTo[RunTimeConfig]) match {
      case Failure(t) =>
        throw new Exception("Cannot deserialize RuntimeConfig JSON. Please correct file according to schema" +
                            "\r\n" + json.prettyPrint)
      case Success(runtimeConf) => runtimeConf
    }
  }
}