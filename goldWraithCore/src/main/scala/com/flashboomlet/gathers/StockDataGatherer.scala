package com.flashboomlet.gathers

import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.stocks.YahooFinance


/**
  * Created by ttlynch on 2/12/17.
  */
class StockDataGatherer(implicit val db: MongoDatabaseDriver) {

  val yahooFinance = new YahooFinance

  def gatherData(): Unit = {
    // Get list of stock listings
    val stocks = db.getUSStockListings
    // Iterate through the stock listings, get updated data and insert
    stocks.foreach( s =>
      insertData(getStockData(s.symbol, s.key), s.sector)
    )
  }

  def getStockData(symbol: String, key: Int): StockData = {
    yahooFinance.getQuote(symbol, key)
  }

  def insertData(sd: StockData, sector: String): Unit = {
    sector match{
      case "Basic Industries" => db.insertBasicIndustries(sd)
      case "Capital Goods" => db.insertCapitalGoods(sd)
      case "Consumer Durables" => db.insertConsumerDurables(sd)
      case "Consumer Non-Durables" => db.insertConsumerNonDurables(sd)
      case "Consumer Services" => db.insertConsumerServices(sd)
      case "Energy" => db.insertEnergy(sd)
      case "Finance" => db.insertFinance(sd)
      case "Health Care" => db.insertHealthCare(sd)
      case "Miscellaneous" => db.insertMiscellaneous(sd)
      case "n/a" => db.insertOther(sd)
      case "Public Utilities" => db.insertPublicUtilities(sd)
      case "Technology" => db.insertTechnology(sd)
      case "Transportation" => db.insertTransportation(sd)
      case _ => /* Shiza. We have yuge problems */
    }

  }

}
