package com.flashboomlet.portfolio

import com.flashboomlet.controls.BankTeller
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
  val bac = new BankTeller


  // contract=[type of contract (put/call)]

  def trade(uid: String, market: String, symbol: String, units: Int, contract: String): Int = {
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

  def trade(uid: String, transactionID: Long): Int = {
    // Magic to figure out contract end date.
    val data = pc.getInvestment(uid, transactionID)
    val contract = pc.getInvestment(uid, transactionID)
    val currentStock = mc.getStock(contract.symbol)
    val sale = PortfolioData(
      contract.uid,
      contract.transactionId,
      contract.market,
      contract.symbol,
      contract.units,
      contract.purchaseDate,
      contract.purchasePrice,
      du.getNowInMillis,
      currentStock.lastTrade,
      contract.contractType,
      contract.contractEndDate
    )
    sell(sale)
  }

  private def sell(sale: PortfolioData): Int = {
    val cost = sale.sellPrice * sale.units
    if(marketOpen(sale.market)){
      if(validUnitCount(sale)) {
        bac.bankTeller(sale.uid, cost, 0, "checking")
        submitSale(sale)
        1
      } else {
        3
      }
    } else 2
  }

  def validUnitCount(sale: PortfolioData): Boolean = {
    pc.validUnitCount(sale)
  }

  def validContract(id: Long): Boolean = {
    pc.validContract(id)
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
