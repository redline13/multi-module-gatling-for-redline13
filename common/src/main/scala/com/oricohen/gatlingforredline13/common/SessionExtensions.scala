/**
  * Created by oric on 1/18/16.
  */

package com.oricohen.gatlingforredline13.common

import com.twitter.algebird._
import io.gatling.core.session.Session

object SessionExtensions {
  implicit class RichSession(val session: Session) extends AnyVal {
    def getOrZero[T](sessionKey: String)(zeroValue: T): T =
      if (session.contains(sessionKey)) session(sessionKey).as[T]
      else zeroValue

    def get[T](sessionKey: String)(implicit ma: Monoid[T]): T =
      getOrZero[T](sessionKey)(ma.zero)

    def validate[T](sessionKey: String, unContainedIsTrue: Boolean = false)
                   (validateIfExists: (T) => Boolean): Boolean =
      if (session.contains(sessionKey)) validateIfExists(session(sessionKey).as[T])
      else unContainedIsTrue

    def pureSet[T](sessionKey: String, value: T) = {
      val newSession =
        if (session.contains(sessionKey)) session.remove(sessionKey)
        else session

      newSession.set(sessionKey, value)
    }
  }
}