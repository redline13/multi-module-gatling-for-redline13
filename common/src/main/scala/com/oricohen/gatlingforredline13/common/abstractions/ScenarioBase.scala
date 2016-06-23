/**
 * Created by oric on 15/07/2015.
 */

package com.oricohen.gatlingforredline13.common.abstractions
import com.oricohen.gatlingforredline13.common.SessionExtensions.RichSession
import com.oricohen.gatlingforredline13.common.SessionKeys
import com.oricohen.gatlingforredline13.common.utils._
import io.gatling.core.session.Session
import io.gatling.core.structure.ScenarioBuilder

trait ScenarioBase extends Identifiable with Loggable
{
  protected def resetSession(session: Session): Session
  def buildScenario: ScenarioBuilder

  protected def writeCurrentMillisToSession(sessionKey: String)(implicit session: Session): Session = {
    implicit val methodName = MethodName("writeCurrentMillisToSession")

    val now = TimeUtils.millis
    log(s"Start - now: $now")

    session.set(sessionKey, now)
  }

  protected def setGatlingUUID(implicit session: Session): Session = {
    val GtlUUID = session.userId.substring(12)
    session.pureSet(SessionKeys.GatlingUUID ,GtlUUID)
  }

  protected def gUUIDStr(implicit session: Session): String =
    s"userId:'${session.userId.split('-').last}' - "

  lazy protected val cacheInvalidatorQueryParam = "__d"

  protected def newCacheInvalidator: String = {
    implicit val methodName = MethodName("newCacheInvalidator")
    log

    LPRandom.randString()
  }

  protected def writeCacheInvalidatorToSession(implicit session: Session): Session = {
    session.set(SessionKeys.CacheInvalidator, newCacheInvalidator)
  }
}