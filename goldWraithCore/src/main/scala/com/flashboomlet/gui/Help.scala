package com.flashboomlet.gui

import java.awt.Color

import com.flashboomlet.controls.ShellHelp
import scala.swing.Dimension
import scala.swing.Panel
import scala.swing.ScrollPane
import scala.swing.TextArea

/**
  * Created by ttlynch on 4/22/17.
  */
object Help extends GUIConstants {


  val shellOutput = new TextArea("")


  // This is where the listener will come in.
  // http://stackoverflow.com/questions/13199439/listeners-and-reactions-in-scala-swing

  def getHelp(): Panel = {


    val height = MiddleHeight
    val width = EastWidth.toInt


    val outputPane = new ScrollPane(shellOutput)
    outputPane.horizontalScrollBar
    outputPane.verticalScrollBar
    shellOutput.background = Color.WHITE
    shellOutput.charWrap = false
    shellOutput.lineWrap = false
    shellOutput.wordWrap = false
    shellOutput.editable = false
    shellOutput.tabSize = 2
    shellOutput.columns = 45
    shellOutput.rows = 35
    shellOutput.text = ShellHelp.allHelp()
    // shellOutput.preferredSize = new Dimension(width, outputHeight)


    val plot = new Panel() {
      private val myContents = new Content += outputPane

      override def contents = myContents

    }
    plot.background = CenterColor
    val size = new Dimension(width, height)
    plot.preferredSize = size
    plot.minimumSize = size



    plot
  }


}
