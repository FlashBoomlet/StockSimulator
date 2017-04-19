package com.flashboomlet.portfolio

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.PortfolioData
import com.flashboomlet.db.controllers.MarketController
import com.flashboomlet.db.controllers.PortfolioController

/**
  * Created by ttlynch on 3/30/17.
  */
object Sell {

  val pc = new PortfolioController
  val mc = new MarketController
  val du = new DateUtil

  def trade(uid: Int, market: String, symbol: String, units: Int, contract: String): Int = {
    // Magic to figure out contract end date.
    val currentStock = mc.getStock(symbol)
    val currentPrice = currentStock.lastTrade
    val sale = PortfolioData(
      uid,
      pc.getTransactionID,
      market,
      symbol,
      units,
      du.getNowInMillis,
      currentPrice,
      -1,
      -1,
      contract,
      0
    )
    sell(sale)
  }

  private def sell(sale: PortfolioData): Int = {
    if(marketOpen(sale.market)){
      submitSale(sale)
      1
    } else 2
  }

  private def submitSale(sale: PortfolioData): Unit = {
    pc.updatePortfolioData(sale)
  }

  private def marketOpen(market: String): Boolean = {
    val now = du.getNowInMillis
    val hour = du.dateToHour(now)
    val morning = du.morning(now)
    if(market.toLowerCase() == "nyse"
    && (hour >= 9 && morning==1)
    && (hour < 4 && morning==0)){

      true
    } else if(market.toLowerCase() == "nasdaq"
    && (hour >= 9 && morning==1)
    && (hour < 4 && morning==0)){

      true
    } else false
  }
}
