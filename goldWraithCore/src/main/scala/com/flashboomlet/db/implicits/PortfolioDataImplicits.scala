package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.PortfolioData
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
trait PortfolioDataImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object PortfolioDataWriter extends BSONDocumentWriter[PortfolioData] {

    override def write(pd: PortfolioData): BSONDocument = BSONDocument(
      PortfolioDataConstants.Uid -> BSONString(pd.uid),
      PortfolioDataConstants.TransactionId -> BSONLong(pd.transactionId),
      PortfolioDataConstants.Market -> BSONString(pd.market),
      PortfolioDataConstants.Symbol -> BSONString(pd.symbol),
      PortfolioDataConstants.Units -> BSONInteger(pd.units),
      PortfolioDataConstants.PurchaseDate -> BSONDateTime(pd.purchaseDate),
      PortfolioDataConstants.PurchasePrice -> BSONDouble(pd.purchasePrice),
      PortfolioDataConstants.SellDate -> BSONDateTime(pd.sellDate),
      PortfolioDataConstants.SellPrice -> BSONDouble(pd.sellPrice),
      PortfolioDataConstants.ContractType -> BSONString(pd.contractType),
      PortfolioDataConstants.ContractEndDate -> BSONDateTime(pd.contractEndDate)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object PortfolioDataReader extends BSONDocumentReader[PortfolioData] {

    override def read(doc: BSONDocument): PortfolioData = {
      val uid = doc.getAs[String](PortfolioDataConstants.Uid).get
      val transactionId = doc.getAs[Long](PortfolioDataConstants.TransactionId).get
      val market = doc.getAs[String](PortfolioDataConstants.Market).get
      val symbol = doc.getAs[String](PortfolioDataConstants.Symbol).get
      val units = doc.getAs[Int](PortfolioDataConstants.Units).get
      val purchaseDate = doc.getAs[Date](PortfolioDataConstants.PurchaseDate).get.getTime
      val purchasePrice = doc.getAs[Double](PortfolioDataConstants.PurchasePrice).get
      val sellDate = doc.getAs[Date](PortfolioDataConstants.SellDate).get.getTime
      val sellPrice = doc.getAs[Double](PortfolioDataConstants.SellPrice).get
      val contractType = doc.getAs[String](PortfolioDataConstants.ContractType).get
      val contractEndDate = doc.getAs[Date](PortfolioDataConstants.ContractEndDate).get.getTime


      PortfolioData(
        uid = uid,
        transactionId = transactionId,
        market = market,
        symbol = symbol,
        units = units,
        purchaseDate = purchaseDate,
        purchasePrice = purchasePrice,
        sellDate = sellDate,
        sellPrice = sellPrice,
        contractType = contractType,
        contractEndDate = contractEndDate
      )
    }
  }
}
