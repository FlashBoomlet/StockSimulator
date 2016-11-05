package com.flashboomlet.gui

import java.awt.Color

/**
  * Created by ttlynch on 7/7/16.
  */
trait GUIConstants {

  val GUIWidth = 1280

  val GUIHeight = 720

  val TopHeight = 25

  val BottomHeight = 125

  val MiddleHeight = GUIHeight - TopHeight - BottomHeight

  val CenterWidth = GUIWidth * 0.60

  val SlackSpace = GUIWidth - CenterWidth

  val WestWidth = SlackSpace * 0.25

  val EastWidth = SlackSpace * 0.75

  val NorthColor = Color.BLACK

  val WestColor = Color.BLUE

  val CenterColor = Color.BLACK

  val EastColor = Color.GREEN

  val SouthColor = Color.LIGHT_GRAY

}
