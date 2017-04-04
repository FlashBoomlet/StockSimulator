package com.flashboomlet

import akka.actor.ActorSystem
import akka.actor.Props
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.flashboomlet.actors.AkkaConstants
import com.flashboomlet.actors.YahooFinanceTickActor
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.gathers.StockDataGatherer
import com.flashboomlet.stocks.YahooFinance
import com.flashboomlet.gui.Frame
import com.typesafe.scalalogging.LazyLogging

import scala.util.Try

/**
  * Created by ttlynch on 02/26/17.
  */
object Driver  extends LazyLogging {

  val system = ActorSystem("scavengersystem")

  /** Defines single global instance of JSON object mapper that can be used throughout. */
  implicit val objectMapper: ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)
  .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)

  /** Database driver to be used globally throughout. */
  implicit val databaseDriver: MongoDatabaseDriver = MongoDatabaseDriver()

  val stockDataGather = new StockDataGatherer()

  /** Global static instance of the Tweet scavenger */
  val yahooFinanceScavenger = stockDataGather.gatherData()

  /** Main entry point to the program */
  def main(args: Array[String]): Unit = {

    configureScheduler()
  }

  /**
    * Configures and schedules all of the tick actors.
    */
  def configureScheduler(): Unit = {
    Try {
      import system.dispatcher // scalastyle:ignore import.grouping

      val yahooFinanceActor = system.actorOf(Props(classOf[YahooFinanceTickActor]))

      system.scheduler.schedule(
        AkkaConstants.InitialDelay,
        AkkaConstants.YahooFinanceTickLength,
        yahooFinanceActor,
        AkkaConstants.Tick)

    }.getOrElse(logger.error("Failed to configure scheduler."))
  }

}
