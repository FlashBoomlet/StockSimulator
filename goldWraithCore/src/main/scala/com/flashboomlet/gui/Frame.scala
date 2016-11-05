package com.flashboomlet.gui

import scala.swing.BorderPanel
import scala.swing.Dimension
import scala.swing.MainFrame
import scala.swing.BorderPanel.Position._

class Frame extends MainFrame with GUIConstants {

  title = "GUI Program #1"
  val minSize = new Dimension(GUIWidth, GUIHeight)
  preferredSize = minSize
  minimumSize = minSize
  resizable = false

  val plotArea = new Plot
  val tickerArea = new Ticker
  val watchListArea = new WatchList
  val statsArea = new Stats
  val controlsArea = new Controls
  val plot = plotArea.getPlot()
  val ticker = tickerArea.getTicker()
  val watchList = watchListArea.getWatchList()
  val stats = statsArea.getStats()
  val controls = controlsArea.getControls()

  contents = new BorderPanel {
    layout(ticker) = North
    layout(watchList) = West
    layout(plot) = Center
    layout(stats) = East
    layout(controls) = South
  }

}