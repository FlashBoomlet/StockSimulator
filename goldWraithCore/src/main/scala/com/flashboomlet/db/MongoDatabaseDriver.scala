package com.flashboomlet.db

import com.flashboomlet.data.MinuteTick
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging
import reactivemongo.api.BSONSerializationPack.Writer
import reactivemongo.api.DefaultDB
import reactivemongo.api.FailoverStrategy
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONObjectID

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import scala.util.Success


/**
  * Database driver for MongoDB
  */
class MongoDatabaseDriver
  extends MongoConstants
  with LazyLogging
  with MongoImplicits {

  val driver = new MongoDriver()

  val connection = driver.connection(List(DatabaseIp))

  val db: DefaultDB = Await.result(connection.database(TheGoldWraithDatabaseString,
    FailoverStrategy.default), Duration.Inf)

  val minuteTickCollection: BSONCollection = db(MinuteTickCollection)



    /**
    * Gets a conversation state given a conversation state id
    *
      * @param startTime the start time or date that the user is interested
      * @return a list of the minute ticks for the given period specified
      */
  def getMinuteTickPrices(startTime: Long, endTime: Long): List[MinuteTick] ={
      val future: Future[List[MinuteTick]] = minuteTickCollection.find(
        BSONDocument(
          MinuteTickConstants.Time -> BSONDocument(
            "$gte" -> BSONDateTime(startTime),
            "$lt" -> BSONDateTime(endTime)
          )
        )
      ).cursor[MinuteTick]().collect[List]()

      Await.result(future, Duration.Inf)
  }

  /** Simply inserts a conversation state Model */
  def insertMinuteTick(mt: MinuteTick): Unit = {
    insert(mt, minuteTickCollection)
  }

  /**
    * Updates a conversation state model
    *
    * @param mt a minute tick
    */
  def updateConversationState(mt: MinuteTick): Unit = {
    val selector = BSONDocument(
      MinuteTickConstants.Time -> mt.time,
      MinuteTickConstants.Company -> mt.company)
    minuteTickCollection.findAndUpdate(selector, mt).onComplete {
      case Success(result) => logger.info("successfully updated minute tick")
      case _ => logger.error(s"failed to update the minute state ${mt.company} for ${mt.time}")
    }
  }

  /**
    * Inserts an object with a defined implicit BSONWriter into the provided
    * collection and then returns the new BSONObjectID associated with the
    * newly inserted document.
    *
    * @param document Class of type T to be inserted as a [[BSONDocument]]
    * @param coll Collection to insert the [[BSONDocument]] into
    * @param writer Implicit writer associated with the Object of type T
    * @tparam T Type of the object to be inserted
    * @return The  [[BSONObjectID]] associated with the newly created document
    */
  private def insert[T](document: T, coll: BSONCollection)(implicit writer: Writer[T]): Unit = {

    val futureRes = coll.insert(document)
    val res = Await.result(futureRes, Duration.Inf)
    res.errmsg match {
      case Some(m) => logger.error(s"Failed to insert and create new id into $coll")
      case _ => // logging needed we won
    }
  }
}

/**
  * Companion object for the MongoDatabaseDriver class
  */
object MongoDatabaseDriver {

  /**
    * Factory constructor method for the mongoDB database driver
 *
    * @return a new instance of MongoDatabseDriver
    */
  def apply(): MongoDatabaseDriver = new MongoDatabaseDriver()
}
