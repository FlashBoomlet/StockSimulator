package com.flashboomlet.stocks

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.StockData
import com.flashboomlet.data.StockData
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



  def getQuote(quote: String, key: Int): StockData = {
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
      val data = request.asString.body.toString.split(",").toList

      logger.info(s"Yahoo fetched quote for: $quote\n")
      toStockData(data, key)

    }.getOrElse {
      logger.info(s"Yahoo failed to get quote for $quote\n")
      nullStockData
    }
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

  private def toStockData(data: List[String], key: Int): StockData = {
    val now = DateUtil.getNowInMillis
    StockData(
      key,
      data.head,
      data(1),
      data(2),
      DateUtil.dateandTimetoTime(data(3), data(4)),
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

  private def nullStockData: StockData = {
    StockData(-1, "","","", -1, -1, -1, -1, -1, -1, -1, -1, "", -1)
  }

}
