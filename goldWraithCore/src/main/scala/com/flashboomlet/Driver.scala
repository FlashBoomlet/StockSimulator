package com.flashboomlet

import com.flashboomlet.stocks.YahooFinance
import com.flashboomlet.gui.Frame

/**
  * Created by ttlynch on 10/7/16.
  */
object Driver {

  def main(args: Array[String]) {
    println("We are up and functioning world")
    val finance = new YahooFinance()
    // S & P 500: "^gspc"
    val snp = "^gspc"
    val symbol = snp
    finance.getQuote(symbol)

    val frame = new Frame()
    frame.visible = true
    println("End of the world")
  }
}
