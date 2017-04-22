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
    } else if(trade == 3){
      println("Sorry, you don't have that many units available to sell")
    } else {
      println(s"Your sale for: $symbol has NOT been made.")
    }
  }


  def sell(uid: String, transactionID: Long): Unit = {
    if(Sell.validContract(transactionID)){
      val trade = Sell.trade(uid, transactionID)
      println(s"Your sale has been made. ")
    } else {
      println(s"Your sale has NOT been made.")
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
