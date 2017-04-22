package com.flashboomlet.controls

import com.flashboomlet.charts.GraphStock
import com.flashboomlet.data.BankAccount
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.controllers.MarketController
import com.flashboomlet.portfolio.PortfolioInfo
import com.flashboomlet.portfolio.Trade
import com.flashboomlet.stocks.MarketInfo

import scala.io.StdIn

/**
  * Created by ttlynch on 3/29/17.
  */
class Controls {

  val portfolio = new PortfolioInfo
  val trader = new Trade
  val marketInfo = new MarketInfo
  val grapher = new GraphStock
  val generalInfo = new GeneralInformation
  val bankAccountInfo = new BankTeller
  val mdd = new MongoDatabaseDriver


  /**
    * Bank is a function to interact with ones bank account
    *
    * Usage:
    *   uid=UserID
    *   deposit=[Amount to deposit] optional
    *   withdraw=[Amount to withdraw] optional
    *   account=[checking/saving] requried with either deposit or withdraw
    *
    * @param options
    */
  def bank(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getBankUsage()
    }
    val uid = options.filter(p => p.toLowerCase().contains("uid")).head.split("=").last
    val deposit = if(options.exists(p => p.toLowerCase().contains("deposit"))) {
      options.filter(p => p.toLowerCase().contains("deposit")).head.split("=").last.toDouble
    } else 0
    val withdraw = if(options.exists(p => p.toLowerCase().contains("withdraw"))) {
      options.filter(p => p.toLowerCase().contains("withdraw")).head.split("=").last.toDouble
    } else 0
    val account = if(options.exists(p => p.toLowerCase().contains("account"))) {
      options.filter(p => p.toLowerCase().contains("account")).head.split("=").last
    } else ""

    bankAccountInfo.bankTeller(uid, deposit, withdraw, account)
  }

  /**
    * Account is a function to create a bank account
    *
    * Usage:
    *   uid=UserID
    *   first=[first name]
    *   last=[last name]
    *
    * @param options
    */
  def account(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getAccountUsage()
    }
    val uid = options.filter(p => p.toLowerCase().contains("uid")).head.split("=").last
    val first = options.filter(p => p.toLowerCase().contains("first")).head.split("=").last
    val last = options.filter(p => p.toLowerCase().contains("last")).head.split("=").last

    bankAccountInfo.createAccount(uid, first, last)
  }

  /**
    * Get Market Prices is a function to get various data
    * about the market.
    *
    * Usage:
    *   start=[mm/dd/yyyy]
    *   end=[mm/dd/yyyy] optional
    *   option=[option]
    *     Options:
    *       ma: Most Active
    *       bg: Biggest Gainers
    *       bl: Biggest Losers
    *       mv: Most Volatile
    *
    * @param options
    */
  def getMarketPrices(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getMarketUsage()
    }
    val start = options.filter(p => p.toLowerCase().contains("start")).head.split("=").last
    val end = options.filter(p => p.toLowerCase().contains("end")).head.split("=").last
    val option = options.filter(p => p.toLowerCase().contains("option")).head.split("=").last

    marketInfo.getMarketInfo(start, end, option)

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
  def getPortfolio(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getPortfolioUsage()
    }
    if(options.map(s => s.toLowerCase()).exists(s => s.contains("getuserids"))){
      portfolio.getUsers()
    } else {
      val uid = options.filter(p => p.toLowerCase().contains("uid")).head.split("=").last
      portfolio.getInformation(uid)
    }
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
  def getQuote(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getQuoteUsage()
    }
    val market = options.filter(p => p.toLowerCase().contains("market")).head.split("=").last
    val symbol = options.filter(p => p.toLowerCase().contains("symbol")).head.split("=").last

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
  def tradeStock(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getTradeUsage()
    }
    val uid = options.filter(p => p.toLowerCase().contains("uid")).head.split("=").last
    val market = options.filter(p => p.toLowerCase().contains("market")).head.split("=").last
    val symbol = options.filter(p => p.toLowerCase().contains("symbol")).head.split("=").last
    val units = options.filter(p => p.toLowerCase().contains("units")).head.split("=").last.toInt
    val contract = options.filter(p => p.toLowerCase().contains("contract")).head.split("=").last
    val action = options.filter(p => p.toLowerCase().contains("action")).head.split("=").last

    if(action == "sell") {
      trader.sell(uid, market, symbol, units, contract)
    } else if(action == "buy") {
      trader.buy(uid, market, symbol, units, contract)
    } else {
      ("Your request was not processed!\n")
    }
  }

  /**
    * Option: sell
    *   Trade a given stock based on the symbol
    *   Ex:\tstockShell:sell transactionid=1
    * Usage:
    *   uid=[user id]
    *   transactionid=[transactionID]
    */
  def sell(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getTradeUsage()
    }
    val uid = options.filter(p => p.toLowerCase().contains("uid")).head.split("=").last
    val transactionid = options.filter(p => p.toLowerCase().contains("transactionid")).head.split("=").last.toLong

    trader.sell(uid, transactionid)
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
  def graphStock(options: Array[String]): String = {
    if(options.head == "help"){
      return ShellHelp.getGraphUsage()
    }
    val symbol = options.filter(p => p.toLowerCase().contains("symbol")).head.split("=").last
    val start = options.filter(p => p.toLowerCase().contains("start")).head.split("=").last.toLong
    val end = options.filter(p => p.toLowerCase().contains("end")).head.split("=").last.toLong

    grapher.GraphStock(symbol, start, end)
    ""
  }

  /**
    * System specific questions. Help with getting debug information
    *
    * @param options
    */
  def getSystemInformation(options: Array[String]): String = {
      // generalInfo.x
      ""
  }

  def clearAll(): String = {
  /*
    println("\n*******************\n\nWarning!!!\n\n*******************\n")
    var line = StdIn.readLine("Are you sure you want to clear all data? (yes/no):")
    if(line == "yes") {
      println("\n*******************\n\nWarning!!!\n\n*******************\n")
      var line = StdIn.readLine("Please confirm that you'd like to delete all your data? (yes/no):")
      if(line == "yes"){
        mdd.clearAll()
      }
    }
    */
    ""
  }

  def clearPortfolio(): String = {
    /*
    println("\n*******************\n\nWarning!!!\n\n*******************\n")
    var line = StdIn.readLine("Are you sure you want to clear your portfolio? (yes/no):")
    if(line == "yes")
    */
    mdd.clearPortfolio()
    ""
  }


}
