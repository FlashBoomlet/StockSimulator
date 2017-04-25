package com.flashboomlet

import java.net.InetSocketAddress
import java.net.Socket

import akka.actor.ActorSystem
import akka.actor.Props
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.flashboomlet.actors.AkkaConstants
import com.flashboomlet.actors.StockGathererTickActor
import com.flashboomlet.controls.StockShell
import com.flashboomlet.data.DateUtil
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.controllers.IndustryController
import com.flashboomlet.db.controllers.MarketController
import com.flashboomlet.db.controllers.PortfolioController
import com.flashboomlet.gathers.ListingGatherer
import com.flashboomlet.gathers.StockDataGatherer
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
  implicit val industryController: IndustryController = IndustryController()
  implicit val marketController: MarketController = MarketController()
  implicit val portfolioController: PortfolioController = PortfolioController()

  val stockDataGather = new StockDataGatherer()

  /** Global static instance of the Tweet scavenger */
  //  yahooFinanceScavenger = stockDataGather.gatherData()

  /** Main entry point to the program */
  def main(args: Array[String]): Unit = {
    /*
     * Pull in historical data from last pulled date to current.
     * Start pulling in data at 4pm UTC and there after every 24 hours.
     */

    // Open up stock shell
    StockShell.StockShell()

    val du = new DateUtil
    val sd = new StockDataGatherer
    val lg = new ListingGatherer
    val startOfSim = du.getNowInMillis

    // Have the ability to clear listings as seen applicable.
    // lg.clearListings()
    // industryController.clearAllIndustries()

    // lg.getListings
    val fetchIndustriesFinish = du.getNowInMillis
    logger.info(s"It took: ${fetchIndustriesFinish-startOfSim} milliseconds to update listings")
    val reenableStart = du.getNowInMillis
    // re-enable all stocks are the beginning of the simulations to see if there is data available.

    // databaseDriver.getUSStockListings.foreach(s => sd.enableStock(s))

    // Gather Historical Data
    val reenableFinish = du.getNowInMillis
    logger.info(s"It took: ${reenableFinish-fetchIndustriesFinish} milliseconds to re-enable all stocks")
    // sd.gatherDataHistory()
    val historicalFinish = du.getNowInMillis
    logger.info(s"It took: ${historicalFinish-reenableFinish} milliseconds to update historical data")

    // Configure and start the scheduler
    // configureScheduler()
  }

  def testInternet(site: String): Boolean = {
    val sock = new Socket()
    val addr = new InetSocketAddress(site,80)
    Try {
      sock.connect(addr,3000)
      true
    }.getOrElse(false)
  }


  /**
    * Configures and schedules all of the tick actors.
    */
  def configureScheduler(): Unit = {
    Try {
      import system.dispatcher // scalastyle:ignore import.grouping

      val yahooFinanceActor = system.actorOf(Props(classOf[StockGathererTickActor]))

      system.scheduler.schedule(
        AkkaConstants.InitialDelay,
        AkkaConstants.YahooFinanceTickLength,
        yahooFinanceActor,
        AkkaConstants.Tick)

    }.getOrElse(logger.error("Failed to configure scheduler."))
  }

}
