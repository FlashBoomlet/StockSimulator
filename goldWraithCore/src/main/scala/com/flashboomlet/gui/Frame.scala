package com.flashboomlet.gui

import scala.swing.Dimension
import scala.swing.Label
import scala.swing.MainFrame

class Frame extends MainFrame {

  title = "GUI Program #1"
  preferredSize = new Dimension(320, 240)
  contents = new Label("Here is the contents!")

}