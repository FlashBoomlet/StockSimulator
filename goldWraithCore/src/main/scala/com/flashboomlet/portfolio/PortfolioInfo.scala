package com.flashboomlet.portfolio

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
      pc.getPortfolioData(uid).foreach{ d =>
        println(s"$d")
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

  /*
   * Future implementation will be to implement filters on the data.
   */
}
