package com.flashboomlet.gui

import java.awt.Color

import com.flashboomlet.data.WatchListData

import scala.swing.Dimension
import scala.swing.Label
import scala.swing.Panel

/**
  * Created by ttlynch on 11/5/16.
  */
class WatchList extends GUIConstants {

  def getWatchList(): Panel = {
    val watchList = new Panel() {
      private val myContents = new Content += new Label("WatchList")
      override def contents = myContents
    }
    val size = new Dimension(WestWidth.toInt, MiddleHeight)
    watchList.preferredSize = size
    watchList.minimumSize = size
    watchList.background = WestColor
    watchList
  }

  /**
    * Update the WatchList with the most recent information
    */
  def UpdateData(wd: List[WatchListData]): Unit = {

  }

}
