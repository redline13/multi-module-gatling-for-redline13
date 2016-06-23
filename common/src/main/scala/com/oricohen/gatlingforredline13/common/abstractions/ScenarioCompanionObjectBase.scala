/**
 * Created by oric on 22/09/2015.
 */

package com.oricohen.gatlingforredline13.common.abstractions

import com.oricohen.gatlingforredline13.common.CurrentRuntimeConfig
import io.gatling.core.Predef._
import io.gatling.core.structure.{PopulatedScenarioBuilder, ScenarioBuilder}
import scala.concurrent.duration._
import scala.language.postfixOps

trait ScenarioCompanionObjectBase {
  protected def flowScenario: ScenarioBuilder

  def populatedScenarioBuilder: PopulatedScenarioBuilder = {
    val scenarioRuntimeConf = CurrentRuntimeConfig.instance.getScenario(this.getClass)
    flowScenario.inject(
      nothingFor(scenarioRuntimeConf.delayStartInSec),
      rampUsers(scenarioRuntimeConf.users).over(scenarioRuntimeConf.rampTimeInSec seconds)
    )
  }

  lazy val DEBUG_populatedScenarioBuilder = flowScenario.inject(atOnceUsers(1))
}