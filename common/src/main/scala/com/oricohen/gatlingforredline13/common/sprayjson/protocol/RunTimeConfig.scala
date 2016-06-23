/**
  * Created by oric on 6/23/16.
  */

package com.oricohen.gatlingforredline13.common.sprayjson.protocol
import com.oricohen.gatlingforredline13.common.sprayjson.model.RunTimeScenario
import com.oricohen.gatlingforredline13.common.sprayjson.model.{RunTimeConfig => RunTimeConfigModel}
import spray.json.DefaultJsonProtocol

object RunTimeConfig extends DefaultJsonProtocol {
  implicit lazy val JsonEnvironmentConfigRuntimeFlowFormat =
    jsonFormat(RunTimeScenario, "scenario",
                                "run",
                                "delayStartInSec",
                                "users",
                                "rampTimeInSec")

  implicit lazy val JsonEnvironmentConfigRuntimeFormat =
    jsonFormat(RunTimeConfigModel, "durationInSeconds",
                                   "scenarios")
}