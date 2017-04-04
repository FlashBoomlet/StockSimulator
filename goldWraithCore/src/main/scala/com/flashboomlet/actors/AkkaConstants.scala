package com.flashboomlet.actors

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

  /** Initial delay for scheduling actors, in milliseconds */
  private[this] val InitialMilliseconds = 5000

  /** Length between schedule ticks for a Yahoo Finance actor, in milliseconds */
  private[this] val YahooFinanceSeconds = 80

  // FINITE DURATIONS //

  /** Finite duration for the initial wait time on actor scheduling */
  val InitialDelay = FiniteDuration(InitialMilliseconds, MILLISECONDS)

  /** Finite duration for time between Yahoo Finance fetching */
  val YahooFinanceTickLength = FiniteDuration(YahooFinanceSeconds, SECONDS)
}
