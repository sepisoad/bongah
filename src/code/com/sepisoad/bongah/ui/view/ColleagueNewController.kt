package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableColleague
import com.sepisoad.bongah.model.ColleagueInfo
import com.sepisoad.bongah.ui.helper.OnAfterSaveAction
import com.sepisoad.bongah.ui.helper.UpdateActionHandler
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.helper.SqlChangeListener
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage

class ColleagueNewController(){
  var parent: Stage? = null
  lateinit var onAfterSaveAction: OnAfterSaveAction
  lateinit var updateActionHandler: UpdateActionHandler
  @FXML private lateinit var textFieldName: TextField
  @FXML private lateinit var buttonSave: Button
  @FXML private lateinit var labelPhoneNumbers: Label
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var textFieldAddress: TextField
  @FXML private lateinit var labelName: Label
  @FXML private lateinit var textFieldPhoneNumbers: TextField
  var viewMode = ViewMode.STAND_ALONE


  @FXML fun initialize(): Unit{
    textFieldName.textProperty().addListener(SqlChangeListener(textFieldName))
    textFieldPhoneNumbers.textProperty().addListener(SqlChangeListener(textFieldPhoneNumbers))
    textFieldAddress.textProperty().addListener(SqlChangeListener(textFieldAddress))

    labelPhoneNumbers.text = PROPS.getProperty("phoneNumbers")
    labelAddress.text = PROPS.getProperty("address")
    labelName.text = PROPS.getProperty("name")
    buttonSave.text = PROPS.getProperty("save")
  }

  fun populateData(obj: ColleagueInfo): Unit {
    textFieldName.text = obj.name
    textFieldPhoneNumbers.text = obj.phoneNumbers
    textFieldAddress.text = obj.address
  }

  fun get(): ColleagueInfo {
    val name = textFieldName.text
    val phoneNumbers = textFieldPhoneNumbers.text
    val address = textFieldAddress.text
    val obj = ColleagueInfo(0, name, phoneNumbers, address, "")

    return obj
  }

  @FXML fun onButtonSaveAction(/*event: ActionEvent*/): Unit {
    val obj = get()
    when(viewMode){
      ViewMode.STAND_ALONE -> {
        if(!TableColleague.insert(obj)) {
          kotlin.io.println("TODO")
        }else{
          val alertMsg = PROPS.getProperty("alertMsgSavedSuccessfully")
          val alert = Alert(Alert.AlertType.INFORMATION, alertMsg, ButtonType.CLOSE)
          alert.showAndWait()
          onAfterSaveAction.handle()
        }

      }
      ViewMode.DEPENDENT -> {
        if(!updateActionHandler.handle(obj)) {
          kotlin.io.println("TODO")
        }else{
          val alertMsg = PROPS.getProperty("alertMsgUpdatedSuccessfully")
          val alert = Alert(Alert.AlertType.INFORMATION, alertMsg, ButtonType.CLOSE)
          alert.showAndWait()
        }
      }
    }

    if(null != parent){
      parent?.hide()
    }
  }
}
