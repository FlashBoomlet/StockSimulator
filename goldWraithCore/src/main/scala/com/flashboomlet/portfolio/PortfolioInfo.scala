package com.flashboomlet.portfolio

import com.flashboomlet.data.PortfolioData
import com.flashboomlet.db.controllers.PortfolioController


/**
  * Created by ttlynch on 3/30/17.
  * Specific Implementation for the stock shell
  */
class PortfolioInfo {

  val pc = new PortfolioController

  // Return list[PortfolioData] (not set or queue as order matters)
  def getInformation(uid: String): Unit = {
    if(uidValid(uid)){
      println(s"\nPortfolio Data for $uid:")
      println(s"\tActive Trades")
      pc.getActiveInvestments(uid).foreach{ d =>
        println(s"\t\t${d.symbol}: ${d.contractType} for ${d.units} shares: ${d.purchasePrice*d.units} USD")
      }
      println(s"\tOutcomes")
      // (Transaction ID, Symbol, Profit*, contractType, units)
      pc.getInvestmentOutcomes(uid).foreach{ d =>
        println(s"\t\t${d._2}: ${d._4} for ${d._5} shares: ${d._3} USD")
      }
    } else {
      println("\nThe user ID that you entered is wrong.\n")
    }
  }

  private def uidValid(uid: String): Boolean = {
    val uidList = pc.uidExist(uid)
    if(uidList) true
    else false
  }

  def getUsers(): Unit = {
    println("\nValid users are:")
    pc.getAllPortfolioData.groupBy(s => s.uid).keys.foreach{ u =>
      println(s"\tUser ID: $u")
    }
    println("\n")
  }

  def getActiveInvestments(uid: String): List[PortfolioData] = {
    // (Symbol, Units)
    pc.getActiveInvestments(uid: String)
  }

  /*
   * Future implementation will be to implement filters on the data.
   */
}
