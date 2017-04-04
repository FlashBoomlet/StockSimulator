package com.flashboomlet.data

/**
  * Created by ttlynch on 10/29/16.
  */
case class PortfolioData(
  uid: Int,
  transactionId: Long,
  market: String,
  symbol: String,
  units: Int,
  purchaseDate: Long,
  purchasePrice: Long,
  sellDate: Long,
  sellPrice: Long,
  contractType: String,
  contractEndDate: Long
)
