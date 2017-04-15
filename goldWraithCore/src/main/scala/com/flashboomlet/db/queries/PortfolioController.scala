package com.flashboomlet.db.queries

import com.flashboomlet.data.PortfolioData
import com.flashboomlet.data.PredictionData
import com.flashboomlet.db.MongoConstants
import com.flashboomlet.db.MongoDatabaseDriver
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONInteger

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.concurrent.ExecutionContext.Implicits.global

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

  def uidExist(): Boolean = {
    false
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
    * Get PortfolioData Main retrieves data for the portfolio Data collection
    *
    * @param uid the User ID
    * @return a list of PortfolioData
    */
  def getPortfolioData(
    uid: Int): List[PortfolioData] =  {

    val selector = BSONDocument(
      StockDataConstants.Exchange -> BSONInteger(uid)
    )

    Await.result(
      portfolioDataCollection
      .find(selector)
      .cursor[PortfolioData]()
      .collect[List](),
      Duration.Inf)
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
    uid: Int): List[PredictionData] =  {

    val selector = BSONDocument(
      StockDataConstants.Exchange -> BSONInteger(uid)
    )

    Await.result(
      predictionDataCollection
      .find(selector)
      .cursor[PredictionData]()
      .collect[List](),
      Duration.Inf)
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
