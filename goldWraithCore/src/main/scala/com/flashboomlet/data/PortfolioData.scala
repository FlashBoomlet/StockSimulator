package com.flashboomlet.data

/**
  * Created by ttlynch on 10/29/16.
  */
case class PortfolioData(
  uid: String,
  transactionId: Long,
  market: String,
  symbol: String,
  units: Int,
  purchaseDate: Long,
  purchasePrice: Double,
  sellDate: Long,
  sellPrice: Double,
  contractType: String,
  contractEndDate: Long
)
