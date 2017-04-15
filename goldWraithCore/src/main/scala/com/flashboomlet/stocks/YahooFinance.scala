package com.flashboomlet.stocks

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.StockData
import com.flashboomlet.data.StockData
import com.flashboomlet.data.StockListing
import com.typesafe.scalalogging.LazyLogging
import org.jfree.data.DataUtilities
import scala.util.Try
import scalaj.http.Http
import scalaj.http.HttpRequest

/**
  * Created by ttlynch on 10/29/16.
  *
  * Great resource"
  *   http://www.jarloo.com/yahoo_finance/
  *   15 Minute Delay for NASDAQ
  *   S & P - Real Time...
  *
  * Symbol List:"
  *   http://www.buyupside.com/calculators/yahooindexsymbols.htm
  *
  * For historical data:
  *   https://greenido.wordpress.com/2009/12/22/work-like-a-pro-with-yahoo-finance-hidden-api/
  *   http://stackoverflow.com/questions/754593/source-of-historical-stock-data
  */
class YahooFinance extends LazyLogging {

  private val BaseApiPath: String = "http://download.finance.yahoo.com/d/quotes.csv?"
  private val BaseApiPathHistory: String = "http://ichart.finance.yahoo.com/table.csv?"
  val du = new DateUtil

  val company = "n"
  val symbol = "s"
  val exchange = "x"
  val date = "d1"
  val time = "t1"
  val lastTrade = "l1"
  val open = "o"
  val ask = "a"
  val bid = "b"
  val volume = "v"
  val dayLow = "g"
  val dayHigh = "h"



  def getQuote(listing: StockListing, start: Long): StockData = {
    val quote = listing.symbol
    val end = du.getNowInMillis

    Try {
      val url = (BaseApiPath + "s=" + quote + "&f="
      + company
      + symbol
      + exchange
      + date
      + time
      + lastTrade
      + open
      + ask
      + bid
      + volume
      + dayLow
      + dayHigh
      )
      val request: HttpRequest = Http(url)
      val data = request.asString.body.toString.split(",").toList

      logger.info(s"Yahoo fetched quote for: $quote")


      val rtn = toStockData(data, listing)

      logger.info(s"${rtn.symbol} on ${data(3)} at ${data(4)} is logged at ${rtn.time}")

      rtn
    }.getOrElse(
      woops(quote)
    )
  }

  def getQuoteHistory(listing: StockListing, start: Long): List[StockData] = {

    val quote = listing.symbol
    val end = du.getNowInMillis

    Try {
      val url = (BaseApiPath + "s=" + quote
      + s"&a=${du.dateToMonth(start)}&b=${du.dateToDay(start)}&c=${du.dateToYear(start)}" +
      s"&d=${du.dateToMonth(end)}&e=${du.dateToDay(end)}&f=${du.dateToYear(end)}"
      )
      val request: HttpRequest = Http(url)
      val data = request.asString.body.toString.split("\n").toList.map { i =>
        i.split(",").toList
      }

      logger.info(s"Yahoo fetched quote for: $quote")

      data.map( s =>
        toStockDataHistory(s, listing)
      )

    }.getOrElse(
      List(woops(quote))
    )
  }

  private def woops(quote: String): StockData = {
    // logger.info(s"Yahoo failed to get quote for $quote\n")
    nullStockData
  }

  private def toStockData(data: List[String], listing: StockListing): StockData = {
    val now = du.getNowInMillis
    StockData(
      listing.key, // key
      listing.name, // company
      listing.symbol, // symbol
      listing.exchange, // exchange
      du.dateandTimetoTime(data(3), data(4)),
      data(5).toLong,
      data(6).toLong,
      data(7).toLong,
      data(8).toLong,
      data(9).toLong,
      data(10).toLong,
      data(11).toLong,
      "yahoo",
      now
    )
  }

  private def toStockDataHistory(data: List[String], listing: StockListing): StockData = {
    val now = du.getNowInMillis
    // (0)Date,(1)Open,(2)High,(3)Low,(4)Close,(5)Volume,(6)Adj Close
    StockData(
      listing.key, // key
      listing.name, // company
      listing.symbol, // symbol
      listing.exchange, // exchange
      du.dateandTimetoTime(data.head, ""), // time
      data(4).toLong, // lastTrade
      data(1).toLong, // open
      0, // ask
      0, // bid
      data(5).toLong, // volume
      data(3).toLong, // dayLow
      data(2).toLong, // dayHigh
      "yahoo", // source
      now // fetchDate
    )
  }

  private def nullStockData: StockData = {
    StockData(-1, "","","", -1, -1, -1, -1, -1, -1, -1, -1, "", -1)
  }

}
