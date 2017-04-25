package com.flashboomlet.controls

/**
  * Created by ttlynch on 4/20/17.
  */
object ShellHelp {

  def allHelp(): String = {
    ("\nStock Shell Help:\n\n"
    + "stockShell:[request] [options...]\n"
    + "\tEx:\tstockShell:portfolio uid=ttlynch\n\n"
    + "stockShell:[request] help\n"
    + "\tEx:\tstockShell:portfolio help\n\n\n"
    + "\n\n"
    + getMarketUsage()
    + "\n\n"
    + getPortfolioUsage()
    + "\n\n"
    + getQuoteUsage()
    + "\n\n"
    + getTradeUsage()
    + "\n\n"
    + getSellUsage()
    + "\n\n"
    + getGraphUsage()
    + "\n\n"
    + getBankUsage()
    + "\n\n"
    + getAccountUsage()
    )
  }




  def getMarketUsage(): String = {
    (
    "Request: market\n"
    + "\tGet various data on a market\n"
    + "\tEx:\tstockShell:market start=04/01/17 end=04/07/2017 option=ma\n"
    + "Usage:\n"
    + "\tstart=[mm/dd/yyyy]\n"
    + "\tend=[mm/dd/yyyy] optional\n"
    + "\toption=[option]\n"
    + "\t\tOptions:\n"
    + "\t\t\tma: Most Active\n"
    + "\t\t\tbg: Biggest Gainers\n"
    + "\t\t\tbl: Biggest Losers\n"
    + "\t\t\tmv: Most Volatile\n"
    )
  }

  def getPortfolioUsage(): String = {
    (
    "Request: portfolio\n"
    + "\tGet users portfolio information\n"
    + "\tEx:\tstockShell:portfolio uid=ttlynch\n"
    + "Usage:\n"
    + "\tuid=[user id]\n"
    + "\n\tor\n\tgetUserIDs\n"
    + "Future:\n"
    + "\tfilter=[filter]\n"
    )
  }

  def getQuoteUsage(): String = {
    (
    "Request: quote\n"
    + "\t Get information on a stock symbol\n"
    + "\tEx:\tstockShell:quote market=NYSE symbol=AIR\n"
    + "Usage:\n"
    + "\tmarket=[nyse/nasdaq]\n"
    + "\tsymbol=[quote]\n"
    + "Future:\n"
    + "\tindicators=[indicators]\n"
    )
  }

  def getTradeUsage(): String  = {
    (
    "Option: trade\n"
    + "\tTrade a given stock based on the symbol\n"
    + "\tEx:\tstockShell:trade uid=ttlynch market=NYSE symbol=AIR units=1 contract=call action=buy\n"
    + "Usage:\n"
    + "\tuid=[user id]\n"
    + "\tmarket=[market]\n"
    + "\tsymbol=[symbol]\n"
    + "\tunits=[units]\n"
    + "\tcontract=[type of contract (put/call)]\n"
    + "\taction=[action (buy/sell)]\n"
    )
  }

  def getSellUsage(): String  = {
    (
    "Option: sell\n"
    + "\tTrade a given stock based on the symbol\n"
    + "\tEx:\tstockShell:sell uid=ttlynch transactionid=2\n"
    + "Usage:\n"
    + "\tuid=[user id]\n"
    + "\ttransactionid=[transactionID]\n"
    )
  }

  def getGraphUsage(): String = {
    (
    "Option: graph\n"
    + "\tGraph a stock on a pop up graph\n"
    + "\tEx:\tstockShell:graph symbol=f start=1483254000000 end=1493142613028\n"
    + "Usage:\n"
    + "\tsymbol=[symbol]\n"
    + "\tstart=[start date]\n"
    + "\tend=[end date]\n"
    + "Future:\n"
    + "\tindicators=[indicators]\n"
    )
  }

  def getBankUsage(): String = {
    (
    "Option: bank\n"
    + "\tBank is a function to interact with ones bank account\n"
    + "\tEx:\tstockShell:bank uid=ttlynch deposit=100000.50 account=checking\n"
    + "Usage:\n"
    + "\tuid=UserID\n"
    + "\tdeposit=[Amount to deposit] optional\n"
    + "\twithdraw=[Amount to withdraw] optional\n"
    + "\taccount=[checking/saving] requried with either deposit or withdraw\n"
    )
  }

  def getAccountUsage(): String = {
    (
    "Option: account\n"
    + "\tAccount is a function to create a bank account\n"
    + "\tEx:\tstockShell:account uid=ttlynch first=tyler last=lynch\n"
    + "Usage:\n"
    + "\tuid=UserID\n"
    + "\tfirst=[first name]\n"
    + "\tlast=[last name]\n"
    )
  }



}
