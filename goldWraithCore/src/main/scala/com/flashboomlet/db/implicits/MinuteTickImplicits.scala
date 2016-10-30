package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.MinuteTick
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait MinuteTickImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object ConversationStateWriter extends BSONDocumentWriter[MinuteTick] {

    override def write(mt: MinuteTick): BSONDocument = BSONDocument(
      MinuteTickConstants.Company -> BSONString(mt.company),
      MinuteTickConstants.Symbol -> BSONString(mt.symbol),
      MinuteTickConstants.Time -> BSONDateTime(mt.time),
      MinuteTickConstants.Ask -> BSONLong(mt.ask),
      MinuteTickConstants.Bid -> BSONLong(mt.bid)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object ConversationStateReader extends BSONDocumentReader[MinuteTick] {

    override def read(doc: BSONDocument): MinuteTick = {
      val company = doc.getAs[String](MinuteTickConstants.Company).get
      val symbol = doc.getAs[String](MinuteTickConstants.Symbol).get
      val time = doc.getAs[Date](MinuteTickConstants.Time).get.getTime
      val ask = doc.getAs[Long](MinuteTickConstants.Ask).get
      val bid = doc.getAs[Long](MinuteTickConstants.Bid).get


      MinuteTick(
        company = company,
        symbol = symbol,
        time = time,
        ask = ask,
        bid = bid
      )
    }
  }
}
