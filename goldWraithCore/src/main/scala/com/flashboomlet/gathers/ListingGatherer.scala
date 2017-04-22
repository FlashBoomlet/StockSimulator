package com.flashboomlet.gathers

import java.io.File
import java.net.URL

import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Await
import scala.concurrent.duration
import scala.concurrent.duration.Duration
import scala.util.Random
import sys.process._
import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.StockListing
import com.flashboomlet.db.MongoDatabaseDriver
import com.github.tototoshi.csv.CSVReader
import com.github.tototoshi.csv.TSVFormat
import com.github.tototoshi.csv.DefaultCSVFormat


/**
  * Created by ttlynch on 2/12/17.
  */
class ListingGatherer(implicit val db: MongoDatabaseDriver) extends LazyLogging {

  /** TSV Formatter */
  implicit object LocalTSVFormat extends TSVFormat

  /** CSV Formatter */
  implicit object LocalCSVFormat extends DefaultCSVFormat

  // http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NYSE&render=download
  // http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NASDAQ&render=download
  val baseNasdaq = "http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange="
  val endNasdaq = "&render=download"
  val nyse = "NYSE"
  val nasdaq = "NASDAQ"
  val downloadLocation = "DownloadedContent/"
  val du = new DateUtil

  /**
    * getListings is used to retrieve listings in the US Stock Markets. It then parses and inserts
    * them into the proper collection
    */
  def getListings(): Unit = {
    // Download files to the appropriate location
    fileDownloader((baseNasdaq + nyse + endNasdaq), ("nyse_listings.csv"))
    fileDownloader((baseNasdaq + nasdaq + endNasdaq), ("nasdq_listings.csv"))
    // Populate and update database
    populateListings(downloadLocation + "nyse_listings.csv", nyse)
    populateListings(downloadLocation + "nasdq_listings.csv", nasdaq)

    // Update Keys and ensure that the keys are sorted by order as to not mess up previously set keys.
    (db.getUSStockListings.sortBy(s => s.key) zipWithIndex)
      .foreach{ l =>
        db.updateUSStockListing(
          StockListing(
            l._2,
            l._1.symbol.toLowerCase(),
            l._1.name,
            l._1.lastSale,
            l._1.marketCap,
            l._1.ipoYear,
            l._1.sector,
            l._1.industry,
            l._1.summaryQuote,
            l._1.exchange.toLowerCase(),
            l._1.lastUpdate,
            l._1.lastDataFetch,
            l._1.valid
          )
        )
    }
  }

  /**
    * File Downloader is used to download files to temporary storage to update the stock listings
    * @param url the url to gather the data from
    * @param filename the file name to save the data gathered from
    */
  def fileDownloader(url: String, filename: String): Unit = {
    new URL(url) #> new File(downloadLocation, filename) !!
  }

  /**
    * Clear Listings is a temporary function to clear the listings out of the db for unit testing.
    */
  def clearListings(): Unit = {
    db.clearAllListings
  }

  def fetchListings(): List[StockListing] = {
    db.getUSStockListings
  }

  /**
    * Populate Listings calls to parse the listings retrieved. It then takes the results and
    * populates the collection of listings
    *
    * @param fileLocation the file to be parsed
    */
  private def populateListings(fileLocation: String, exchange: String): Unit = {
    parseListings(fileLocation, exchange).foreach{ l =>
       db.insertUSStockListing(l)
    }
  }

  /**
    * ParseListings loads a CSV of listings, parse them into a StockListing object
    *
    * @param fileLocation the file to be parsed
    * @return
    */
  private def parseListings(fileLocation: String, exchange: String): Vector[StockListing] = {

    val now = du.getNowInMillis
    getClass.getClassLoader.getResourceAsStream(fileLocation)
    val results = CSVReader.open(fileLocation)(LocalCSVFormat).all()
    val size = results.size
    results.takeRight(size-1).map{ l =>
      StockListing (
        0,
        l.head.toString,
        l(1).toString,
        if(l(2).toString == "n/a") 0 else l(2).toDouble.toLong, // LastSale
        if(l(3).toString == "n/a") 0 else l(3).toDouble.toLong, // MarketCap
        if(l(5).toString == "n/a") 0 else l(5).toInt, // IPO Year
        l(6).toString,
        l(7).toString,
        l(8).toString,
        exchange,
        now,
        0,
        true
      )
    }.toVector

  }
}
