package com.flashboomlet.stocks

import com.typesafe.scalalogging.LazyLogging
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



  def getQuote(quote: String): Unit = {
    Try {
      val request: HttpRequest = Http(BaseApiPath + "s=" + quote + "&f="
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
        + dayHigh )
      request.asString.body.toString.split(",").foreach( s => println(s) )

      logger.info(s"Fetched quote for: $quote\n")
    }.getOrElse(
      logger.info(s"Failed to get quote for $quote\n")
    )
  }

  def getHistory(
    quote: String,
    startDate: Long,
    endDate: Option[Long]): Unit = {

    Try {
      val request: HttpRequest = Http(BaseApiPath + "s=" + quote + "&f="
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
        + dayHigh )
      request.asString.body.toString.split(",").foreach( s => println(s) )

      logger.info(s"Fetched quote for: $quote\n")
    }.getOrElse(
      logger.info(s"Failed to get quote for $quote\n")
    )
  }

}
