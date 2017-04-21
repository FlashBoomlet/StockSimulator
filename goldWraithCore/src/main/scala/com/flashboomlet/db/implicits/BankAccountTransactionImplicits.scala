package com.flashboomlet.db.implicits

import java.util.Date

import com.flashboomlet.data.BankAccountTransaction
import com.flashboomlet.db.MongoConstants
import reactivemongo.bson.BSONDateTime
import reactivemongo.bson.BSONDocument
import reactivemongo.bson.BSONDocumentReader
import reactivemongo.bson.BSONDocumentWriter
import reactivemongo.bson.BSONDouble
import reactivemongo.bson.BSONLong
import reactivemongo.bson.BSONString

/** Implicit readers and writers for the Conversation State model in of MongoDB */
trait BankAccountTransactionImplicits extends MongoConstants {

  /** Implicit conversion object for Conversation State writing */
  implicit object BankAccountTransactionWriter extends BSONDocumentWriter[BankAccountTransaction] {

    override def write(bat: BankAccountTransaction): BSONDocument = BSONDocument(
      BankAccountTransactionConstants.AccountNumber -> BSONLong(bat.accountNumber),
      BankAccountTransactionConstants.Date -> BSONDateTime(bat.date),
      BankAccountTransactionConstants.TransactionType -> BSONString(bat.transactionType),
      BankAccountTransactionConstants.Description -> BSONString(bat.description),
      BankAccountTransactionConstants.Debit -> BSONDouble(bat.debit),
      BankAccountTransactionConstants.Credit -> BSONDouble(bat.credit),
      BankAccountTransactionConstants.Balance -> BSONDouble(bat.balance)
    )
  }


  /** Implicit conversion object for reading Conversation State class */
  implicit object BankAccountTransactionReader extends BSONDocumentReader[BankAccountTransaction] {

    override def read(doc: BSONDocument): BankAccountTransaction = {
      val accountNumber = doc.getAs[Long](BankAccountTransactionConstants.AccountNumber).get
      val date = doc.getAs[Date](BankAccountTransactionConstants.Date).get.getTime
      val transactionType = doc.getAs[String](BankAccountTransactionConstants.TransactionType).get
      val description = doc.getAs[String](BankAccountTransactionConstants.Description).get
      val debit = doc.getAs[Double](BankAccountTransactionConstants.Debit).get
      val credit = doc.getAs[Double](BankAccountTransactionConstants.Credit).get
      val balance = doc.getAs[Double](BankAccountTransactionConstants.Balance).get

      BankAccountTransaction(
        accountNumber = accountNumber,
        date = date,
        transactionType = transactionType,
        description = description,
        debit = debit,
        credit = credit,
        balance = balance
      )
    }
  }
}
