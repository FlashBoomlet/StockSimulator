package com.flashboomlet.data

/**
  * Created by ttlynch on 10/29/16.
  */
case class PredictionData(
  transactionId: Long,
  contractType: String,
  safetyLevel: Long,
  predictedPrice: Long,
  predictedSellDate: Long,
  predictedProfit: Long,
  strategy: String
)
