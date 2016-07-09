package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.helper.OnWhenPicturesReady
import com.sepisoad.bongah.ui.widget.ImageViewEx
import com.sun.javafx.application.HostServicesDelegate
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.stage.FileChooser
import javafx.stage.Stage
import java.net.URI

class PicturesController {
  lateinit var onWhenPicturesReady: OnWhenPicturesReady
  var movedPictures: MutableList<URI> = mutableListOf()
  var tempPictures: MutableList<URI> = mutableListOf()
  @FXML private lateinit var flowPaneMain: FlowPane
  @FXML private lateinit var buttonAdd: Button

  @FXML private fun initialize(): Unit {
    buttonAdd.text = PROPS.getProperty("add")
  }

  @FXML fun onButtonAddAction(/*event: ActionEvent*/): Unit {
    val stage = flowPaneMain.scene.window as Stage
    val fileChooser = FileChooser()
    val filters = FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif", "*.tiff")
    fileChooser.extensionFilters.add(filters)
    val files = fileChooser.showOpenMultipleDialog(stage);

    if(null == files)
      return

    if(tempPictures.size > 0){
      for(file in files) {
        var canAdd = true
        for (pic in tempPictures) {
          if (pic == file.toURI()) {
            canAdd = false
            break
          }
        }
        if(canAdd) {
          addPictures(file.toURI())
        }
      }
    }else{
      for(file in files) {
        addTempPictures(file.toURI())
      }
    }
  }

  fun onWhenPicturesReadyHandler(): MutableList<URI> {
    return tempPictures
  }

  fun addPictures(file: URI): Unit {
    var imageView = ImageViewEx(file)
    val lable = Label(null, imageView)
    lable.contextMenu = getContextMenu(lable)
    lable.onMouseClicked = EventHandler<MouseEvent> {
      event ->
      val hsd = HostServicesDelegate.getInstance(FxApp.appObj)
      hsd.showDocument(file.toString())
    }
    imageView.fitWidth = 100.0
    imageView.fitHeight = 100.0
    flowPaneMain.children.add(lable)
  }

  fun addTempPictures(file: URI): Unit {
    addPictures(file)
    tempPictures.add(file)
  }

  fun addMovedPictures(file: URI): Unit {
    addPictures(file)
    movedPictures.add(file)
  }

  fun getContextMenu(label: Label): ContextMenu{
    val result = LabelContextMenu(label)
    val itemRemove = MenuItem(PROPS.getProperty("remove"))
    result.items.add(itemRemove)
    itemRemove.onAction = EventHandler {
      event -> removeImage(label)
    }
    return result
  }

  fun removeImage(label: Label): Boolean {
    val labels = flowPaneMain.children
    val imageView = label.graphic as ImageViewEx
    val uri = imageView.uri
    tempPictures.remove(uri)
    label.isVisible = false
    labels.remove(label)

    return true
  }

  class LabelContextMenu(val label: Label) : ContextMenu()

  fun setPictures(_tempPictures: MutableList<URI>, _movedPictures: MutableList<URI>) {
    if(_movedPictures.size > 0){
      for(file in _movedPictures)
        addMovedPictures(file)
    }

    if(_tempPictures.size > 0){
      for(file in _tempPictures)
        addTempPictures(file)
    }
  }

}
