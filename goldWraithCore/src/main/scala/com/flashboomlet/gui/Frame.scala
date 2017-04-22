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

  // val plotArea = new Plot
  val plotArea = Shell
  val tickerArea = new Ticker
  val watchListArea = new WatchList
  //val statsArea = new Stats
  val statsArea = Help
  val controlsArea = new Controls
  // val plot = plotArea.getPlot()
  val plot = plotArea.getShell()
  val ticker = tickerArea.getTicker()
  val watchList = watchListArea.getWatchList()
  // val stats = statsArea.getStats()
  val stats = statsArea.getHelp()
  val controls = controlsArea.getControls()

  contents = new BorderPanel {
    layout(ticker) = North
    layout(watchList) = West
    layout(plot) = Center
    layout(stats) = East
    layout(controls) = South
  }

}