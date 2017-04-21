package com.flashboomlet.portfolio

/**
  * Created by ttlynch on 3/30/17.
  * Specific Implementation for the Stock Shell
  */
class Trade {


  def sell(uid: String, market: String, symbol: String, units: Int, contract: String): Unit = {
    val trade = Sell.trade(uid, market, symbol, units, contract)
    if(trade == 1){
      println(s"Your sale for: $symbol has been made. ")
    } else if(trade == 2){
      println("Sorry, the market is not yet open.")
    } else {
      println(s"Your sale for: $symbol has NOT been made.")
    }
  }

  def buy(uid: String, market: String, symbol: String, units: Int, contract: String): Unit = {
    val trade = Buy.trade(uid, market, symbol, units, contract)
    if(trade == 1){
      println(s"Your purchase for: $symbol has been made. ")
    } else if(trade == 2){
      println("Sorry, the market is not yet open.")
    } else {
      println(s"Your purchase for: $symbol has NOT been made.")
    }
  }

}
