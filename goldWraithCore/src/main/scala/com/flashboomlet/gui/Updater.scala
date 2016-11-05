package com.flashboomlet.gui

import com.flashboomlet.data.PlotData
import com.flashboomlet.data.StatsData
import com.flashboomlet.data.TickerData
import com.flashboomlet.data.WatchListData

/**
  * Updater is a system to update information displayed in the GUI
  * Responsible for all information updating
  *
  * Created by ttlynch on 11/5/16.
  */
class Updater {

  private val plotArea = new Plot
  private val tickerArea = new Ticker
  private val watchListArea = new WatchList
  private val statsArea = new Stats
  private val controlsArea = new Controls

  def UpdateInfo(): Unit = {
    UpdateTicker()
    UpdatePlot()
    UpdateWatchList()
    UpdateStats()
  }

  /**
    * Update Ticker updates the information that is feeding the ticker in the GUI
    */
  private def UpdateTicker(): Unit = {
    tickerArea.UpdateData(List(new TickerData()))
  }

  /**
    * Update Plot updates the information being displayed in the plot in the GUI
    */
  private def UpdatePlot(): Unit = {
    plotArea.UpdateData(List(new PlotData()))
  }

  /**
    * Update Watch List updates the information that is being displayed in the watchlist
    */
  private def UpdateWatchList(): Unit = {
    watchListArea.UpdateData(List(new WatchListData()))
  }

  /**
    * Update Stats updates the information that is being displayed in the Stats Panel
    */
  private def UpdateStats(): Unit = {
    statsArea.UpdateData(List(new StatsData()))
  }
}
