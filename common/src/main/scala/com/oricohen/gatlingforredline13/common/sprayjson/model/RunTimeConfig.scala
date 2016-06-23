/**
  * Created by oric on 6/23/16.
  */

package com.oricohen.gatlingforredline13.common.sprayjson.model

case class RunTimeScenario(scenario: String,
                           run: Boolean,
                           delayStartInSec: Int,
                           users: Int,
                           rampTimeInSec: Int)

case class RunTimeConfig(durationInSeconds: Int,
                         scenarios: List[RunTimeScenario]) {
  def getScenario(javaClassName: String): Option[RunTimeScenario] = {
    scenarios.find(runtimeScenario => runtimeScenario.scenario.equals(javaClassName))
  }

  def getScenario(scenarioClass: Class[_]): RunTimeScenario = {
    val objStr = scenarioClass.toString
    val classStr = objStr.substring(6, objStr.length - 1)
    getScenario(classStr).getOrElse(
      new RunTimeScenario("", run = false, delayStartInSec = 0, users = 1, rampTimeInSec = 1)
    )
  }
}