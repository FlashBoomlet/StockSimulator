package com.flashboomlet.controls

/**
  * Created by ttlynch on 4/20/17.
  */
object ShellHelp {

  def allHelp(): Unit = {
    println("\nStock Shell Help:\n")

    println("stockShell:[request] [options...]")
    println("\tEx:\tstockShell:portfolio uid=ttlynch\n")
    println("stockShell:[request] help")
    println("\tEx:\tstockShell:portfolio help\n\n")
    println("\n")
    getMarketUsage()
    println("\n")
    getPortfolioUsage()
    println("\n")
    getQuoteUsage()
    println("\n")
    getTradeUsage()
    println("\n")
    getSellUsage()
    println("\n")
    getGraphUsage()
    println("\n")
    getBankUsage()
    println("\n")
    getAccountUsage()

  }


  def getMarketUsage(): Unit = {
    println("Request: market")
    println("\tGet various data on a market")
    println("\tEx:\tstockShell:portfolio start=04/01/17 end=04/07/2017 option=ma")
    println("Usage:")
    println("\tstart=[mm/dd/yyyy]")
    println("\tend=[mm/dd/yyyy] optional")
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
    println("\tEx:\tstockShell:portfolio uid=ttlynch")
    println("Usage:")
    println("\tuid=[user id]")
    println("getUserIDs")
    println("Future:")
    println("\tfilter=[filter]")
  }

  def getQuoteUsage(): Unit = {
    println("Request: quote")
    println("\t Get information on a stock symbol")
    println("\tEx:\tstockShell:quote market=nasdaq symbol=tsla")
    println("Usage:")
    println("\tmarket=[nyse/nasdaq]")
    println("\tsymbol=[quote]")
    println("Future:")
    println("\tindicators=[indicators]")
  }

  def getTradeUsage(): Unit  = {
    println("Option: trade")
    println("\tTrade a given stock based on the symbol")
    println("\tEx:\tstockShell:trade uid=ttlynch market=nasdaq symbol=tsla units=1 contract=call action=buy")
    println("Usage:")
    println("\tuid=[user id]")
    println("\tmarket=[market]")
    println("\tsymbol=[symbol]")
    println("\tunits=[units]")
    println("\tcontract=[type of contract (put/call)]")
    println("\taction=[action (buy/sell)]")
  }

  def getSellUsage(): Unit  = {
    println("Option: sell")
    println("\tTrade a given stock based on the symbol")
    println("\tEx:\tstockShell:sell uid=ttlynch transactionid=1")
    println("Usage:")
    println("\tuid=[user id]")
    println("\ttransactionid=[transactionID]")
  }

  def getGraphUsage(): Unit = {
    println("Option: graph")
    println("\tGraph a stock on a pop up graph")
    println("\tEx:\tstockShell:graph symbol=tsla start=04/01/17 end=04/07/2017")
    println("Usage:")
    println("\tsymbol=[symbol]")
    println("\tstart=[start date]")
    println("\tend=[end date]")
    println("Future:")
    println("\tindicators=[indicators]")
  }

  def getBankUsage(): Unit = {
    println("Option: bank")
    println("\tBank is a function to interact with ones bank account")
    println("\tEx:\tstockShell:bank uid=ttlynch deposit=100000.50 account=checking")
    println("Usage:")
    println("\tuid=UserID")
    println("\tdeposit=[Amount to deposit] optional")
    println("\twithdraw=[Amount to withdraw] optional")
    println("\taccount=[checking/saving] requried with either deposit or withdraw")
  }

  def getAccountUsage(): Unit = {
    println("Option: account")
    println("\tAccount is a function to create a bank account")
    println("\tEx:\tstockShell:account uid=ttlynch first=tyler last=lynch")
    println("Usage:")
    println("\tuid=UserID")
    println("\tfirst=[first name]")
    println("\tlast=[last name]")
  }


}
