package com.flashboomlet.controls

import scala.io.StdIn

/**
  * Run and Interact with the program
  */
object StockShell {

  val controls = new Controls()

  def main(args: Array[String]) {
    StockShell()
  }

  def StockShell(): Unit = {
    var line = ""
    while ({line = StdIn.readLine("stockShell:"); line != null}) {
      // Continuous loop
      parseLine(line)
    }
  }

  def parseLine(input: String): String = {
    val tokenized = input.split(" ")
    val request = tokenized.head
    val options = tokenized.takeRight(tokenized.length-1)


    request match {
      case "portfolio" => controls.getPortfolio(options)
      case "quote" => controls.getQuote(options)
      case "graph" => controls.graphStock(options)
      case "trade" => controls.tradeStock(options)
      case "market" => controls.getMarketPrices(options)
      case "system" => controls.getSystemInformation(options)
      case _ => help()
    }
    return ""
  }

  def help(): Unit = {
    println("\nStock Shell Help:\n")

    println("stockShell:[request] [options...]")
    println("\tEx:\tstockShell:portfolio uid=ttlynch\n\n")
    getMarketUsage()
    println("\n")
    getPortfolioUsage()
    println("\n")
    getQuoteUsage()
    println("\n")
    getTradeUsage()
    println("\n")
    getGraphUsage()
  }

  def getMarketUsage(): Unit = {
    println("Request: market")
    println("\tGet various data on a market")
    println("Usage:")
    println("\tmarket=[nyse/nasdaq]")
    println("\toption=[option]")
    println("\t\tOptions:")
    println("\t\t\tma: Most Active")
    println("\t\t\tbg: Biggest Gainers")
    println("\t\t\tbl: Biggest Losers")
    println("\t\t\tmv: Most Volatile")
  }

  def getPortfolioUsage(): Unit = {
    println("Request: portfolio")
    println("\tGet users portfolio information")
    println("Usage:")
    println("\tuid=[user id]")
    println("getUserIDs")
    println("Future:")
    println("\tfilter=[filter]")
  }

  def getQuoteUsage(): Unit = {
    println("Request: quote")
    println("\t Get information on a stock symbol")
    println("Usage:")
    println("\tstart=[mm/dd/yyyy]")
    println("\tend=[mm/dd/yyyy] optional")
    println("\tsymbol=[quote]")
    println("Future:")
    println("\tindicators=[indicators]")
  }

  def getTradeUsage(): Unit  = {
    println("Option: trade")
    println("\tTrade a given stock based on the symbol")
    println("Usage:")
    println("\tuid=[user id]")
    println("\tmarket=[market]")
    println("\tsymbol=[symbol]")
    println("\tunits=[units]")
    println("\tcontract=[type of contract (put/call)]")
    println("\taction=[action (buy/sell)]")
  }

  def getGraphUsage(): Unit = {
    println("Option: graph")
    println("\tGraph a stock on a pop up graph")
    println("Usage:")
    println("\tsymbol=[symbol]")
    println("\tstart=[start date]")
    println("\tend=[end date]")
    println("Future:")
    println("\tindicators=[indicators]")
  }

}
