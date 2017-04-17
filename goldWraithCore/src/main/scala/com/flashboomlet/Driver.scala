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
import com.flashboomlet.data.DateUtil
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.queries.IndustryController
import com.flashboomlet.db.queries.MarketController
import com.flashboomlet.db.queries.PortfolioController
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

    val du = new DateUtil
    val startOfSim = du.getNowInMillis


    val sd = new StockDataGatherer
    //databaseDriver.getUSStockListings.foreach(s => sd.enableStock(s))
    val tmpFinishOne = du.getNowInMillis-startOfSim
    logger.info(s"It took: ${tmpFinishOne} milliseconds to update stock listings")
    sd.gatherData()
    logger.info(s"It took: ${tmpFinishOne-startOfSim} milliseconds to update stocks")

    /*
        // Check the network connection
        val internetTest = testInternet("google.com")
        if(!internetTest){
          logger.info(s"\n\n*************\n\nIs the internet functioning? $internetTest\n\n*************\n")
          System.exit(1)
        }

        val lg = new ListingGatherer
        // TODO: Clear all stock listings for now
        lg.clearListings()
        industryController.clearAllIndustries()

        // This should hopefully not print anything.
        lg.fetchListings.foreach(println)
        // Ensure that the stock listings are up to date
        lg.getListings


        println("\n\n\n\n\n\n" + industryController.getBasicIndustriesMain(1491890400000L,1492239367913L,"TSLA","NASDAQ") + "\n\n\n\n")

        */
    // Configure and start the scheduler
    // configureScheduler()

    println("\nTesting to see if the data gathering was successful.\n")
    println(databaseDriver.getUSStockListings.filter(p => p.symbol == "TSLA").head)
    industryController.getCapitalGoodsMain(1491890400000L,1492239367913L,"TSLA","NASDAQ").foreach(println)

    println("\n\n\nSim has been running for:" +  (du.getNowInMillis-startOfSim) + " (milliseconds)\n\n\n\n")
    System.exit(1)
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
