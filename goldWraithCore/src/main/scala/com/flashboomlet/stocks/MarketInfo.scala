package com.flashboomlet.stocks

import com.flashboomlet.data.DateUtil
import com.flashboomlet.db.controllers.MarketController

/**
  * Created by ttlynch on 3/30/17.
  */
class MarketInfo {

  val mc = new MarketController
  val du = new DateUtil

  def getQuote(market: String, symbol: String): Unit = {
    val stock = mc.getStock(symbol)
    println(s"Raw stock: $stock")
    println(s"\n${stock.symbol} at ${stock.time}:")
    println(s"\tprice: ${stock.lastTrade}")
    println(s"\task: ${stock.ask}\tbid: ${stock.bid}")
    println(s"\trange: [${stock.dayLow}, ${stock.dayHigh}]")
    println(s"\tvolume: ${stock.volume}")
    println(s"\topen: ${stock.open}")
    println(s"\texchange: ${stock.exchange}\n")
  }

  /**
    * ma: Most Active
    * bg: Biggest Gainers
    * bl: Biggest Losers
    * mv: Most Volatile
    *
    */
  def getMarketInfo(start: String, end: String, option: String): Unit = {
    val tmpStart = start.split("/")
    val sMonth = tmpStart.head.toInt
    val sDay = tmpStart(1).toInt
    val sYear = tmpStart(2).toInt
    val startDate = du.formattedTimeToMillis(sYear, sMonth, sDay, 0, 0, 0, 0)

    val tmpEnd = end.split("/")
    val eMonth = if(tmpEnd.length > 0) tmpEnd.head.toInt else 0
    val eDay = if(tmpEnd.length > 0) tmpEnd(1).toInt else 0
    val eYear = if(tmpEnd.length > 0) tmpEnd(2).toInt else 0
    val endDate = du.formattedTimeToMillis(eYear, eMonth, eDay, 4, 0, 0, 1)

    if(option == "ma"){
      getMostActive(startDate, endDate)
    } else if(option == "bg"){
      getBiggestGainers(startDate, endDate)
    } else if(option == "bl"){
      getBiggestLosers(startDate, endDate)
    } else if(option == "mv"){
      getMostVolatile(startDate, endDate)
    } else println(s"Well it looks like you submitted an invalid option: $option")
  }

  private def getMostActive(start: Long, end: Long): Unit = {
    println("Most Active stocks:")
    mc.getOverallMostActive(start, end).foreach(println)
  }

  private def getMostVolatile(start: Long, end: Long): Unit = {
    println("Most Active Volatile:")
    mc.getOverallMostVolatile(start, end).foreach(println)
  }

  private def getBiggestGainers(start: Long, end: Long): Unit = {
    println("Most Biggest Gainers:")
    mc.getOverallBiggestGainers(start, end).foreach(println)
  }

  private def getBiggestLosers(start: Long, end: Long): Unit = {
    println("Most Biggest Losers:")
    mc.getOverallBiggestLosers(start, end).foreach(println)
  }

}
