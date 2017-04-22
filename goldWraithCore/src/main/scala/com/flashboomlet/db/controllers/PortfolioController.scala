package com.flashboomlet.db.controllers

import com.flashboomlet.data.PortfolioData
import com.flashboomlet.data.PredictionData
import com.flashboomlet.db.MongoConstants
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

/**
  * Created by ttlynch on 4/4/17.
  */
class PortfolioController
  extends MongoConstants
  with LazyLogging
  with MongoImplicits {

  /** Instance of pre-configured database driver. */
  val databaseDriver = MongoDatabaseDriver()

  val predictionDataCollection: BSONCollection = databaseDriver
    .db(PredictionDataCollection)
  val portfolioDataCollection: BSONCollection = databaseDriver
    .db(PortfolioDataCollection)

  def uidExist(uid: String): Boolean = {
    val future =  portfolioDataCollection
    .find(BSONDocument(
      PortfolioDataConstants.Uid -> BSONString(uid)
    ))
    .cursor[BSONDocument]()
    .collect[List]()
    .map(list => list.nonEmpty)

    Await.result(future, Duration.Inf)
  }

  def getValidContractID(sale: PortfolioData): Long = {
    getActiveInvestments(sale.uid).filter(i =>
      i.contractType == sale.contractType && i.symbol == sale.symbol).filter(i =>
      i.units >= sale.units).minBy(i => i.units).transactionId
  }

  def validUnitCount(sale: PortfolioData): Boolean = {
    getActiveInvestments(sale.uid).filter(i =>
      i.contractType == sale.contractType && i.symbol == sale.symbol).exists(i => i.units >= sale.units)
  }

  def validContract(id: Long): Boolean = {
    getAllPortfolioData().filter(i => i.sellPrice < 0).exists(i => i.transactionId == id)
  }

  def getInvestment(uid: String, id: Long): PortfolioData = {
    getPortfolioData(uid).filter(i => i.transactionId == id).head
  }

  def getAActiveInvestments(uid: String): List[(String, Int)] = {
    // (Symbol, Units)
    val investments = getPortfolioData(uid).filter(s => s.sellPrice < 0)
    investments.groupBy(i => i.symbol).map( i =>
      (
      i._1,
      i._2.map(sale => sale.units).sum
      )
    ).toList
  }

  def getActiveInvestments(uid: String): List[PortfolioData] = {
    getPortfolioData(uid).filter(s => s.sellPrice < 0)
  }

  def getInvestmentOutcomes(uid: String): List[(Long, String, Double, String, Int)] = {
    // (Transaction ID, Symbol, Profit*, contractType, units)
    val outcomes = getPortfolioData(uid).filter(s => s.sellPrice > 0)
    outcomes.map { s =>
      val sale = s.sellPrice*s.units
      val buy = s.purchasePrice*s.units
      (
        s.transactionId,
        s.symbol,
        if (s.contractType == "put") {
          // Sell should be lower
          buy - sale
        } else {
          // call: sale should be higher
          sale-buy
        },
        s.contractType,
        s.units
      )
    }
  }

  /**
    * Inserts Portfolio data to the portfolioDataCollection
    *
    * @param pd PortfolioData to insert
    */
  def insertPortfolioData(pd: PortfolioData): Unit = {
    databaseDriver.insert(pd, portfolioDataCollection)
  }

  /**
    * Updates Portfolio data
    *
    * @param pd  PortfolioData
    */
  def updatePortfolioData(pd: PortfolioData): Unit = {
    val selector = BSONDocument(
      PortfolioDataConstants.Symbol -> BSONLong(pd.transactionId)
    )
    portfolioDataCollection.findAndUpdate(selector, pd).onComplete {
      case Success(result) => // Do Nothing, we win
      case _ => logger.error(s"failed to update the state for ${pd.transactionId}")
    }
  }

  /**
    * Get PortfolioData Main retrieves data for the portfolio Data collection
    *
    * @param uid the User ID
    * @return a list of PortfolioData
    */
  def getPortfolioData(
    uid: String): List[PortfolioData] =  {

    val selector = BSONDocument(
      PortfolioDataConstants.Uid -> BSONString(uid)
    )

    Await.result(
      portfolioDataCollection
      .find(selector)
      .cursor[PortfolioData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Get PortfolioData Main retrieves data for the portfolio Data collection
    *
    * @return a list of PortfolioData
    */
  def getAllPortfolioData(): List[PortfolioData] =  {

    val selector = BSONDocument()

    Await.result(
      portfolioDataCollection
      .find(selector)
      .cursor[PortfolioData]()
      .collect[List](),
      Duration.Inf)
  }

  def getTransactionID(): Long = {
    getAllPortfolioData.map(s => s.transactionId).max+1
  }

  /**
    * Inserts Prediction data to the PredictionDataCollection
    *
    * @param pd PredictionData to insert
    */
  def insertPredictionData(pd: PredictionData): Unit = {
    databaseDriver.insert(pd, predictionDataCollection)
  }

  /**
    * Get PredictionData Main retrieves data for the Prediction Data collection
    *
    * @param uid the User ID
    * @return a list of PredictionData
    */
  def getPredictionData(
    uid: String): List[PredictionData] =  {

    val selector = BSONDocument(
      PortfolioDataConstants.Uid -> BSONString(uid)
    )

    Await.result(
      predictionDataCollection
      .find(selector)
      .cursor[PredictionData]()
      .collect[List](),
      Duration.Inf)
  }


  private def clearAllPrediction(): Unit = {
    val future = predictionDataCollection.drop(false)
    Await.result(future, Duration.Inf)
  }

  private def clearAllPortfolio(): Unit = {
    val future = portfolioDataCollection.drop(false)
    Await.result(future, Duration.Inf)
  }


  def clearAll(): Unit = {
    clearAllPortfolio
    clearAllPrediction
  }
}

/**
  * Companion object for the PortfolioController class
  */
object PortfolioController {

  /**
    * Factory constructor method for the PortfolioController driver
    *
    * @return a new instance of PortfolioController
    */
  def apply(): PortfolioController = new PortfolioController()
}
