/**
 * Created by oric on 25/06/2015.
 */

package com.oricohen.gatlingforredline13.common.utils

import java.util.Calendar

import com.oricohen.gatlingforredline13.common.utils.LogLevels.TRACE

import scala.util.Random

object LPRandom extends Identifiable with Loggable {
  protected implicit def getTimeInMs: Long = Calendar.getInstance.getTimeInMillis
  implicit lazy val rand = new Random(getTimeInMs)

  def betweenWithSeed(seed: Long = Calendar.getInstance.getTimeInMillis,
              minNum: Int = 0,
              maxNum: Int): String = {
    implicit val methodName = MethodName("betweenWithSeed")

    val rand = new Random(seed)
    val res = (rand.nextInt(maxNum - minNum) + minNum).toString

    log(s"minNum:$minNum maxNum:$maxNum res:$res")
    res
  }

  def betweenWithRand(rand: Random, minNum: Int = 0, maxNum: Int): String = {
    implicit val methodName = MethodName("betweenWithSeed")

    val formatString = "%0" + maxNum.toString.length.toString + "d"
    log(TRACE, s"FormatString: $formatString")

    val randNum =
      if (minNum == maxNum) minNum
      else rand.nextInt(maxNum - minNum) + minNum
    val res = formatString.format(randNum)

    log(s"from:$minNum to:$maxNum res:$res")
    res
  }

  def between(minNum: Int = 0, maxNum: Int)(implicit rand: Random): Int = {
    betweenWithRand(rand, minNum, maxNum).toInt
  }

  def randString(pref: String = "", maxNum: Int = Int.MaxValue): String = {
    pref + betweenWithRand(rand = rand, maxNum = maxNum)
  }
}