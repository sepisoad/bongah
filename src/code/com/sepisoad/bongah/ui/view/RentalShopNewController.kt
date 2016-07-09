package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.APP_RENTAL_SHOP_IMG_DIR_PATH
import com.sepisoad.bongah.app.AppBaseException
import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableRentalHouse
import com.sepisoad.bongah.db.TableRentalShop
import com.sepisoad.bongah.model.*
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.form.RentalHouseFormPrinter
import com.sepisoad.bongah.ui.form.RentalShopFormPrinter
import com.sepisoad.bongah.ui.helper.*
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.NodeOrientation
import javafx.stage.Stage
import java.net.URI
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.Modality
import jodd.io.FileNameUtil
import tornadofx.selectedItem
import java.io.File
import java.text.DecimalFormat
import java.util.*

class RentalShopNewController {
  var parent: Stage? = null
  var movedPictures: MutableList<URI> = mutableListOf()
  var tempPictures: MutableList<URI> = mutableListOf()
  var viewMode = ViewMode.STAND_ALONE
  var editingObjId = 0
  lateinit var onAfterSaveAction: OnAfterSaveAction
  lateinit var updateActionHandler: UpdateActionHandler
  @FXML private lateinit var checkBoxHasWater: CheckBox
  @FXML private lateinit var hBoxRoot: HBox
  @FXML private lateinit var comboBoxRoofType: ComboBox<RoofType>
  @FXML private lateinit var textFieldReferrer: TextField
  @FXML private lateinit var textFieldAdvanceAmount: TextField
  @FXML private lateinit var buttonFindReferrerId: Button
  @FXML private lateinit var labelRentalAmount: Label
  @FXML private lateinit var labelFacilityPhoneNumbers: Label
  @FXML private lateinit var buttonPrintPreview: Button
  @FXML private lateinit var buttonSave: Button
  @FXML private lateinit var labelDocumentDate: Label
  @FXML private lateinit var labelMortageAmount: Label
  @FXML private lateinit var textAreaDescription: TextArea
  @FXML private lateinit var textFieldAddress: TextField
  @FXML private lateinit var textFieldMortageAmount: TextField
  @FXML private lateinit var labelRentalType: Label
  @FXML private lateinit var labelRoofType: Label
  @FXML private lateinit var textFieldOwnerName: TextField
  @FXML private lateinit var scrollPaneMain: ScrollPane
  @FXML private lateinit var textFieldDate: TextField
  @FXML private lateinit var labelArea: Label
  @FXML private lateinit var textFieldOwnerPhoneNumbers: TextField
  @FXML private lateinit var labelOwnerPhoneNumbers: Label
  @FXML private lateinit var labelAdvanceAmount: Label
  @FXML private lateinit var labelOwnerName: Label
  @FXML private lateinit var labelPhoneNumbers: Label
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var comboBoxRentalType: ComboBox<RentalType>
  @FXML private lateinit var textFieldRentalAmount: TextField
  @FXML private lateinit var checkBoxHasElectricity: CheckBox
  @FXML private lateinit var hBoxBottom: HBox
  @FXML private lateinit var hBoxFather: VBox
  @FXML private lateinit var labelReferrer: Label
  @FXML private lateinit var checkBoxHasGas: CheckBox
  @FXML private lateinit var checkBoxHasPhone: CheckBox
  @FXML private lateinit var textFieldArea: TextField
  @FXML private lateinit var buttonPictures: Button
  @FXML private lateinit var textFieldPhoneNumbers: TextField

  @FXML private fun initialize(): Unit {
    textFieldDate.textProperty().addListener(DateChangeListener(textFieldDate))
    textFieldReferrer.textProperty().addListener(IntegerNumericChangeListener(textFieldReferrer))
    textFieldAddress.textProperty().addListener(SqlChangeListener(textFieldAddress))
    textFieldOwnerName.textProperty().addListener(SqlChangeListener(textFieldOwnerName))
    textFieldOwnerPhoneNumbers.textProperty().addListener(SqlChangeListener(textFieldOwnerPhoneNumbers))
    textFieldArea.textProperty().addListener(FloatingNumericChangeListener(textFieldArea))

    textFieldAdvanceAmount.textProperty().addListener(FloatingNumericChangeListener(textFieldAdvanceAmount))
    textFieldRentalAmount.textProperty().addListener(FloatingNumericChangeListener(textFieldRentalAmount))
    textFieldMortageAmount.textProperty().addListener(FloatingNumericChangeListener(textFieldMortageAmount))

    labelDocumentDate.text = PROPS.getProperty("documentDate")
    labelReferrer.text = PROPS.getProperty("referrerCode")
    buttonFindReferrerId.text = PROPS.getProperty("findReferrerId")
    labelOwnerName.text = PROPS.getProperty("owner")
    labelOwnerPhoneNumbers.text = PROPS.getProperty("phoneNumbers")
    labelAddress.text = PROPS.getProperty("address")
    labelRentalType.text = PROPS.getProperty("rentalType")
    buttonPictures.text = PROPS.getProperty("pictures")
    labelArea.text = PROPS.getProperty("area")
    labelRoofType.text = PROPS.getProperty("roofType")
    buttonSave.text = PROPS.getProperty("save")
    buttonPrintPreview.text = PROPS.getProperty("printPreview")

    labelAdvanceAmount.text = PROPS.getProperty("advanceAmount")
    labelRentalAmount.text = PROPS.getProperty("rentalAmount")
    labelMortageAmount.text = PROPS.getProperty("mortageAmount")

    checkBoxHasWater.text = PROPS.getProperty("water")
    checkBoxHasElectricity.text = PROPS.getProperty("electricity")
    checkBoxHasGas.text = PROPS.getProperty("gas")
    checkBoxHasPhone.text = PROPS.getProperty("phone")
    labelPhoneNumbers.text = PROPS.getProperty("registeredPhoneNumbers")

    comboBoxRentalType.items.add(RentalType.UNDEFINED)
    comboBoxRentalType.items.add(RentalType.RENTAL)
    comboBoxRentalType.items.add(RentalType.MORTAGE)
    comboBoxRentalType.items.add(RentalType.BOTH)
    comboBoxRentalType.selectionModel.select(0)

    comboBoxRoofType.items.add(RoofType.UNDEFINED)
    comboBoxRoofType.items.add(RoofType.FREE)
    comboBoxRoofType.items.add(RoofType.UNDER_BUILDING)
    comboBoxRoofType.items.add(RoofType.INSIDE_BUILDING)
    comboBoxRoofType.selectionModel.select(0)
  }

  fun getRentalShopInfo(): RentalShopInfo {
    val referrerId = if(!textFieldReferrer.text.isNullOrEmpty())
      textFieldReferrer.text.toInt() else -1
    val hasReferrer = if (referrerId != -1) true else false
    val date = textFieldDate.text

    val address = textFieldAddress.text
    val ownerName = textFieldOwnerName.text
    val ownerPhoneNumbers = textFieldOwnerPhoneNumbers.text
    val rentalType = comboBoxRentalType.selectionModel.selectedItem
    val latitude = 0.0 //FIXME
    val longitude = 0.0 //FIXME
    val pictures = ""
    val description = textAreaDescription.text

    val phoneNumbers = textFieldPhoneNumbers.text
    val hasPhone = if(checkBoxHasPhone.isSelected) true else false
    val hasWater = if(checkBoxHasWater.isSelected) true else false
    val hasElectricity = if(checkBoxHasElectricity.isSelected) true else false
    val hasGas = if(checkBoxHasGas.isSelected) true else false
    val roofType = comboBoxRoofType.selectionModel.selectedItem

    var area = 0.0
    var advanceAmount = 0.0
    var rentalAmount = 0.0
    var mortageAmount = 0.0

    try{
      area = if(!textFieldArea.text.isNullOrEmpty())
        textFieldArea.text.toDouble() else 0.0
    }catch(ex: NumberFormatException){
      throw AppBaseException(PROPS.getProperty("alertMsgInvalidAreaValue"))
    }

    try{
      advanceAmount = if(!textFieldAdvanceAmount.text.isNullOrEmpty())
        textFieldAdvanceAmount.text.toDouble() else 0.0
    }catch(ex: NumberFormatException){
      throw AppBaseException(PROPS.getProperty("TODO"))
    }

    try{
      rentalAmount = if(!textFieldRentalAmount.text.isNullOrEmpty())
        textFieldRentalAmount.text.toDouble() else 0.0
    }catch(ex: NumberFormatException){
      throw AppBaseException(PROPS.getProperty("TODO"))
    }

    try{
      mortageAmount = if(!textFieldMortageAmount.text.isNullOrEmpty())
        textFieldMortageAmount.text.toDouble() else 0.0
    }catch(ex: NumberFormatException){
      throw AppBaseException(PROPS.getProperty("TODO"))
    }

    if(ownerName.isNullOrEmpty()) {
      throw AppBaseException(PROPS.getProperty("alertMsgOwnerNotSet"))
    }
    if(address.isNullOrEmpty()) {
      throw AppBaseException(PROPS.getProperty("alertMsgAddressNotSet"))
    }

    val obj = RentalShopInfo(
      0, hasReferrer, referrerId, date.toString(), address, area, ownerName, ownerPhoneNumbers,
      rentalType, advanceAmount, rentalAmount, mortageAmount, latitude, longitude, pictures,
      phoneNumbers, hasPhone, hasWater, hasElectricity, hasGas, roofType, description)
    return obj
  }

  fun populateData(obj: RentalShopInfo): Unit {
    val decimalFormat = DecimalFormat("#.##")
    textFieldDate.text = obj.date
    textFieldReferrer.text = obj.referrerId.toString()
    textFieldAdvanceAmount.text = decimalFormat.format(obj.advanceAmount)
    textFieldRentalAmount.text = decimalFormat.format(obj.rentalAmount)
    textFieldMortageAmount.text = decimalFormat.format(obj.mortageAmount)

    textFieldArea.text = decimalFormat.format(obj.area)
    textFieldAddress.text = obj.address
    textFieldOwnerName.text = obj.ownerName
    textFieldOwnerPhoneNumbers.text = obj.ownerPhoneNumbers

    comboBoxRentalType.selectionModel.select(obj.rentalType)
    comboBoxRoofType.selectionModel.select(obj.roofType)
    textAreaDescription.text = obj.description

    checkBoxHasElectricity.isSelected = obj.hasElectricity
    checkBoxHasGas.isSelected = obj.hasGas
    checkBoxHasPhone.isSelected = obj.hasPhone
    checkBoxHasWater.isSelected = obj.hasWater

    textFieldPhoneNumbers.text = obj.phoneNumbers

    getPicturesPath(obj)
    editingObjId = obj.id
  }

  @FXML fun onButtonPrintPreviewAction(): Unit{
    try{
      val orientation = if(Config.layoutDirection == "LFT")
        NodeOrientation.LEFT_TO_RIGHT else NodeOrientation.RIGHT_TO_LEFT

      val rentalShopInfo = getRentalShopInfo()
      RentalShopFormPrinter.print(FxApp.appObj, orientation, rentalShopInfo)
    }catch(ex: AppBaseException){
      val alert = Alert(Alert.AlertType.WARNING, ex.message, ButtonType.CLOSE)
      alert.showAndWait()
    }
  }

  @FXML fun onButtonSaveAction(): Unit {
    do{
      var whenBlockSuccessful = false
      when(viewMode){
        ViewMode.STAND_ALONE -> {
          try{
            val rentalShopInfo = getRentalShopInfo()
            val id = TableRentalShop.insert(rentalShopInfo)
            if(id != -1){
              if(movePictures(id)){
                val pictures = getMovedFileName()
                if(TableRentalShop.updatePictures(pictures, id)){
                  val alertMsg = PROPS.getProperty("alertMsgSavedSuccessfully")
                  val alert = Alert(Alert.AlertType.INFORMATION, alertMsg, ButtonType.CLOSE)
                  alert.showAndWait()
                  onAfterSaveAction.handle()
                  whenBlockSuccessful = true
                }
              }
            }
          }catch(ex: AppBaseException){
            val alert = Alert(Alert.AlertType.WARNING, ex.message, ButtonType.CLOSE)
            alert.showAndWait()
          }
        }
        ViewMode.DEPENDENT -> {
          try{
            val rentalShopInfo = getRentalShopInfo()
            movePictures(editingObjId)
            rentalShopInfo.pictures = getMovedFileName()
            if(updateActionHandler.handle(rentalShopInfo)) {
              val alertMsg = PROPS.getProperty("alertMsgUpdatedSuccessfully")
              val alert = Alert(Alert.AlertType.INFORMATION, alertMsg, ButtonType.CLOSE)
              alert.showAndWait()
              onAfterSaveAction.handle()
              whenBlockSuccessful = true
            }else{
              //TODO
            }

            if(null != parent){
              parent?.hide()
            }
          }catch(ex: AppBaseException){
            val alert = Alert(Alert.AlertType.WARNING, ex.message, ButtonType.CLOSE)
            alert.showAndWait()
          }
        }
      }
      if(!whenBlockSuccessful)
        break
    }while(false)
  }

  @FXML fun onButtonFindReferrerIdAction(): Unit{
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/colleague_find.fxml")

    val root = loader.load<VBox>()
    var colleagueFindCtrl: ColleagueFindController
    colleagueFindCtrl = loader.getController()
    colleagueFindCtrl.findReferrerIdActionHandler = FindReferrerId()
    colleagueFindCtrl.viewMode = ViewMode.DEPENDENT

    val stage = Stage()
    val scene = Scene(root)
    stage.scene = scene

    if(Config.layoutDirection.equals("RTL"))
      root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
    if(Config.layoutDirection.equals("LTR"))
      root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

    root.setPrefSize(500.0, 300.0)
    stage.initModality(Modality.APPLICATION_MODAL)
    stage.showAndWait()
    colleagueFindCtrl.findReferrerIdAction()
  }

  @FXML fun onButtonPicturesAction(): Unit {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/pictures.fxml")

    val root = loader.load<VBox>()
    var picturesCtrl: PicturesController
    picturesCtrl = loader.getController()

    val stage = Stage()
    val scene = Scene(root)
    stage.scene = scene

    stage.onCloseRequest = EventHandler {
      event ->
      tempPictures = picturesCtrl.tempPictures
      movedPictures = picturesCtrl.movedPictures
    }

    if(Config.layoutDirection.equals("RTL"))
      root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
    if(Config.layoutDirection.equals("LTR"))
      root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

    cl.getResource("res/ui/pictures.fxml").toExternalForm()
    scene.stylesheets.add(cl.getResource("res/ui/pictures.fxml").toExternalForm())

    stage.initModality(Modality.APPLICATION_MODAL)
    picturesCtrl.setPictures(tempPictures, movedPictures)
    stage.showAndWait()
  }

  fun movePictures(id: Int): Boolean{
    var result = false

    do{
      val imageDir = File(APP_RENTAL_SHOP_IMG_DIR_PATH  + File.separator + id.toString())
      if(tempPictures.size > 0)
        if(!imageDir.exists())
          if(!imageDir.mkdir())
            break
      for(pic in tempPictures){
        val uuid = UUID.randomUUID()
        val targetFile = File(APP_RENTAL_SHOP_IMG_DIR_PATH  +
          File.separator +
          id.toString() +
          File.separator +
          uuid.toString() + "." + FileNameUtil.getExtension(pic.toASCIIString()))
        val sourceFile = File(pic)
        sourceFile.copyTo(targetFile, true)
        movedPictures.add(targetFile.toURI())
      }
      tempPictures.clear()
      result = true
    }while(false)

    return result
  }

  fun getPicturesFileName(): String {
    var result = ""

    for(file in tempPictures)
    {
      result += FileNameUtil.getName(file.toString()) + "#"
    }

    for(file in movedPictures)
    {
      result += FileNameUtil.getName(file.toString()) + "#"
    }

    return result
  }

  fun getMovedFileName(): String {
    var result = ""

    for(file in movedPictures)
    {
      result += FileNameUtil.getName(file.toString()) + "#"
    }

    return result
  }

  fun getPicturesPath(obj: RentalShopInfo): Unit{
    val listNames = obj.pictures.split('#')
    if(listNames.size <= 0)
      return

    val imageDirPath = APP_RENTAL_SHOP_IMG_DIR_PATH  + File.separator + obj.id.toString()
    for(name in listNames){
      if(name == "")
        continue
      val imageFilePath = File(imageDirPath + File.separator + name)
      movedPictures.add(imageFilePath.toURI())
    }
  }

  //--------------------------
  inner class FindReferrerId : FindReferrerIdActionHandler {
    override fun handle(OBJ: Any?) {
      if(null != OBJ){
        val obj = OBJ as RentalShopInfo
        textFieldReferrer.text = obj.id.toString()
      }
    }
  }
}