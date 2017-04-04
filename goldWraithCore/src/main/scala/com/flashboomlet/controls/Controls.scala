package com.flashboomlet.controls

import com.flashboomlet.charts.GraphStock
import com.flashboomlet.portfolio.PortfolioInfo
import com.flashboomlet.portfolio.Trade
import com.flashboomlet.stocks.MarketInfo

/**
  * Created by ttlynch on 3/29/17.
  */
class Controls {

  val portfolio = new PortfolioInfo
  val trader = new Trade
  val marketInfo = new MarketInfo
  val grapher = new GraphStock
  val generalInfo = new GeneralInformation

  /**
    * Get Market Prices is a function to get various data
    * about the market.
    *
    * Usage:
    *   market=[market]
    *   option=[option]
    *     Options:
    *       ma: Most Active
    *       bg: Biggest Gainers
    *       bl: Biggest Losers
    *       mv: Most Volatile
    *
    * @param options
    */
  def getMarketPrices(options: Array[String]): Unit = {
    val market = options.filter(p => p.contains("market")).head.split("=").last
    println("Market: " + market)
    val option = options.filter(p => p.contains("option")).head.split("=").last

    marketInfo.getMarketInfo(market, option)

  }

  /**
    * Get Portfolio prints out the users portfolio
    *
    * Usage:
    *   uid=[user id]
    *
    * Future:
    *   filter=[filter]
    *
    * @param options
    */
  def getPortfolio(options: Array[String]): Unit = {
    val uid = options.filter(p => p.contains("uid")).head.split("=").last.toInt

    portfolio.getInformation(uid)
  }

  /**
    * Get Quote gets information on a stock
    *
    * Usage:
    *   market=[market]
    *   symbol=[quote]
    *
    * Future:
    *   indicators=[indicators]
    *
    * @param options
    */
  def getQuote(options: Array[String]): Unit = {
    val market = options.filter(p => p.contains("market")).head.split("=").last
    val symbol = options.filter(p => p.contains("symbol")).head.split("=").last

    marketInfo.getQuote(market, symbol)
  }

  /**
    * Trade stock will allow a user to trade a stock
    *
    * Usage:
    *   uid=[user id]
    *   market=[market]
    *   symbol=[symbol]
    *   units=[units]
    *   contract=[type of contract (put/call)]
    *   action=[action (buy/sell)]
    *
    * @param options
    */
  def tradeStock(options: Array[String]): Unit = {
    val uid = options.filter(p => p.contains("uid")).head.split("=").last.toInt
    val market = options.filter(p => p.contains("market")).head.split("=").last
    val symbol = options.filter(p => p.contains("symbol")).head.split("=").last
    val units = options.filter(p => p.contains("units")).head.split("=").last.toInt
    val contract = options.filter(p => p.contains("contract")).head.split("=").last
    val action = options.filter(p => p.contains("action")).head.split("=").last

    if(action == "sell") {
      trader.sell(uid, market, symbol, units, contract)
    } else if(action == "buy") {
      trader.buy(uid, market, symbol, units, contract)
    } else {
      println("Your request was not processed!");
    }
  }

  /**
    * Graph Stock will graph a stock
    *
    * Usage:
    *   symbol=[symbol]
    *   start=[start date]
    *   end=[end date]
    *
    * Future:
    *   indicators=[indicators]
    *
    * @param options
    */
  def graphStock(options: Array[String]): Unit = {
    val symbol = options.filter(p => p.contains("symbol")).head.split("=").last
    val start = options.filter(p => p.contains("start")).head.split("=").last.toLong
    val end = options.filter(p => p.contains("end")).head.split("=").last.toLong

    grapher.GraphStock(symbol, start, end)
  }

  /**
    * System specific questions. Help with getting debug information
    *
    * @param options
    */
  def getSystemInformation(options: Array[String]): Unit = {
      // generalInfo.x
  }
}
