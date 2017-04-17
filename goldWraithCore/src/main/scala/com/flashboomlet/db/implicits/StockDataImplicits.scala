package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.StockData
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONDouble
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
      StockDataConstants.LastTrade -> BSONDouble(sd.lastTrade),
      StockDataConstants.Open -> BSONDouble(sd.open),
      StockDataConstants.Ask -> BSONDouble(sd.ask),
      StockDataConstants.Bid -> BSONDouble(sd.bid),
      StockDataConstants.Volume -> BSONDouble(sd.volume),
      StockDataConstants.DayLow -> BSONDouble(sd.dayLow),
      StockDataConstants.DayHigh -> BSONDouble(sd.dayHigh),
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
      val lastTrade =  doc.getAs[Double](StockDataConstants.LastTrade).get
      val open =  doc.getAs[Double](StockDataConstants.Open).get
      val ask =  doc.getAs[Double](StockDataConstants.Ask).get
      val bid =  doc.getAs[Double](StockDataConstants.Bid).get
      val volume =  doc.getAs[Double](StockDataConstants.Volume).get
      val dayLow =  doc.getAs[Double](StockDataConstants.DayLow).get
      val dayHigh =  doc.getAs[Double](StockDataConstants.DayHigh).get
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
