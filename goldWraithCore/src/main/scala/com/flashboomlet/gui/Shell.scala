package com.flashboomlet.gui


import java.awt.Color
import javax.swing.text.DefaultCaret

import com.flashboomlet.controls.StockShell

import scala.swing.Button
import scala.swing.Dimension
import scala.swing.Label
import scala.swing.MainFrame
import scala.swing.Panel
import scala.swing.ScrollPane
import scala.swing.TextArea
import scala.swing.TextField
import scala.swing.event.ButtonClicked
import scala.swing.event.Key
import scala.swing.event.KeyPressed

/**
  * Created by ttlynch on 4/22/17.
  */
object Shell extends GUIConstants {

  // Simple reference to the shell
  val shell = StockShell


  val shellLabel = new Label("StockShell:")
  val shellInput = new TextField("")
  val shellSubmit = new Button("Submit")
  val shellOutput = new TextArea("")


  // This is where the listener will come in.
  // http://stackoverflow.com/questions/13199439/listeners-and-reactions-in-scala-swing

  def getShell(): Panel = {


    val height = MiddleHeight
    val width = CenterWidth.toInt
    val inputWidth = width - ((width/8)*3)
    val submitWidth = width/8

    val inputHeight = MiddleHeight/16
    val outputHeight = height - (height/16)

    shellLabel.foreground = Color.WHITE
    shellLabel.preferredSize = new Dimension(submitWidth, inputHeight)

    shellInput.background = Color.WHITE
    shellInput.foreground = Color.BLACK
    shellInput.preferredSize = new Dimension(inputWidth, inputHeight)

    shellSubmit.background = Color.RED
    shellSubmit.preferredSize = new Dimension(submitWidth, inputHeight)

    val outputPane = new ScrollPane(shellOutput)
    outputPane.horizontalScrollBar
    shellOutput.background = Color.WHITE
    shellOutput.charWrap = false
    shellOutput.lineWrap = true
    shellOutput.wordWrap = true
    shellOutput.editable = false
    shellOutput.tabSize = 2
    shellOutput.columns = 69
    shellOutput.rows = 34
    // shellOutput.preferredSize = new Dimension(width, outputHeight)


    val plot = new Panel() {
      private val myContents = new Content += shellLabel
      myContents += shellInput
      myContents += shellSubmit
      myContents += outputPane

      override def contents = myContents

      listenTo(shellInput.keys)
      listenTo(shellSubmit)
      reactions += {
        case KeyPressed(_, Key.Enter , _, _) =>
          shellOutput.text +=  sendToShell(shellInput.text) + "\n"
        case ButtonClicked(_) =>
          shellOutput.text +=  sendToShell(shellInput.text) + "\n"
      }
      focusable = true
      requestFocus
    }
    plot.background = CenterColor
    val size = new Dimension(CenterWidth.toInt, MiddleHeight)
    plot.preferredSize = size
    plot.minimumSize = size



    plot
  }

  def sendToShell(input: String): String = {
    // Send to StockShell and get output add to the input text.
    shellInput.text = ""
    if(input.nonEmpty) "stockShell$ " + input + "\n\n" + shell.parseLine(input)
    else "stockShell$"
  }

}
