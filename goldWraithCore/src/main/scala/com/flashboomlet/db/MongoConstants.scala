package com.flashboomlet.db

/**
  * Yeah, that's right. We got dank mongo constants
  *
  * The val's name must be HumpBack case
  * The string constant must be ALL lower case
  *
  * Collections must correspond
  */
trait MongoConstants {

  // DATABASE CONFIGURATION VALUES //

  /* String constant for connecting to MongoDB */
  final val DatabaseIp = "localhost"

  /* String constant for the database name */
  final val TheGoldWraithDatabaseString = "goldWraith"

  /* String constant for the conversation state collection */
  final val TickCollection = "tick"


  /**
    * String constants used in the conversation state collection model schema
    */
  object TickConstants {

    /* String constant for the `company` field in a MongoDB DayTick document */
    final val Company = "company"

    /* String constant for the `symbol` field in a MongoDB DayTick document */
    final val Symbol = "symbol"

    /* String constant for the `exchange` field in a MongoDB DayTick document */
    final val Exchange = "exchange"

    /* String constant for the `time` field in a MongoDB DayTick document */
    final val Time = "time"

    /* String constant for the `lastTrade` field in a MongoDB DayTick document */
    final val LastTrade = "lasttrade"

    /* String constant for the `open` field in a MongoDB DayTick document */
    final val Open = "open"

    /* String constant for the `ask` field in a MongoDB DayTick document */
    final val Ask = "ask"

    /* String constant for the `bid` field in a MongoDB DayTick document */
    final val Bid = "bid"

    /* String constant for the `volume` field in a MongoDB DayTick document */
    final val Volume = "volume"

    /* String constant for the `dayLow` field in a MongoDB DayTick document */
    final val DayLow = "daylow"

    /* String constant for the `dayHigh` field in a MongoDB DayTick document */
    final val DayHigh = "dayhigh"

    /* String constant for the `source` field in a MongoDB DayTick document */
    final val Source = "source"

    /* String constant for the `fetchDate` field in a MongoDB DayTick document */
    final val FetchDate = "fetchdate"


  }

  object StockListingConstants {

    final val Symbol = "symbol"
    final val Name = "name"
    final val LastSale = "lastsale"
    final val MarketCap = "marketcap"
    final val IPOYear = "ipoyear"
    final val Sector = "sector"
    final val Industry = "industry"
    final val SummaryQuote = "summaryquote"
    final val Exchange = "exchange"
    final val LastUpdate = "lastupdate"
  }

  /**
    * String constants used across the entirity of the MongoDB database
    */
  object GlobalConstants {

    /* String constant for the `_id` field in a MongoDB document */
    final val IdString = "_id"

    final val SetString = "$set"

    final val ElemMatchString = "$elemMatch"
  }

}
