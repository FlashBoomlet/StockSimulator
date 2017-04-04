package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.PredictionData
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait PredictionDataImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object PredictionDataWriter extends BSONDocumentWriter[PredictionData] {

    override def write(pd: PredictionData): BSONDocument = BSONDocument(
      PredictionDataConstants.TransactionId -> BSONLong(pd.transactionId),
      PredictionDataConstants.ContractType -> BSONString(pd.contractType),
      PredictionDataConstants.SafetyLevel -> BSONLong(pd.safetyLevel),
      PredictionDataConstants.PredictedPrice -> BSONLong(pd.predictedPrice),
      PredictionDataConstants.PredictedSellDate -> BSONDateTime(pd.predictedSellDate),
      PredictionDataConstants.PredictedProfit -> BSONLong(pd.predictedProfit),
      PredictionDataConstants.Strategy -> BSONString(pd.strategy)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object PredictionDataReader extends BSONDocumentReader[PredictionData] {

    override def read(doc: BSONDocument): PredictionData = {
      val transactionId = doc.getAs[Long](PredictionDataConstants.TransactionId).get
      val contractType = doc.getAs[String](PredictionDataConstants.ContractType).get
      val safetyLevel = doc.getAs[Long](PredictionDataConstants.SafetyLevel).get
      val predictedPrice = doc.getAs[Long](PredictionDataConstants.PredictedPrice).get
      val predictedSellDate = doc.getAs[Date](PredictionDataConstants.PredictedSellDate).get.getTime
      val predictedProfit = doc.getAs[Long](PredictionDataConstants.PredictedProfit).get
      val strategy = doc.getAs[String](PredictionDataConstants.Strategy).get

      PredictionData(
        transactionId = transactionId,
        contractType = contractType,
        safetyLevel = safetyLevel,
        predictedPrice = predictedPrice,
        predictedSellDate = predictedSellDate,
        predictedProfit = predictedProfit,
        strategy = strategy
      )
    }
  }
}
