package com.flashboomlet.gui

import com.flashboomlet.data.TickerData

import scala.swing.Dimension
import scala.swing.Label
import scala.swing.Panel
import scala.util.Random

/**
  * Created by ttlynch on 11/5/16.
  */
class Ticker extends GUIConstants {

  val Labels = CreateLabels()

  def getTicker(): Panel = {
    val ticker = new Panel() {
      private val myContents = new Content
      Labels.foreach(s =>
        myContents += s
      )
      override def contents = myContents
    }
    val size = new Dimension(GUIWidth, TopHeight)
    ticker.preferredSize = size
    ticker.minimumSize = size
    ticker.background = NorthColor
    ticker
  }

  def CreateLabels(): List[Label] = {
    List(
      new Label("Label #1: ^ 1234.12"),
      new Label("Label #2: ^ 1234.12"),
      new Label("Label #3: ^ 1234.12"),
      new Label("Label #4: ^ 1234.12"),
      new Label("Label #5: ^ 1234.12"),
      new Label("Label #6: ^ 1234.12"),
      new Label("Label #7: ^ 1234.12"),
      new Label("Label #8: ^ 1234.12")
    )
  }
  /**
    * Update the ticker with the most recent information
    */
  def UpdateData(td: List[TickerData]): Unit = {
    val rand = new Random()
    val rNum = rand.nextFloat()
    Labels.foreach(l =>
      l.text = "Update Label" + rNum
    )
  }

}
