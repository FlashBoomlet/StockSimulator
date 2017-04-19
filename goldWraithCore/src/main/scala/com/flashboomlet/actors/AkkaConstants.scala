package com.flashboomlet.actors

import com.flashboomlet.data.DateUtil

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration.MILLISECONDS
import scala.concurrent.duration.SECONDS
import scala.concurrent.duration.HOURS

/**
  * String constants used in akka systems
  * Created by ttlynch on 02/26/17.
  */
object AkkaConstants {

  /** String constant for the tick message */
  val Tick = "tick"


  // Calculate delay to occur on an exact hour. (4:30 pm EST) Stock Hours Are 9:30-4pm ET
  val du = new DateUtil()
  val cur = du.getNowInMillis
  val desiredTime = du.formattedTimeToMillis(
    du.dateToYear(cur),
    du.dateToMonth(cur),
    du.dateToDay(cur),
    4,
    30,
    0,
    1
  )

  /** Initial delay for scheduling actors, in milliseconds */
  private[this] val InitialMilliseconds = if(desiredTime-cur > 0){
    desiredTime-cur
  } else 0


  /** Length between schedule ticks for a Yahoo Finance actor, in milliseconds */
  // 24 Hours: 86400000 (milliseconds)
  private[this] val YahooFinanceSeconds = 86400000


  // FINITE DURATIONS //

  /** Finite duration for the initial wait time on actor scheduling */
  val InitialDelay = FiniteDuration(InitialMilliseconds, MILLISECONDS)

  /** Finite duration for time between Yahoo Finance fetching */
  val YahooFinanceTickLength = FiniteDuration(YahooFinanceSeconds, SECONDS)
}
