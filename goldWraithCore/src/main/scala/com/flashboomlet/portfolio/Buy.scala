package com.flashboomlet.portfolio

import com.flashboomlet.data.DateUtil
import com.flashboomlet.data.PortfolioData
import com.flashboomlet.db.controllers.MarketController
import com.flashboomlet.db.controllers.PortfolioController

/**
  * Created by ttlynch on 3/30/17.
  */
object Buy {

  val pc = new PortfolioController
  val mc = new MarketController
  val du = new DateUtil

  def trade(uid: Int, market: String, symbol: String, units: Int, contract: String): Int = {
    // Magic to figure out contract end date.
    val currentStock = mc.getStock(symbol)
    val currentPrice = currentStock.lastTrade
    val purchase = PortfolioData(
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
    buy(purchase)
  }

  private def buy(purchase: PortfolioData): Int = {
    if(marketOpen(purchase.market)){
      submitPurchase(purchase)
      1
    } else 2
  }

  private def submitPurchase(purchase: PortfolioData): Unit = {
    pc.insertPortfolioData(purchase)
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
