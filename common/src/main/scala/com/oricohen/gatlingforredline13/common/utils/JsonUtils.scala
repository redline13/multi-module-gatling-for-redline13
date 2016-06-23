/**
 * Created by oric on 20/09/2015.
 */

package com.oricohen.gatlingforredline13.common.utils

import javax.activity.InvalidActivityException

import spray.json.JsonParser.ParsingException
import spray.json.{JsValue, JsonParser}

import scala.util.{Failure, Try}

object JsonUtils {
  /**
   * retrieve JSON string using a JSON-P string
   *
   * @param jsonp the JSON-P string
   * @return the JSON string extracted from JSON-P
   */
  def getJsonFromJsonp(jsonp: String): String = {
    val isNotEmpty = jsonp.length > 0
    // The index of the first occurrence of the '{' char in the JSON-P string
    val firstIndex = if (isNotEmpty) jsonp indexOf '{' else 0

    // The index of the last occurrence of the '}' char in the JSON-P string
    val lastIndex = if (isNotEmpty) (jsonp lastIndexOf '}') + 1 else 0

    if ((firstIndex < 0 || lastIndex < 0) && isNotEmpty) {
      println(s"ERROR! firstIndex: $firstIndex\t\tlastIndex: $lastIndex\n\r" +
        s"jsonp: '$jsonp'")
      throw new InvalidActivityException(
        "Could not find index of braces character in JSON-P string")
    }

    // the string between the two indices found just now (JSON)
    val subStr = {
      if (isNotEmpty)
        jsonp.substring(firstIndex, lastIndex)
      else
        jsonp
    }
    // println(s"getJsonFromJsonp json-p: $jsonp\r\n" +
    //   s"firstIndexOf '{': $firstIndex\t\tlastIndexOf '}': $lastIndex\r\n" +
    //   s"subString: $subStr")

    // return the JSON string
    subStr
  }

  /**
   * retrieve a JsValue object using a JSON string
   *
   * @param json the JSON string
   * @return the JsValue object from the parsed string
   */
  def getJsonObject(json: String): Try[JsValue] = {
    json match {
      case "" => Failure(new Exception("Empty string JSON"))
      case _ =>
        Try(JsonParser(json)).recoverWith {
          case t: ParsingException =>
            Try(JsonParser("{\"array\":[" + json + "]}")).recoverWith {
              case t2: ParsingException =>
                return Failure(new Exception(s"EXCEPTION CAUGHT! ${t.toString}\r\n" +
                  s"\tError parsing JSON:\r\n\t\t\t$json"))
            }
        }
    }
  }

  /**
   * retrieve a JsValue object from a JSON-P string
   *
   * @param jsonp the JSON-P string
   * @return the JsValue object from the parsed string
   */
  def getJsonObjectFromJsonp(jsonp: String): Try[JsValue] = {
    val json = getJsonFromJsonp(jsonp)
    getJsonObject(json)
  }
}