package com.flashboomlet.gathers

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.StockData
import com.flashboomlet.data.StockListing
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
  val du = new DateUtil

  /**
    * GatherData is a trigger to gather data from all sources
    */
  def gatherData(): Unit = {
    // Get list of stock listings
    val stocks = db.getUSStockListings.filter(sl => sl.valid)
    // Iterate through the stock listings, get updated data and insert
    stocks.foreach { s =>
      val lastUpdated = s.lastDataFetch
      val stockData = getStockData(s, lastUpdated)
      if (stockData.key != -1) {
        // Updated Stock Listings with the time that it was last gathered (i.e. the time on the stockdata)
        updateStockListing(stockData.time, s)

        // Insert The Data
        insertData(stockData, s.sector)
      } else {
        disableStock(s)
      }
    }
  }


  /**
    * GatherDataHistory is a trigger to gather data from all sources
    */
  def gatherDataHistory(): Unit = {
    // Get list of stock listings
    val stocks = db.getUSStockListings.filter(sl => sl.valid)
    // Iterate through the stock listings, get updated data and insert
    stocks.foreach { s =>
      val lastUpdated = s.lastDataFetch
      val stockData = getStockDataHistory(s, lastUpdated)
      stockData.foreach { sd =>
        if (sd.key != -1) {
          // Updated Stock Listings with the time that it was last gathered (i.e. the time on the stockdata)
          updateStockListing(sd.time, s)

          // Insert The Data
          insertData(sd, s.sector)
        } else {
          disableStock(s)
        }
      }
    }
  }

  private def disableStock(s: StockListing): Unit = {
    db.updateUSStockListing(
      StockListing(
        s.key,
        s.symbol,
        s.name,
        s.lastSale,
        s.marketCap,
        s.ipoYear,
        s.sector,
        s.industry,
        s.summaryQuote,
        s.exchange,
        s.lastUpdate,
        s.lastDataFetch,
        false
      )
    )
  }

  /**
    * Update Stock Listing is to update a stock listing with when the stock was last gathered.
    *
    * @param stockDate the date of which the data was updated
    * @param listing stock listing to update
    */
  private def updateStockListing(stockDate: Long, listing: StockListing): Unit = {
    db.updateUSStockListing(
      StockListing(
        listing.key,
        listing.symbol,
        listing.name,
        listing.lastSale,
        listing.marketCap,
        listing.ipoYear,
        listing.sector,
        listing.industry,
        listing.summaryQuote,
        listing.exchange,
        du.getNowInMillis,
        stockDate,
        listing.valid
      )
    )
  }

  /**
    * Get Stock Data can utilize the various sources of data to obtain stock data
    *
    * @param listing the listing for the stock
    * @return a stockData
    */
  private def getStockData(
    listing: StockListing,
    start: Long): StockData = {

    yahooFinance.getQuote(listing, start)
  }

  /**
    * Get Stock Data can utilize the various sources of data to obtain stock data
    *
    * @param listing the listing for the stock
    * @return a stockData
    */
  private def getStockDataHistory(
    listing: StockListing,
    start: Long): List[StockData] = {

    yahooFinance.getQuoteHistory(listing, start)
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


