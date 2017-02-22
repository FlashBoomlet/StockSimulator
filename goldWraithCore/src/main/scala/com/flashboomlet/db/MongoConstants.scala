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

  final val BasicIndustriesMainCollection = "basicIndustriesMainCollection"
  final val CapitalGoodsMainCollection = "capitalGoodsMainCollection"
  final val ConsumerDurablesMainCollection = "consumerDurablesMainCollection"
  final val ConsumerNonDurablesMainCollection = "consumerNonDurablesMainCollection"
  final val ConsumerServicesMainCollection = "consumerServicesMainCollection"
  final val EnergyMainCollection = "energyMainCollection"
  final val FinanceMainCollection = "financeMainCollection"
  final val HealthCareMainCollection = "healthCareMainCollection"
  final val MiscellaneousMainCollection = "miscellaneousMainCollection"
  final val OtherMainCollection = "otherMainCollection"
  final val PublicUtilitiesMainCollection = "publicUtilitiesMainCollection"
  final val TechnologyMainCollection = "technologyMainCollection"
  final val TransportationMainCollection = "transportationMainCollection"

  final val USStockListingsCollection = "usListingsCollection"


  /**
    * String constants used in the conversation state collection model schema
    */
  object StockDataConstants {

    /* String constant for the `key` field in a MongoDB Tick document */
    final val Key = "key"

    /* String constant for the `company` field in a MongoDB Tick document */
    final val Company = "company"

    /* String constant for the `symbol` field in a MongoDB Tick document */
    final val Symbol = "symbol"

    /* String constant for the `exchange` field in a MongoDB Tick document */
    final val Exchange = "exchange"

    /* String constant for the `time` field in a MongoDB Tick document */
    final val Time = "time"

    /* String constant for the `lastTrade` field in a MongoDB Tick document */
    final val LastTrade = "lasttrade"

    /* String constant for the `open` field in a MongoDB Tick document */
    final val Open = "open"

    /* String constant for the `ask` field in a MongoDB Tick document */
    final val Ask = "ask"

    /* String constant for the `bid` field in a MongoDB Tick document */
    final val Bid = "bid"

    /* String constant for the `volume` field in a MongoDB Tick document */
    final val Volume = "volume"

    /* String constant for the `dayLow` field in a MongoDB Tick document */
    final val DayLow = "daylow"

    /* String constant for the `dayHigh` field in a MongoDB Tick document */
    final val DayHigh = "dayhigh"

    /* String constant for the `source` field in a MongoDB Tick document */
    final val Source = "source"

    /* String constant for the `fetchDate` field in a MongoDB Tick document */
    final val FetchDate = "fetchdate"


  }

  object StockListingConstants {

    /* String constant for the `key` field in a MongoDB StockListing document */
    final val Key = "key"

    /* String constant for the `symbol` field in a MongoDB StockListing document */
    final val Symbol = "symbol"

    /* String constant for the `name` field in a MongoDB StockListing document */
    final val Name = "name"

    /* String constant for the `lastsale` field in a MongoDB StockListing document */
    final val LastSale = "lastsale"

    /* String constant for the `marketcap` field in a MongoDB StockListing document */
    final val MarketCap = "marketcap"

    /* String constant for the `ipoyear` field in a MongoDB StockListing document */
    final val IPOYear = "ipoyear"

    /* String constant for the `sector` field in a MongoDB StockListing document */
    final val Sector = "sector"

    /* String constant for the `industry` field in a MongoDB StockListing document */
    final val Industry = "industry"

    /* String constant for the `summaryquote` field in a MongoDB StockListing document */
    final val SummaryQuote = "summaryquote"

    /* String constant for the `exchange` field in a MongoDB StockListing document */
    final val Exchange = "exchange"

    /* String constant for the `lastupdate` field in a MongoDB StockListing document */
    final val LastUpdate = "lastupdate"

    final val LastDataFetch = "lastdatafetch"

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
