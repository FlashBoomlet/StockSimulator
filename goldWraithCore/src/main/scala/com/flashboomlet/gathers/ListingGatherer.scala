package com.flashboomlet.gathers

import java.io.File
import java.net.URL
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
class ListingGatherer(implicit val db: MongoDatabaseDriver) {

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

  /**
    * getListings is used to retrieve listings in the US Stock Markets. It then parses and inserts
    * them into the proper collection
    */
  def getListings(): Unit = {
    // Download files to the appropriate location
    new URL(baseNasdaq + nyse + endNasdaq) #> new File(downloadLocation + "nyse_listings.csv")
    new URL(baseNasdaq + nasdaq + endNasdaq) #> new File(downloadLocation + "nasdq_listings.csv")
    // Populate and update database
    populateListings(downloadLocation + "nyse_listings.csv")
    populateListings(downloadLocation + "nasdq_listings.csv")
  }

  /**
    * Populate Listings calls to parse the listings retrieved. It then takes the results and
    * populates the collection of listings
    *
    * @param fileLocation the file to be parsed
    */
  private def populateListings(fileLocation: String): Unit = {
    parseListings(fileLocation).foreach( l =>
      db.insertUSStockListing(l)
    )
  }

  /**
    * ParseListings loads a CSV of listings, parse them into a StockListing object
    *
    * @param fileLocation the file to be parsed
    * @return
    */
  private def parseListings(fileLocation: String): Vector[StockListing] = {

    val now = DateUtil.getNowInMillis
    getClass.getClassLoader.getResourceAsStream(fileLocation)
    CSVReader.open(fileLocation)(LocalTSVFormat).all().map{ l =>
      StockListing (
        getNextKey(),
        l.head.toString,
        l(1).toString,
        l(2).toLong,
        l(3).toLong,
        l(4).toInt,
        l(5).toString,
        l(6).toString,
        l(7).toString,
        l(8).toString,
        now,
        0
      )
    }.toVector
  }

  /**
    * Private key management tool. This will be the primary collection. Thus, the keys are important
    * to be unique.
    * @return the max key thus far plus one.
    */
  private def getNextKey(): Int = {
    val maxKey = db.getMaxKey
    if(maxKey < 1){
      // Use the StockListing Object as is
      1
    } else {
      // Use the Max Key
      maxKey + 1
    }
  }
}
