package com.flashboomlet.db.implicits

import com.flashboomlet.data.BankAccount
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONDouble
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait BankAccountImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object BankAccountWriter extends BSONDocumentWriter[BankAccount] {

    override def write(ba: BankAccount): BSONDocument = BSONDocument(
      BankAccountConstants.AccountNumber -> BSONLong(ba.accountNumber),
      BankAccountConstants.Uid -> BSONString(ba.uid),
      BankAccountConstants.First -> BSONString(ba.first),
      BankAccountConstants.Last -> BSONString(ba.last),
      BankAccountConstants.Checking -> BSONDouble(ba.checking),
      BankAccountConstants.Saving -> BSONDouble(ba.saving)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object BankAccountReader extends BSONDocumentReader[BankAccount] {

    override def read(doc: BSONDocument): BankAccount = {
      val accountNumber = doc.getAs[Long](BankAccountConstants.AccountNumber).get
      val uid = doc.getAs[String](BankAccountConstants.Uid).get
      val first = doc.getAs[String](BankAccountConstants.First).get
      val last = doc.getAs[String](BankAccountConstants.Last).get
      val checking = doc.getAs[Double](BankAccountConstants.Checking).get
      val saving = doc.getAs[Double](BankAccountConstants.Saving).get

      BankAccount(
        accountNumber = accountNumber,
        uid = uid,
        first = first,
        last = last,
        checking = checking,
        saving = saving
      )
    }
  }
}
