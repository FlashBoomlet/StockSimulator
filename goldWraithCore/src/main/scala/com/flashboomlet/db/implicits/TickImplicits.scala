package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.Tick
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait TickImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object ConversationStateWriter extends BSONDocumentWriter[Tick] {

    override def write(t: Tick): BSONDocument = BSONDocument(
      TickConstants.Company -> BSONString(t.company),
      TickConstants.Symbol -> BSONString(t.symbol),
      TickConstants.Exchange -> BSONString(t.exchange),
      TickConstants.Time -> BSONDateTime(t.time),
      TickConstants.LastTrade -> BSONLong(t.lastTrade),
      TickConstants.Open -> BSONLong(t.open),
      TickConstants.Ask -> BSONLong(t.ask),
      TickConstants.Bid -> BSONLong(t.bid),
      TickConstants.Volume -> BSONLong(t.volume),
      TickConstants.DayLow -> BSONLong(t.dayLow),
      TickConstants.DayHigh -> BSONLong(t.dayHigh),
      TickConstants.Source -> BSONString(t.source),
      TickConstants.FetchDate -> BSONDateTime(t.fetchDate)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object ConversationStateReader extends BSONDocumentReader[Tick] {

    override def read(doc: BSONDocument): Tick = {
      val company =  doc.getAs[String](TickConstants.Company).get
      val symbol =  doc.getAs[String](TickConstants.Symbol).get
      val exchange =  doc.getAs[String](TickConstants.Exchange).get
      val time = doc.getAs[Date](TickConstants.Time).get.getTime
      val lastTrade =  doc.getAs[Long](TickConstants.LastTrade).get
      val open =  doc.getAs[Long](TickConstants.Open).get
      val ask =  doc.getAs[Long](TickConstants.Ask).get
      val bid =  doc.getAs[Long](TickConstants.Bid).get
      val volume =  doc.getAs[Long](TickConstants.Volume).get
      val dayLow =  doc.getAs[Long](TickConstants.DayLow).get
      val dayHigh =  doc.getAs[Long](TickConstants.DayHigh).get
      val source =  doc.getAs[String](TickConstants.Source).get
      val fetchDate = doc.getAs[Date](TickConstants.FetchDate).get.getTime

      Tick(
        company = company,
        symbol = symbol,
        exchange = exchange,
        time = time,
        lastTrade = lastTrade,
        open = open,
        ask = ask,
        bid = bid,
        volume = volume,
        dayLow  = dayLow,
        dayHigh = dayHigh,
        source = source,
        fetchDate = fetchDate
      )
    }
  }
}
