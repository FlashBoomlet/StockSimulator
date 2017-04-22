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
  def getInformation(uid: String): String = {

    if(uidValid(uid)){
      (
        "\nPortfolio Data for " + uid + ":\n"
        + "\n\tActive Trades\n"
        + pc.getActiveInvestments(uid).foreach{ d =>
          ("\t\t" + d.symbol + ": " + d.contractType+ " for " +d.units + " shares: "
          + d.purchasePrice*d.units + " USD (" + d.transactionId + ")\n")
        }
        + "\n\tOutcomes\n"
        // (Transaction ID, Symbol, Profit*, contractType, units)
        + pc.getInvestmentOutcomes(uid).foreach{ d =>
         "\t\t" + d._2 + ": " + d._4 + " for " + d._5 + " shares: " + d._3 + " USD\n"
        }
      )
    } else {
      "\nThe user ID that you entered is wrong.\n\n"
    }
  }

  private def uidValid(uid: String): Boolean = {
    val uidList = pc.uidExist(uid)
    if(uidList) true
    else false
  }

  def getUsers(): String = {
    (
      "\nValid users are:\n"
      + pc.getAllPortfolioData.groupBy(s => s.uid).keys.foreach{ u =>
        ("\tUser ID: " + u + "\n")
      }
      + "\n\n"
    )
  }

  def getActiveInvestments(uid: String): List[PortfolioData] = {
    // (Symbol, Units)
    pc.getActiveInvestments(uid: String)
  }

  /*
   * Future implementation will be to implement filters on the data.
   */
}
