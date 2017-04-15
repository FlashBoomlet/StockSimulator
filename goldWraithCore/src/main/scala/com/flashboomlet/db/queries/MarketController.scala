package com.flashboomlet.db.queries

import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoConstants
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by ttlynch on 4/4/17.
  */
class MarketController
  extends MongoConstants
  with LazyLogging
  with MongoImplicits {

  /** Instance of pre-configured database driver. */
  val databaseDriver = MongoDatabaseDriver()
  val industryController = new IndustryController()

  // Aggregate and calculate the most active stocks
  def getOverallMostActive(start: Long, end: Long): List[(StockData, Double)] = {
    // Call each individual industry, then aggregate them.
    val stocks = aggregateGetters(start, end)
    stocks.map{ s =>
      val cur = s.sortBy(s => s.lastTrade).take(51)
      val previous = cur.takeRight(50)
      // Pair of a stock object and the average (absolute) change in the stocks
      (
        s.head,
        (cur.take(50) zip previous).map( p =>
          Math.abs(p._1.lastTrade-p._2.lastTrade)
        ).sum/50.0
      )
    }.sortBy(i => i._2).take(50)
  }

  // Aggregate and calculate the most volatile stocks
  def getOverallMostVolatile(start: Long, end: Long): List[(StockData, Double)] = {
    getOverallMostActive(start, end)
  }

  // Aggregate and calculate the biggest losers stocks
  def getOverallBiggestLosers(start: Long, end: Long): List[(StockData, Double)] = {
    // Call each individual industry, then aggregate them.
    getOverallChange(start, end).takeRight(50) // Take the biggest losers
  }

  // Aggregate and calculate the biggest gainers stocks
  def getOverallBiggestGainers(start: Long, end: Long): List[(StockData, Double)] = {
    // Call each individual industry, then aggregate them.
    getOverallChange(start, end).take(50) // Take the biggest losers
  }

  // Aggregate and calculate the biggest losers stocks
  def getOverallChange(start: Long, end: Long): List[(StockData, Double)] = {
    // Call each individual industry, then aggregate them.
    val stocks = aggregateGetters(start, end)
    stocks.map{ s =>
      val cur = s.sortBy(s => s.lastTrade).take(51)
      val previous = cur.takeRight(50)
      // Pair of a stock object and the average change in the stocks
      (
      s.head,
      (cur.take(50) zip previous).map( p =>
        Math.abs(p._1.lastTrade-p._2.lastTrade)
      ).sum/50.0
      )
    }.sortBy(i => i._2)
  }

  def aggregateGetters(start: Long, end: Long): List[List[StockData]] = {
    // Get list of stock listings
    val stocks = databaseDriver.getUSStockListings
    // Iterate through the stock listings, get updated data and insert
    stocks.map( s =>
       getData(s.sector, start, end, s.symbol, s.exchange)
    ).toList
  }

  /**
    * Insert Data is a utility to aid in inserting stock data into the right collection
    *
    */
  private def getData(
    sector: String,
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] = {

    sector match {
      case "Basic Industries" => industryController
        .getBasicIndustriesMain(startTime, endTime, symbol, exchange)
      case "Capital Goods" => industryController
        .getCapitalGoodsMain(startTime, endTime, symbol, exchange)
      case "Consumer Durables" => industryController
        .getConsumerDurablesMain(startTime, endTime, symbol, exchange)
      case "Consumer Non-Durables" => industryController
        .getConsumerNonDurablesMain(startTime, endTime, symbol, exchange)
      case "Consumer Services" => industryController
        .getConsumerServicesMain(startTime, endTime, symbol, exchange)
      case "Energy" => industryController
        .getEnergyMain(startTime, endTime, symbol, exchange)
      case "Finance" => industryController
        .getFinanceMain(startTime, endTime, symbol, exchange)
      case "Health Care" => industryController
        .getHealthCareMain(startTime, endTime, symbol, exchange)
      case "Miscellaneous" => industryController
        .getMiscellaneousMain(startTime, endTime, symbol, exchange)
      case "n/a" => industryController
        .getOtherMain(startTime, endTime, symbol, exchange)
      case "Public Utilities" => industryController
        .getPublicUtilitiesMain(startTime, endTime, symbol, exchange)
      case "Technology" => industryController
        .getTechnologyMain(startTime, endTime, symbol, exchange)
      case "Transportation" => industryController
        .getTransportationMain(startTime, endTime, symbol, exchange)
    }

  }

}

/**
  * Companion object for the MarketController class
  */
object MarketController {

  /**
    * Factory constructor method for the MarketController driver
    *
    * @return a new instance of MarketController
    */
  def apply(): MarketController = new MarketController()
}
