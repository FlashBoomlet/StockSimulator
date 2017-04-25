package com.flashboomlet.charts

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.controllers.MarketController

/**
  * Created by ttlynch on 4/3/17.
  */
class GraphStock {

  val mc = new MarketController
  val db = new MongoDatabaseDriver
  val du = new DateUtil

  // In the future indicator options will also be included
  //   graph symbol=f start=1483254000000 end=1493142613028
  def GraphStock(symbol: String, start: Long, end: Long): Unit = {
    // Get Data for the graph.
    val data = getStockData(symbol, start, end)
    // Convert data to Chart Points
    //  data.map(p => ( (s"${du.dateToYear(p.time)}${du.dateToMonth(p.time)}${du.dateToDay(p.time)}").toDouble , p.lastTrade))
    //  data.map(p => (p.time.toDouble, p.lastTrade))
    val points = List(ChartPoint(
      symbol,
      '-',
      data.map(p => (p.time.toDouble, p.lastTrade))
    ))
    GenericChartPlotter.chartPointPlot(
      points,
      s"Stock data from $start to $end for $symbol",
      "Time (milliseconds)",
      "Price ($)",
      "",
      du.getNowInMillis)
  }

  // Return list[StockData] (not set or queue as order matters)
  private def getStockData(symbol: String, start: Long, end: Long): List[StockData] = {
    // TODO change the symbol to lower case
    val listing = db.getUSStockListing(symbol.toUpperCase())
    mc.getData(listing.sector, start, end, listing.symbol, listing.exchange)
  }
}
