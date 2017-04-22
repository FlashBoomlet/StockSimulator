package com.flashboomlet.portfolio

/**
  * Created by ttlynch on 3/30/17.
  * Specific Implementation for the Stock Shell
  */
class Trade {


  def sell(uid: String, market: String, symbol: String, units: Int, contract: String): String = {
    val trade = Sell.trade(uid, market, symbol, units, contract)
    if(trade == 1){
      (s"Your sale for: $symbol has been made. \n")
    } else if(trade == 2){
      ("Sorry, the market is not yet open.\\n")
    } else if(trade == 3){
      ("Sorry, you don't have that many units available to sell\\n")
    } else {
      (s"Your sale for: $symbol has NOT been made.\n")
    }
  }


  def sell(uid: String, transactionID: Long): String = {
    if(Sell.validContract(transactionID)){
      val trade = Sell.trade(uid, transactionID)
      (s"Your sale has been made. \n")
    } else {
      (s"Your sale has NOT been made.\n")
    }
  }

  def buy(uid: String, market: String, symbol: String, units: Int, contract: String): String = {
    val trade = Buy.trade(uid, market, symbol, units, contract)
    if(trade == 1){
      (s"Your purchase for: $symbol has been made. \n")
    } else if(trade == 2){
      ("Sorry, the market is not yet open.\\n")
    } else {
      (s"Your purchase for: $symbol has NOT been made.\n")
    }
  }

}
