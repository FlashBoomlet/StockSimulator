package com.flashboomlet.gathers

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.StockListing
import com.github.tototoshi.csv.CSVReader
import com.github.tototoshi.csv.TSVFormat
import com.github.tototoshi.csv.DefaultCSVFormat


/**
  * Created by ttlynch on 2/12/17.
  */
object ListingGatherer {

  /** TSV Formatter */
  implicit object LocalTSVFormat extends TSVFormat

  /** CSV Formatter */
  implicit object LocalCSVFormat extends DefaultCSVFormat

  // http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NYSE&render=download
  // http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange=NASDAQ&render=download

  //   0        1       2         3           4          5        6           7           8
  // "Symbol","Name","LastSale","MarketCap","ADR TSO","IPOyear","Sector","Industry","Summary Quote",
  // Want: 0,1,3,5,6,7,8

  val baseNasdaq = "http://www.nasdaq.com/screening/companies-by-industry.aspx?exchange="
  val endNasdaq = "&render=download"
  val nyse = "NYSE"
  val nasdaq = "NASDAQ"

  val downloadLocation = "/"

  def populateListings(): Unit = {
    // Download CSV to location
    // Load the CSV and Parse
    // Update DB
  }

  /** Loads clean names */
  def parseListings(fileName: String): Vector[StockListing] = {

    val now = DateUtil.getNowInMillis
    ListingGatherer.getClass.getClassLoader.getResourceAsStream(fileName)
    CSVReader.open(fileName)(LocalTSVFormat).all().map{ l =>
      StockListing (
        l.head.toString,
        l(1).toString,
        l(2).toLong,
        l(3).toLong,
        l(4).toInt,
        l(5).toString,
        l(6).toString,
        l(7).toString,
        l(8).toString,
        now
      )

    }.toVector
  }
}
