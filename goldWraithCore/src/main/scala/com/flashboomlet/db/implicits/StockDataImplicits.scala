package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait StockDataImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object StockDataWriter extends BSONDocumentWriter[StockData] {

    override def write(sd: StockData): BSONDocument = BSONDocument(
      StockDataConstants.Key -> BSONInteger(sd.key),
      StockDataConstants.Company -> BSONString(sd.company),
      StockDataConstants.Symbol -> BSONString(sd.symbol),
      StockDataConstants.Exchange -> BSONString(sd.exchange),
      StockDataConstants.Time -> BSONDateTime(sd.time),
      StockDataConstants.LastTrade -> BSONLong(sd.lastTrade),
      StockDataConstants.Open -> BSONLong(sd.open),
      StockDataConstants.Ask -> BSONLong(sd.ask),
      StockDataConstants.Bid -> BSONLong(sd.bid),
      StockDataConstants.Volume -> BSONLong(sd.volume),
      StockDataConstants.DayLow -> BSONLong(sd.dayLow),
      StockDataConstants.DayHigh -> BSONLong(sd.dayHigh),
      StockDataConstants.Source -> BSONString(sd.source),
      StockDataConstants.FetchDate -> BSONDateTime(sd.fetchDate)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object StockDataReader extends BSONDocumentReader[StockData] {

    override def read(doc: BSONDocument): StockData = {
      val key = doc.getAs[Int](StockDataConstants.Key).get
      val company =  doc.getAs[String](StockDataConstants.Company).get
      val symbol =  doc.getAs[String](StockDataConstants.Symbol).get
      val exchange =  doc.getAs[String](StockDataConstants.Exchange).get
      val time = doc.getAs[Date](StockDataConstants.Time).get.getTime
      val lastTrade =  doc.getAs[Long](StockDataConstants.LastTrade).get
      val open =  doc.getAs[Long](StockDataConstants.Open).get
      val ask =  doc.getAs[Long](StockDataConstants.Ask).get
      val bid =  doc.getAs[Long](StockDataConstants.Bid).get
      val volume =  doc.getAs[Long](StockDataConstants.Volume).get
      val dayLow =  doc.getAs[Long](StockDataConstants.DayLow).get
      val dayHigh =  doc.getAs[Long](StockDataConstants.DayHigh).get
      val source =  doc.getAs[String](StockDataConstants.Source).get
      val fetchDate = doc.getAs[Date](StockDataConstants.FetchDate).get.getTime

      StockData(
        key = key,
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
