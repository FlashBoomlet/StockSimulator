package com.flashboomlet.gui

import java.awt.Color

import com.flashboomlet.data.StatsData

import scala.swing.Dimension
import scala.swing.Label
import scala.swing.Panel

/**
  * Created by ttlynch on 11/5/16.
  */
class Stats extends GUIConstants {

  def getStats(): Panel = {
    val stats = new Panel() {
      private val myContents = new Content += new Label("Stats")
      override def contents = myContents
    }
    val size = new Dimension(EastWidth.toInt, MiddleHeight)
    stats.preferredSize = size
    stats.minimumSize = size

    stats.background = EastColor
    stats
  }

  /**
    * Update stats updates the stats that are shown within the GUI
    */
  def UpdateData(sd: List[StatsData]): Unit = {

  }

}
