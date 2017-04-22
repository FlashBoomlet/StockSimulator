package com.flashboomlet.controls

import com.flashboomlet.gui.Frame

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
    val frame = new Frame()
    frame.visible = true
    /*
    var line = ""
    while ({line = StdIn.readLine("stockShell:"); line != null}) {
      // Continuous loop
      parseLine(line)
    }
    */
  }

  def parseLine(input: String): String = {
    val tokenized = input.split(" ")
    val request = tokenized.head
    val options = tokenized.takeRight(tokenized.length-1)


    val rtn: String = request match {
      case "portfolio" => controls.getPortfolio(options)
      case "quote" => controls.getQuote(options)
      case "graph" => controls.graphStock(options)
      case "trade" => controls.tradeStock(options)
      case "sell" => controls.sell(options)
      case "market" => controls.getMarketPrices(options)
      case "bank" => controls.bank(options)
      case "account" => controls.account(options)
      case "system" => controls.getSystemInformation(options)
      case "clearall" => controls.clearAll()
      case "clear" => controls.clearPortfolio()
      case _ => ShellHelp.allHelp()
    }
    rtn
  }



}
