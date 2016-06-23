/**
 * Created by oric on 24/06/2015.
 *
 * Scenario and user distribution - the main execution of all tests
 */

package com.oricohen.gatlingforredline13.simulations

import com.oricohen.gatlingforredline13.common.CurrentRuntimeConfig
import com.oricohen.gatlingforredline13.common.utils.{Identifiable, Loggable, MethodName}
import com.oricohen.gatlingforredline13.scenarios.{Scenario1, Scenario2, Scenario3}
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.PopulatedScenarioBuilder
import scala.concurrent.duration._
import scala.language.postfixOps

  class MainSimulation extends Simulation with Identifiable with Loggable {
    /**
     * Constructor Start
     */
    setUp(scenarioBuilders: _*)
    .maxDuration(CurrentRuntimeConfig.instance.durationInSeconds seconds)
    /**
     * Constructor End
     */


    lazy val scenarioBuilders: Seq[PopulatedScenarioBuilder] = {
      implicit val methodName = MethodName("scenarioBuilders")
      log

      val seq = scenarioSeq

      if (seq.nonEmpty) seq
      else throw new Exception("No scenario has been enabled in RuntimeConfig \"runtime\" section!!")
    }

    private def checkIfRequired(scenarioClass: Class[_]): Boolean = {
      implicit val methodName = MethodName("checkIfRequired")
      log(s"Start - javaClassName: $scenarioClass")

      val res = CurrentRuntimeConfig.instance.getScenario(scenarioClass)
      log(s"${res.scenario}: run = ${res.run}")
      res.run
    }

    private def appendIfRequired(scenarioClass: Class[_],
                                 scenarioBuilder: PopulatedScenarioBuilder,
                                 prevSeq: Seq[PopulatedScenarioBuilder]): Seq[PopulatedScenarioBuilder] = {
      implicit val methodName = MethodName("appendIfRequired")
      log

      val isRequired = checkIfRequired(scenarioClass)
      if (isRequired) scenarioBuilder +: prevSeq
      else prevSeq
    }

    private lazy val scenarioSeq: Seq[PopulatedScenarioBuilder] = {
      implicit val methodName = MethodName("scenarioSeq")
      log

      val empty = Seq()
      val wScenario1 = appendIfRequired(Scenario1.getClass, Scenario1.populatedScenarioBuilder, empty)
      val wScenario2 = appendIfRequired(Scenario2.getClass, Scenario2.populatedScenarioBuilder, wScenario1)
      val wScenario3 = appendIfRequired(Scenario3.getClass, Scenario3.populatedScenarioBuilder, wScenario2)

      log("All required appended. Seq.len:" + wScenario3.length)
      wScenario3.foreach(item => log("\t\tSeq item: " + item.scenarioBuilder.name))
      wScenario3
    }
  }