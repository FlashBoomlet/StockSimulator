package com.flashboomlet.data

/**
  * Created by ttlynch on 10/29/16.
  */
case class BankAccountTransaction(
  accountNumber: Long,
  date: Long,
  transactionType: String,
  description: String,
  debit: Double,
  credit: Double,
  balance: Double
)
