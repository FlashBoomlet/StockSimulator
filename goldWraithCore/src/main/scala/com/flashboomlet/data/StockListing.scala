package com.flashboomlet.data

/**
  * Created by ttlynch on 2/12/17.
  *
  * @param symbol the symbol of the stock
  * @param name the name of the stock
  * @param lastSale the last known sale price of the stock
  * @param marketCap the market cap of the stock
  * @param ipoYear the year that it was first offered
  * @param sector the sector that it falls into
  * @param industry the industry that it falls into
  * @param summaryQuote the URL to more information about the stock price.
  * @param exchange the exchange that the stock is in
  */
case class StockListing (
  val symbol: String,
  val name: String,
  val lastSale: Long,
  val marketCap: Long,
  val ipoYear: Int,
  val sector: String,
  val industry: String,
  val summaryQuote: String,
  val exchange: String,
  val lastUpdate: Long
)
