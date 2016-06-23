/**
  * Created by oric on 6/23/16.
  */

package com.oricohen.gatlingforredline13.scenarios

import com.oricohen.gatlingforredline13.common.SessionKeys
import com.oricohen.gatlingforredline13.common.abstractions.{ScenarioBase, ScenarioCompanionObjectBase}
import com.oricohen.gatlingforredline13.common.utils.MethodName
import io.gatling.core.Predef._
import io.gatling.core.session.Session
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import scala.concurrent.duration._
import scala.language.postfixOps

class Scenario1 extends ScenarioBase {
  val buildScenario: ScenarioBuilder = {
    implicit val methodName = MethodName("buildScenario")

    scenario("scenario1")
     .forever("mainRepeater") {
        exec(implicit session => writeCacheInvalidatorToSession)
       .exec(
         http("req1")
          .get("http://www.liveperson.com/?req=1")
          .queryParam(cacheInvalidatorQueryParam, "${" + SessionKeys.CacheInvalidator + "}"))
       .exec(implicit session => logS("Completed another iteration of req1"))
       .pause(1 second)
      }
  }

  def resetSession(session:Session): Session = session
}

object Scenario1 extends ScenarioCompanionObjectBase {
  lazy val flowScenario = {
    val agentChatScenario = new Scenario1()
    agentChatScenario.buildScenario
  }
}