package com.flashboomlet.gathers

import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.queries.IndustryController
import com.flashboomlet.stocks.YahooFinance


/**
  * Created by ttlynch on 2/12/17.
  */
class StockDataGatherer(
  implicit val ic: IndustryController,
  implicit val db: MongoDatabaseDriver) {

  val yahooFinance = new YahooFinance

  /**
    * GatherData is a trigger to gather data from all sources
    */
  def gatherData(): Unit = { // TODO : Add framework for just getting a history of stock prices.
    // Get list of stock listings
    val stocks = db.getUSStockListings
    // Iterate through the stock listings, get updated data and insert
    stocks.foreach { s =>
      val stockData = getStockData(s.symbol, s.key)
      if( stockData.key != -1 ) {
        insertData(stockData, s.sector)
      }
    }
  }

  /**
    * Get Stock Data can utilize the various sources of data to obtain stock data
    *
    * @param symbol the symbol for which data should be fetched for
    * @param key the key of the symbol (a unique ID for quick filtering in mongodb)
    * @return a stockData
    */
  private def getStockData(symbol: String, key: Int): StockData = {
    yahooFinance.getQuote(symbol, key)
  }

  /**
    * Insert Data is a utility to aid in inserting stock data into the right collection
    *
    * @param sd the stock data
    * @param sector the sector that the stock data belongs to
    */
  private def insertData(sd: StockData, sector: String): Unit = {
    sector match{
      case "Basic Industries" => ic.insertBasicIndustriesMain(sd)
      case "Capital Goods" => ic.insertCapitalGoodsMain(sd)
      case "Consumer Durables" => ic.insertConsumerDurablesMain(sd)
      case "Consumer Non-Durables" => ic.insertConsumerNonDurablesMain(sd)
      case "Consumer Services" => ic.insertConsumerServicesMain(sd)
      case "Energy" => ic.insertEnergyMain(sd)
      case "Finance" => ic.insertFinanceMain(sd)
      case "Health Care" => ic.insertHealthCareMain(sd)
      case "Miscellaneous" => ic.insertMiscellaneousMain(sd)
      case "n/a" => ic.insertOtherMain(sd)
      case "Public Utilities" => ic.insertPublicUtilitiesMain(sd)
      case "Technology" => ic.insertTechnologyMain(sd)
      case "Transportation" => ic.insertTransportationMain(sd)
      case _ => /* Shiza. We have yuge problems */
    }

  }

}
