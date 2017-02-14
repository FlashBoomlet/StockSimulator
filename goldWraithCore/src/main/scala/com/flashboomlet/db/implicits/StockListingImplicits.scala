package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.StockListing
import com.flashboomlet.data.Tick
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONInteger
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait StockListingImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object ConversationStateWriter extends BSONDocumentWriter[StockListing] {

    override def write(sl: StockListing): BSONDocument = BSONDocument(
      StockListingConstants.Symbol -> BSONString(sl.symbol),
      StockListingConstants.Name -> BSONString(sl.name),
      StockListingConstants.LastSale -> BSONLong(sl.lastSale),
      StockListingConstants.MarketCap -> BSONLong(sl.marketCap),
      StockListingConstants.IPOYear -> BSONInteger(sl.ipoYear),
      StockListingConstants.Sector -> BSONString(sl.sector),
      StockListingConstants.Industry -> BSONString(sl.industry),
      StockListingConstants.SummaryQuote -> BSONString(sl.summaryQuote),
      StockListingConstants.Exchange -> BSONString(sl.exchange),
      StockListingConstants.LastUpdate -> BSONDateTime(sl.lastUpdate)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object ConversationStateReader extends BSONDocumentReader[StockListing] {

    override def read(doc: BSONDocument): StockListing = {
      val symbol = doc.getAs[String](StockListingConstants.Symbol).get
      val name = doc.getAs[String](StockListingConstants.Name).get
      val lastSale = doc.getAs[Long](StockListingConstants.LastSale).get
      val marketCap = doc.getAs[Long](StockListingConstants.MarketCap).get
      val ipoYear = doc.getAs[Int](StockListingConstants.IPOYear).get
      val sector = doc.getAs[String](StockListingConstants.Sector).get
      val industry = doc.getAs[String](StockListingConstants.Industry).get
      val summaryQuote = doc.getAs[String](StockListingConstants.SummaryQuote).get
      val exchange = doc.getAs[String](StockListingConstants.Exchange).get
      val lastUpdate = doc.getAs[Date](StockListingConstants.LastUpdate).get.getTime

      StockListing(
        symbol = symbol,
        name = name,
        lastSale = lastSale,
        marketCap = marketCap,
        ipoYear = ipoYear,
        sector = sector,
        industry = industry,
        summaryQuote = summaryQuote,
        exchange = exchange,
        lastUpdate = lastUpdate
      )
    }
  }
}
