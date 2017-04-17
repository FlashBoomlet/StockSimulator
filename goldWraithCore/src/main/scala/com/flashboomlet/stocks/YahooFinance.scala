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
      val data = request.asString.body.toString.split("\",").toList.map(s => s.replaceAll("\"", ""))

      // logger.info(s"Yahoo fetched quote for: $quote, data: $data")

      val rtn = toStockData(data, listing)

      // logger.info(s"rtn: ${rtn.symbol} on ${data(3)} at ${data(4)} is logged at ${rtn.time}")

      rtn
    }.getOrElse(
      woops(quote)
    )
  }

  def getQuoteHistory(listing: StockListing, start: Long): List[StockData] = {

    val quote = listing.symbol
    val end = du.getNowInMillis

    Try {
      val url = (BaseApiPathHistory + "s=" + quote
      + s"&a=${du.dateToMonth(start)}&b=${du.dateToDay(start)}&c=${du.dateToYear(start)}" +
      s"&d=${du.dateToMonth(end)}&e=${du.dateToDay(end)}&f=${du.dateToYear(end)}"
      )
      val request: HttpRequest = Http(url)
      val data = request.asString.body.toString.split("\n").toList.map { i =>
        i.split(",").toList
      }.drop(1)
      // logger.info(s"URL: ${url}")
      // logger.info(s"Yahoo fetched quote for: $quote")

      // logger.info(s"\n\n${data}\n")

      data.map( s =>
        toStockDataHistory(s, listing)
      )
    }.getOrElse(
      List(woops(quote))
    )
  }

  private def woops(quote: String): StockData = {
    logger.error(s"Yahoo failed to get quote for $quote")
    nullStockData
  }

  private def toStockData(data: List[String], listing: StockListing): StockData = {
    val now = du.getNowInMillis
    //logger.info(s"Date check: ${du.dateandTimetoTime(data(3), data(4))}, ${data(3)}, ${data(4)}")
    val tmp = data(5).split(",").toList
    val rtn = StockData(
      listing.key, // key
      listing.name, // company
      listing.symbol, // symbol
      listing.exchange, // exchange
      if(data(3)!="N/A" && data(4)!="N/A") du.dateandTimetoTime(data(3), data(4)) else -1,
      if(tmp.head!="N/A") tmp.head.toDouble else -1, // Last Trade
      if(tmp(1)!="N/A") tmp(1).toDouble else -1, // open
      if(tmp(2)!="N/A") tmp(2).toDouble else -1, // Ask
      if(tmp(3)!="N/A") tmp(3).toDouble else -1, // Bid
      if(tmp(4)!="N/A") tmp(4).toDouble else -1, // volume
      if(tmp(5)!="N/A") tmp(5).toDouble else -1, // dayLow
      if(tmp(6)!="N/A") tmp(6).toDouble else -1, // dayHigh
      "yahoo",
      now
    )
    // logger.info(s"SD: $rtn")
    rtn
  }

  private def toStockDataHistory(data: List[String], listing: StockListing): StockData = {
    val now = du.getNowInMillis
    // (0)Date,(1)Open,(2)High,(3)Low,(4)Close,(5)Volume,(6)Adj Close
    // logger.info(s"Date: ${du.dateandTimetoTime(data.head, "0:0:0 AM")}")
    StockData(
      listing.key, // key
      listing.name, // company
      listing.symbol, // symbol
      listing.exchange, // exchange
      if(data.head!="N/A") du.dateandTimeoTimeHistory(data.head, "4:0:0 PM") else -1, // time
      if(data(4)!="N/A") data(4).toDouble else -1, // lastTrade
      if(data(1)!="N/A") data(1).toDouble else -1, // open
      0, // ask
      0, // bid
      if(data(5)!="N/A") data(5).toDouble else -1, // volume
      if(data(3)!="N/A") data(3).toDouble else -1, // dayLow
      if(data(2)!="N/A") data(2).toDouble else -1, // dayHigh
      "yahoo", // source
      now // fetchDate
    )
  }

  private def nullStockData: StockData = {
    StockData(-1, "","","", -1, -1, -1, -1, -1, -1, -1, -1, "", -1)
  }

}
