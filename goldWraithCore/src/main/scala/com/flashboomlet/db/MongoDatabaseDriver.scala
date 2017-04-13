package com.flashboomlet.db

import com.flashboomlet.data.StockListing
import com.flashboomlet.data.StockData
import com.flashboomlet.db.implicits.MongoImplicits
import com.typesafe.scalalogging.LazyLogging
import reactivemongo.api.BSONSerializationPack.Writer
import reactivemongo.api.DefaultDB
import reactivemongo.api.FailoverStrategy
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONObjectID
import reactivemongo.bson.BSONString

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.Success


/**
  * Database driver for MongoDB
  */
class MongoDatabaseDriver
  extends MongoConstants
  with LazyLogging
  with MongoImplicits {

  val driver = new MongoDriver()

  val connection = driver.connection(List(DatabaseIp))

  val db: DefaultDB = Await.result(connection.database(TheGoldWraithDatabaseString,
    FailoverStrategy.default), Duration.Inf)

  val basicIndustriesMainCollection: BSONCollection = db(BasicIndustriesMainCollection)

  val capitalGoodsMainCollection: BSONCollection = db(CapitalGoodsMainCollection)

  val consumerDurablesMainCollection: BSONCollection = db(ConsumerDurablesMainCollection)

  val consumerNonDurablesMainCollection: BSONCollection = db(ConsumerNonDurablesMainCollection)

  val consumerServicesMainCollection: BSONCollection = db(ConsumerServicesMainCollection)

  val energyMainCollection: BSONCollection = db(EnergyMainCollection)

  val financeMainCollection: BSONCollection = db(FinanceMainCollection)

  val healthCareMainCollection: BSONCollection = db(HealthCareMainCollection)

  val miscellaneousMainCollection: BSONCollection = db(MiscellaneousMainCollection)

  val otherMainCollection: BSONCollection = db(OtherMainCollection)

  val publicUtilitiesMainCollection: BSONCollection = db(PublicUtilitiesMainCollection)

  val technologyMainCollection: BSONCollection = db(TechnologyMainCollection)

  val transportationMainCollection: BSONCollection = db(TransportationMainCollection)

  val usStockListingsCollection: BSONCollection = db(USStockListingsCollection)


  /**
    * Stock Selector returns a selector for complex queries to retrieve data
    *
    * @param startTime the start time to retrieve the data for
    * @param endTime the end time to retrieve the data for
    * @param symbol the symbol to retrieve data for
    * @param exchange the echange from which to retrieve data for
    * @return a BSONDocument selector
    */
  def stockSelector(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): BSONDocument = {

    BSONDocument(
      StockDataConstants.Exchange -> BSONString(exchange),
      StockDataConstants.Symbol -> BSONString(symbol),
      StockDataConstants.Time -> BSONDocument(
        "$gte" -> BSONDateTime(startTime),
        "$lt" -> BSONDateTime(endTime)
      )
    )
  }

  /**
    * Get Stock Listings is a function to retrieve all of the stock listings in the us
    *
    * @return the stock listings in the us
    */
  def getUSStockListings: Set[StockListing] = {
    val selector = BSONDocument()
    Await.result(
      usStockListingsCollection
      .find(selector)
      .cursor[StockListing]()
      .collect[Set](),
      Duration.Inf)
  }

  /**
    * Updates a stock listing
    *
    * @param sl a stock listing
    */
  def updateUSStockListing(sl: StockListing): Unit = {
    val selector = BSONDocument(
      StockListingConstants.Symbol -> BSONString(sl.symbol),
      StockListingConstants.Exchange -> BSONString(sl.exchange)
    )
    usStockListingsCollection.findAndUpdate(selector, sl).onComplete {
      case Success(result) => logger.info("successfully updated tick")
      case _ => logger.error(s"failed to update the state for ${sl.symbol} at ${sl.lastDataFetch}")
    }
  }

  /**
    * Insert USStockLIsting is a function used to insert a stock listing if and only if it is not
    * already present in the collection
 *
    * @param sl the stock listing to be inserted into the collection
    */
  def insertUSStockListing(sl: StockListing): Unit = {
    if(!stockListingExists(sl.symbol, sl.exchange)){
      insert(sl, usStockListingsCollection)
    }
  }

  /**
    * Get Max Key should return the max key found so far in the stock listsings database
 *
    * @return
    */
  def getMaxKey: Int = {
    val future =  usStockListingsCollection
    .find(BSONDocument())
    .cursor[StockListing]()
    .collect[List]()
    .map( l => l.map(s => s.key).max)

    Await.result(future, Duration.Inf)
  }

  /**
    * Determines if a stock exists in the database.
    *
    * @param symbol The stock to be assessed for uniqueness in the database
    * @return true if the stock exists in the USStockListingsCollection database, else false
    */
  def stockListingExists(symbol: String, exchange: String): Boolean = {
    val future =  usStockListingsCollection
      .find(BSONDocument(
        StockListingConstants.Symbol -> BSONString(symbol),
        StockListingConstants.Exchange -> BSONString(exchange)
      ))
      .cursor[BSONDocument]()
      .collect[List]()
      .map(list => list.nonEmpty)

    Await.result(future, Duration.Inf)
  }

  /**
    * Inserts stock data to the appropriate collection
    *
    * @param sd stockData to insert
    */
  def insertBasicIndustriesMain(sd: StockData): Unit = {
    insert(sd, basicIndustriesMainCollection)
  }

  /**
    * Get BasicIndustries Main retrieves data for the main BasicIndustries collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getBasicIndustriesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      basicIndustriesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the capitalGoodsMainCollection
    *
    * @param sd stockData to insert
    */
  def insertCapitalGoodsMain(sd: StockData): Unit = {
    insert(sd, capitalGoodsMainCollection)
  }

  /**
    * Get CapitalGoods Main retrieves data for the main CapitalGoods collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getCapitalGoodsMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      capitalGoodsMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the consumerDurablesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertConsumerDurablesMain(sd: StockData): Unit = {
    insert(sd, consumerDurablesMainCollection)
  }

  /**
    * Get ConsumerDurables Main retrieves data for the main ConsumerDurables collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getConsumerDurablesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      consumerDurablesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the consumerNonDurablesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertConsumerNonDurablesMain(sd: StockData): Unit = {
    insert(sd, consumerNonDurablesMainCollection)
  }

  /**
    * Get ConsumerNonDurables Main retrieves data for the main ConsumerNonDurables collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getConsumerNonDurablesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      consumerNonDurablesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the consumerServicesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertConsumerServicesMain(sd: StockData): Unit = {
    insert(sd, consumerServicesMainCollection)
  }

  /**
    * Get ConsumerServices Main retrieves data for the main ConsumerServices collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getConsumerServicesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      consumerServicesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the energyMainCollection
    *
    * @param sd stockData to insert
    */
  def insertEnergyMain(sd: StockData): Unit = {
    insert(sd, energyMainCollection)
  }

  /**
    * Get Energy Main retrieves data for the main Energy collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getEnergyMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      energyMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the financeMainCollection
    *
    * @param sd stockData to insert
    */
  def insertFinanceMain(sd: StockData): Unit = {
    insert(sd, financeMainCollection)
  }

  /**
    * Get Finance Main retrieves data for the main Finance collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getFinanceMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      financeMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the healthCareMainCollection
    *
    * @param sd stockData to insert
    */
  def insertHealthCareMain(sd: StockData): Unit = {
    insert(sd, healthCareMainCollection)
  }

  /**
    * Get HealthCare Main retrieves data for the main HealthCare collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getHealthCareMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      healthCareMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the miscellaneousMainCollection
    *
    * @param sd stockData to insert
    */
  def insertMiscellaneousMain(sd: StockData): Unit = {
    insert(sd, miscellaneousMainCollection)
  }

  /**
    * Get Miscellaneous Main retrieves data for the main Miscellaneous collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getMiscellaneousMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      miscellaneousMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the otherMainCollection
    *
    * @param sd stockData to insert
    */
  def insertOtherMain(sd: StockData): Unit = {
    insert(sd, otherMainCollection)
  }

  /**
    * Get Other Main retrieves data for the main Other collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getOtherMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      otherMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

    /**
    * Inserts stock data to the publicUtilitiesMainCollection
    *
    * @param sd stockData to insert
    */
  def insertPublicUtilitiesMain(sd: StockData): Unit = {
    insert(sd, publicUtilitiesMainCollection)
  }

  /**
    * Get PublicUtilities Main retrieves data for the main PublicUtilities collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getPublicUtilitiesMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      publicUtilitiesMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the technologyMainCollection
    *
    * @param sd stockData to insert
    */
  def insertTechnologyMain(sd: StockData): Unit = {
    insert(sd, technologyMainCollection)
  }

  /**
    * Get Technology Main retrieves data for the main Technology collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getTechnologyMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      technologyMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts stock data to the transportationMainCollection
    *
    * @param sd stockData to insert
    */
  def insertTransportationMain(sd: StockData): Unit = {
    insert(sd, transportationMainCollection)
  }

  /**
    * Get Transportation Main retrieves data for the main transportation collection
    *
    * @param startTime the time from which to begin the results
    * @param endTime the end time from which to end the results
    * @param symbol the symbol for which to gather data
    * @param exchange the exchange for which the symbol belongs to
    * @return a list of stock data (order does matter)
    */
  def getTransportationMain(
    startTime: Long,
    endTime: Long,
    symbol: String,
    exchange: String): List[StockData] =  {

    val selector = stockSelector(startTime, endTime, symbol, exchange)
    Await.result(
      transportationMainCollection
      .find(selector)
      .cursor[StockData]()
      .collect[List](),
      Duration.Inf)
  }

  /**
    * Inserts an object with a defined implicit BSONWriter into the provided
    * collection and then returns the new BSONObjectID associated with the
    * newly inserted document.
    *
    * @param document Class of type T to be  inserted as a [[BSONDocument]]
    * @param coll Collection to insert the [[BSONDocument]] into
    * @param writer Implicit writer associated with the Object of type T
    * @tparam T Type of the object to be inserted
    * @return The  [[BSONObjectID]] associated with the newly created document
    */
  private def insert[T](document: T, coll: BSONCollection)(implicit writer: Writer[T]): Unit = {
    val futureRes = coll.insert(document)
    val res = Await.result(futureRes, Duration.Inf)
    res.errmsg match {
      case Some(m) => logger.error(s"Failed to insert and create new id into $coll")
      case _ => // logging needed we won
    }
  }
}

/**
  * Companion object for the MongoDatabaseDriver class
  */
object MongoDatabaseDriver {

  /**
    * Factory constructor method for the mongoDB database driver
 *
    * @return a new instance of MongoDatabseDriver
    */
  def apply(): MongoDatabaseDriver = new MongoDatabaseDriver()
}
