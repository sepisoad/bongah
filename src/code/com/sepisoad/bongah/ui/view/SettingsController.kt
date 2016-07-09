package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.APP_CONFIG_PATH
import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.EMPTY_PASSWORD
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableLogin
import com.sepisoad.bongah.model.LanguageType
import com.sepisoad.bongah.ui.helper.IntegerNumericChangeListener
import com.sepisoad.bongah.ui.helper.OnAfterSaveAction
import com.sepisoad.bongah.ui.helper.SqlChangeListener
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.stage.Stage
import jodd.io.FileUtil
import jodd.json.JsonSerializer

class SettingsController {
  var parent: Stage? = null
  var wasPasswordEnabled = false;
  lateinit var onAfterSaveAction: OnAfterSaveAction

  @FXML private lateinit var textFieldWindowWidth: TextField
  @FXML private lateinit var checkBoxUsePassword: CheckBox
  @FXML private lateinit var labelOldPassword: Label
  @FXML private lateinit var comboBoxLanguage: ComboBox<LanguageType>
  @FXML private lateinit var labelWindowWidth: Label
  @FXML private lateinit var textFieldWindowHeight: TextField
  @FXML private lateinit var buttonSave: Button
  @FXML private lateinit var labelNewPassword: Label
  @FXML private lateinit var textFieldOldPassword: TextField
  @FXML private lateinit var labelLanguage: Label
  @FXML private lateinit var labelWindowHeight: Label
  @FXML private lateinit var textFieldNewPassword: TextField

  @FXML fun initialize() {
    labelLanguage.text = PROPS.getProperty("language")
    labelWindowWidth.text = PROPS.getProperty("defaultWindowWidth")
    labelWindowHeight.text = PROPS.getProperty("defaultWindowHeight")
    checkBoxUsePassword.text = PROPS.getProperty("usePassword")
    labelOldPassword.text = PROPS.getProperty("oldPassword")
    labelNewPassword.text = PROPS.getProperty("newPassword")
    buttonSave.text = PROPS.getProperty("save")

    comboBoxLanguage.items.add(LanguageType.PERSIAN)
    comboBoxLanguage.items.add(LanguageType.ENGLISH)
    comboBoxLanguage.selectionModel?.select(0)

    textFieldWindowWidth.textProperty().addListener(IntegerNumericChangeListener(textFieldWindowWidth))
    textFieldWindowHeight.textProperty().addListener(IntegerNumericChangeListener(textFieldWindowHeight))
    textFieldOldPassword.textProperty().addListener(SqlChangeListener(textFieldOldPassword))
    textFieldNewPassword.textProperty().addListener(SqlChangeListener(textFieldNewPassword))

    textFieldWindowWidth.text = Config.windowWidth.toString()
    textFieldWindowHeight.text = Config.windowHeight.toString()
    checkBoxUsePassword.isSelected = Config.usePassword
    if(Config.language == "fa")
      comboBoxLanguage.selectionModel?.select(0)
    else
      comboBoxLanguage.selectionModel?.select(1)

    if(!Config.usePassword){
      textFieldOldPassword.isDisable = true
      textFieldNewPassword.isDisable = true
      wasPasswordEnabled = false
    }else{
      textFieldOldPassword.isDisable = false
      textFieldNewPassword.isDisable = false
      wasPasswordEnabled = true
    }

    HIDE_CONTROLS()
  }

  fun HIDE_CONTROLS(){
    checkBoxUsePassword.isVisible = false
    labelOldPassword.isVisible = false
    labelNewPassword.isVisible = false

    Config.usePassword = false
  }

  @FXML fun onCheckBoxUsePasswordAction(event: ActionEvent) {
    val isSelected = (event.source as CheckBox).isSelected

    if(!isSelected){
      textFieldOldPassword.isDisable = true
      textFieldNewPassword.isDisable = true
      wasPasswordEnabled = true
    }else{
      textFieldOldPassword.isDisable = false
      textFieldNewPassword.isDisable = false
      wasPasswordEnabled = false
    }
  }

  @FXML fun onButtonSaveAction(event: ActionEvent){
    val usePassword = checkBoxUsePassword.isSelected
    val windowWidth = Integer.parseInt(textFieldWindowWidth.text) ?: 0
    val windowHeight = Integer.parseInt(textFieldWindowHeight.text) ?: 0
    val language = comboBoxLanguage.selectionModel.selectedItem
    var oldPassword = textFieldOldPassword.text ?: ""
    val newPassword = textFieldNewPassword.text ?: ""
    val dbPassword = TableLogin.get()

//    if(dbPassword == EMPTY_PASSWORD)
//      oldPassword = EMPTY_PASSWORD
//
//    if(wasPasswordEnabled && !usePassword){
//      if(!newPassword.isNullOrEmpty()){
//        if(dbPassword != oldPassword){
//          val alertMsg = PROPS.getProperty("alertMsgIncorrectPassword")
//          val alert = Alert(Alert.AlertType.ERROR)
//          alert.title = PROPS.getProperty("error")
//          alert.headerText = PROPS.getProperty("error")
//          alert.contentText = alertMsg
//          alert.showAndWait()
//          return
//        }
//      }
//    }
//
//    if(!newPassword.isNullOrEmpty()){
//      if(dbPassword != oldPassword){
//        val alertMsg = PROPS.getProperty("alertMsgIncorrectPassword")
//        val alert = Alert(Alert.AlertType.ERROR)
//        alert.title = PROPS.getProperty("error")
//        alert.headerText = PROPS.getProperty("error")
//        alert.contentText = alertMsg
//        alert.showAndWait()
//        return
//      }
//    }

    if(language == LanguageType.PERSIAN) {
      Config.language = "fa"
      Config.layoutDirection = "RTL"
    }else{
      Config.language = "en"
      Config.layoutDirection = "LTR"
    }

    if(windowHeight > 200 && windowHeight < 1500)
      Config.windowHeight = windowHeight
    if(windowWidth > 200 && windowWidth < 1500)
      Config.windowWidth = windowWidth

//    if(usePassword)
//      Config.usePassword = true
//    else
//      Config.usePassword = false

    try{
      val js = JsonSerializer.create()
      val jsonStr = js.serialize(Config) ?: ""
      FileUtil.writeString(APP_CONFIG_PATH, jsonStr)
    }catch(ex: Exception){
      val alertMsg = PROPS.getProperty("alertMsgSettingsNotApplied")
      val alert = Alert(Alert.AlertType.ERROR)
      alert.title = PROPS.getProperty("error")
      alert.headerText = PROPS.getProperty("error")
      alert.contentText = alertMsg
      alert.showAndWait()
    }

    val alertMsg = PROPS.getProperty("alertMsgSavedSuccessfully")
    val alert = Alert(Alert.AlertType.INFORMATION, alertMsg, ButtonType.CLOSE)
    alert.showAndWait()

  }
}