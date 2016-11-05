package com.flashboomlet.gui

import java.awt.Color

import scala.swing.Dimension
import scala.swing.Label
import scala.swing.Panel

/**
  * Created by ttlynch on 11/5/16.
  */
class Controls extends GUIConstants {

  def getControls(): Panel = {
    val controls = new Panel() {
      private val myContents = new Content += new Label("Controls")
      override def contents = myContents
    }
    val size = new Dimension(GUIWidth, BottomHeight)
    controls.preferredSize = size
    controls.minimumSize = size


    controls.background = SouthColor
    controls
  }

}
