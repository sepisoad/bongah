package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.PROPS
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.layout.VBox

class AboutAppController {
  @FXML private lateinit var labelSoftwareVersion: Label
  @FXML private lateinit var labelSoftwareName: Label
  @FXML private lateinit var lebelDeveloperTitle: Label
  @FXML private lateinit var lebelDeveloperValue: Label
  @FXML private lateinit var lebelEmailValue: Label
  @FXML private lateinit var lebelEmailTitle: Label
  @FXML private lateinit var lebelPhoneTitle: Label
  @FXML private lateinit var lebelPhoneValue: Label

  @FXML fun initialize(): Unit{
    labelSoftwareName.text = PROPS.getProperty("appName")
    labelSoftwareVersion.text = PROPS.getProperty("appVersion")
    lebelDeveloperTitle.text = PROPS.getProperty("developerNameTitle")
    lebelDeveloperValue.text = PROPS.getProperty("developerNameValue")
    lebelEmailTitle.text = PROPS.getProperty("developerEmailTitle")
    lebelEmailValue.text = PROPS.getProperty("developerEmailValue")
    lebelPhoneTitle.text = PROPS.getProperty("developerPhoneTitle")
    lebelPhoneValue.text = PROPS.getProperty("developerPhoneValue")
  }
}