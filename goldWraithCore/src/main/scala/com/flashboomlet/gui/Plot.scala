package com.flashboomlet.gui

import java.awt.Color

import com.flashboomlet.data.PlotData

import scala.swing.Dimension
import scala.swing.Label
import scala.swing.Panel

/**
  * Created by ttlynch on 11/4/16.
  */
class Plot extends GUIConstants {

  def getPlot(): Panel = {
    val plot = new Panel() {
      private val myContents = new Content += new Label("Plot")
      override def contents = myContents
    }
    plot.background = CenterColor
    val size = new Dimension(CenterWidth.toInt, MiddleHeight)
    plot.preferredSize = size
    plot.minimumSize = size

    plot
  }

  /**
    * Update Information in the Plot with what is passed into the chart.
    */
  def UpdateData(pd: List[PlotData]): Unit = {

  }

}
