package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.*
import com.sepisoad.bongah.db.TableHouse
import com.sepisoad.bongah.db.TableLand
import com.sepisoad.bongah.model.*
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.form.HouseFormPrinter
import com.sepisoad.bongah.ui.form.LandFormPrinter
import com.sepisoad.bongah.ui.helper.*
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.NodeOrientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import jodd.io.FileNameUtil
import java.io.File
import java.net.URI
import java.text.DecimalFormat
import java.time.LocalDate
import java.util.*

class HouseNewController {
  var parent: Stage? = null
  var movedPictures: MutableList<URI> = mutableListOf()
  var tempPictures: MutableList<URI> = mutableListOf()
  var viewMode = ViewMode.STAND_ALONE
  var editingObjId = 0
  lateinit var onAfterSaveAction: OnAfterSaveAction
  lateinit var updateActionHandler: UpdateActionHandler
  lateinit var textFieldDate: TextField
  @FXML lateinit var labelDocumentDate: Label;
  @FXML lateinit var labelReferrer: Label;
  @FXML lateinit var textFieldReferrer: TextField;
  @FXML lateinit var buttonFindReferrerId: Button;

  @FXML private lateinit var labelOwnerName: Label
  @FXML private lateinit var labelPhoneNumbers: Label
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var labelSindh: Label
  @FXML private lateinit var textFieldPrice: TextField
  @FXML private lateinit var comboBoxSindhType: ComboBox<SindhType>
  @FXML private lateinit var labelPrice: Label
  @FXML private lateinit var buttonPictures: Button
  @FXML private lateinit var textFieldArea: TextField
  @FXML private lateinit var textFieldAddress: TextField
  @FXML private lateinit var checkBoxIsSwappable: CheckBox
  @FXML private lateinit var textFieldOwnerName: TextField
  @FXML private lateinit var labelArea: Label
  @FXML private lateinit var textFieldPhoneNumbers: TextField
  @FXML private lateinit var checkBoxHasWater: CheckBox
  @FXML private lateinit var checkBoxHasElectricity: CheckBox
  @FXML private lateinit var checkBoxHasGas: CheckBox
  @FXML private lateinit var checkBoxHasPhone: CheckBox
  @FXML private lateinit var labelFacilityPhoneNumbers: Label
  @FXML private lateinit var textFieldFacilityPhoneNumbers: TextField
  @FXML private lateinit var buttonSave: Button
  @FXML private lateinit var buttonPrintPreview: Button

  @FXML private lateinit var labelBuildingLevelCount: Label
  @FXML private lateinit var textFieldBuildingLevelCount: TextField
  @FXML private lateinit var labelBuildingLevelNumber: Label
  @FXML private lateinit var textFieldBuildingLevelNumber: TextField
  @FXML private lateinit var checkBoxHasSeparateWay: CheckBox
  @FXML private lateinit var checkBoxHasFurniture: CheckBox
  @FXML private lateinit var checkBoxHasStorageRoom: CheckBox
  @FXML private lateinit var checkBoxHasParking: CheckBox
  @FXML private lateinit var checkBoxHasVideoDoorPhone: CheckBox
  @FXML private lateinit var checkBoxHasAirConditioner: CheckBox
  @FXML private lateinit var checkBoxHasPackagePlumbing: CheckBox
  @FXML private lateinit var checkBoxHasCabinet: CheckBox
  @FXML private lateinit var checkBoxHasWallDresser: CheckBox
  @FXML private lateinit var checkBoxHasLoan: CheckBox
  @FXML private lateinit var labelLoanAmount: Label
  @FXML private lateinit var textFieldLoanAmount: TextField
  @FXML private lateinit var labelBedroomCount: Label
  @FXML private lateinit var textFieldBedroomCount: TextField

  @FXML private lateinit var comboBoxHouseType: ComboBox<HouseType>
  @FXML private lateinit var comboBoxTerrainType: ComboBox<TerrainType>
  @FXML private lateinit var labelTerrain: Label
  @FXML private lateinit var labelHouseType: Label

  @FXML private lateinit var textAreaDescription: TextArea

  @FXML private fun initialize(): Unit {
    textFieldDate.textProperty().addListener(DateChangeListener(textFieldDate))
    textFieldReferrer.textProperty().addListener(IntegerNumericChangeListener(textFieldReferrer))
    textFieldAddress.textProperty().addListener(SqlChangeListener(textFieldAddress))
    textFieldOwnerName.textProperty().addListener(SqlChangeListener(textFieldOwnerName))
    textFieldPhoneNumbers.textProperty().addListener(SqlChangeListener(textFieldPhoneNumbers))
    textFieldFacilityPhoneNumbers.textProperty().addListener(SqlChangeListener(textFieldFacilityPhoneNumbers))
    textFieldPrice.textProperty().addListener(FloatingNumericChangeListener(textFieldPrice))
    textFieldArea.textProperty().addListener(FloatingNumericChangeListener(textFieldArea))

    textFieldBuildingLevelCount.textProperty().addListener(IntegerNumericChangeListener(textFieldBuildingLevelCount))
    textFieldBuildingLevelNumber.textProperty().addListener(IntegerNumericChangeListener(textFieldBuildingLevelNumber))
    textFieldLoanAmount.textProperty().addListener(FloatingNumericChangeListener(textFieldLoanAmount))
    textFieldBedroomCount.textProperty().addListener(IntegerNumericChangeListener(textFieldBedroomCount))

    labelDocumentDate.text = PROPS.getProperty("documentDate")
    labelReferrer.text = PROPS.getProperty("referrerCode")
    buttonFindReferrerId.text = PROPS.getProperty("findReferrerId")
    labelOwnerName.text = PROPS.getProperty("owner")
    labelPhoneNumbers.text = PROPS.getProperty("phoneNumbers")
    labelAddress.text = PROPS.getProperty("address")
    labelSindh.text = PROPS.getProperty("sindhType")
    labelPrice.text = PROPS.getProperty("price")
    buttonPictures.text = PROPS.getProperty("pictures")
    checkBoxIsSwappable.text = PROPS.getProperty("isSwappable")
    labelArea.text = PROPS.getProperty("area")
    checkBoxHasWater.text = PROPS.getProperty("water")
    checkBoxHasElectricity.text = PROPS.getProperty("electricity")
    checkBoxHasGas.text = PROPS.getProperty("gas")
    checkBoxHasPhone.text = PROPS.getProperty("phone")
    labelFacilityPhoneNumbers.text = PROPS.getProperty("registeredPhoneNumbers")
    labelTerrain.text = PROPS.getProperty("terrain")
    labelHouseType.text = PROPS.getProperty("houseType")
    buttonSave.text = PROPS.getProperty("save")
    buttonPrintPreview.text = PROPS.getProperty("printPreview")

    labelBuildingLevelCount.text = PROPS.getProperty("levelCount")
    labelBuildingLevelNumber.text = PROPS.getProperty("levelNumber")
    checkBoxHasSeparateWay.text = PROPS.getProperty("hasSeparateWay")
    checkBoxHasFurniture.text = PROPS.getProperty("hasFurniture")
    checkBoxHasStorageRoom.text = PROPS.getProperty("hasStorageRoom")
    checkBoxHasParking.text = PROPS.getProperty("hasParking")
    checkBoxHasVideoDoorPhone.text = PROPS.getProperty("hasVideoDoorPhone")
    checkBoxHasAirConditioner.text = PROPS.getProperty("hasAirConditioner")
    checkBoxHasPackagePlumbing.text = PROPS.getProperty("hasPackagePlumbing")
    checkBoxHasCabinet.text = PROPS.getProperty("hasCabinet")
    checkBoxHasWallDresser.text = PROPS.getProperty("hasWallDresser")
    checkBoxHasLoan.text = PROPS.getProperty("hasLoan")
    labelLoanAmount.text = PROPS.getProperty("loanAmount")
    labelBedroomCount.text = PROPS.getProperty("bedroomCount")

    comboBoxSindhType.items?.add(SindhType.UNDEFINED)
    comboBoxSindhType.items?.add(SindhType.ENDOWMENT)
    comboBoxSindhType.items?.add(SindhType.URBAN)
    comboBoxSindhType.selectionModel?.select(0)
    comboBoxTerrainType.items.add(TerrainType.UNDEFINED)
    comboBoxTerrainType.items.add(TerrainType.COASTAL)
    comboBoxTerrainType.items.add(TerrainType.FLAT_LAND)
    comboBoxTerrainType.items.add(TerrainType.MOUNTAIN_SIDE)
    comboBoxTerrainType.selectionModel.select(0)
    comboBoxHouseType.items.add(HouseType.UNDEFINED)
    comboBoxHouseType.items.add(HouseType.URBAN_APARTMENT)
    comboBoxHouseType.items.add(HouseType.URBAN_VILLA)
    comboBoxHouseType.items.add(HouseType.RURAL_VILLA)
    comboBoxHouseType.selectionModel.select(0)
  }

  fun getHouseInfo(): HouseInfo {
    val referrerId = if(!textFieldReferrer.text.isNullOrEmpty())
      textFieldReferrer.text.toInt() else -1
    val hasReferrer = if (referrerId != -1) true else false
    val date = textFieldDate.text

    val address = textFieldAddress.text
    val ownerName = textFieldOwnerName.text
    val ownerPhoneNumbers = textFieldPhoneNumbers.text
    val sindhType = comboBoxSindhType.selectionModel.selectedItem
    val isSwappable = checkBoxIsSwappable.isSelected
    val phoneNumbers = textFieldFacilityPhoneNumbers.text
    val hasPhone = checkBoxHasPhone.isSelected
    val hasWater = checkBoxHasWater.isSelected
    val hasElectricity = checkBoxHasElectricity.isSelected
    val hasGas = checkBoxHasGas.isSelected
    val latitude = 0.0 //FIXME
    val longitude = 0.0 //FIXME
    val pictures = ""
    val description = textAreaDescription.text

    val buildingLevelCount = if(!textFieldBuildingLevelCount.text.isNullOrEmpty())
      textFieldBuildingLevelCount.text.toInt() else 0
    val buildingLevelNumber = if(!textFieldBuildingLevelNumber.text.isNullOrEmpty())
      textFieldBuildingLevelNumber.text.toInt() else 0
    val bedroomCount = if(!textFieldBedroomCount.text.isNullOrEmpty())
      textFieldBedroomCount.text.toInt() else 0
    val hasSeparateWay = checkBoxHasSeparateWay.isSelected
    val hasFurniture = checkBoxHasFurniture.isSelected
    val hasStorageRoom = checkBoxHasStorageRoom.isSelected
    val hasParking = checkBoxHasParking.isSelected
    val hasVideoDoorPhone = checkBoxHasVideoDoorPhone.isSelected
    val hasAirConditioner = checkBoxHasAirConditioner.isSelected
    val hasPackagePlumbing = checkBoxHasPackagePlumbing.isSelected
    val hasCabinet = checkBoxHasCabinet.isSelected
    val hasWallDresser = checkBoxHasWallDresser.isSelected
    val hasLoan = checkBoxHasLoan.isSelected
    val loanAmount = if(!textFieldLoanAmount.text.isNullOrEmpty())
      textFieldLoanAmount.text.toDouble() else 0.0

    val houseType = comboBoxHouseType.selectionModel.selectedItem
    val terrainType = comboBoxTerrainType.selectionModel.selectedItem

    var area = 0.0
    var price = 0.0

    try{
      area = if(!textFieldArea.text.isNullOrEmpty())
        textFieldArea.text.toDouble() else 0.0
    }catch(ex: NumberFormatException){
      throw AppBaseException(PROPS.getProperty("alertMsgInvalidAreaValue"))
    }

    try{
      price = if(!textFieldPrice.text.isNullOrEmpty())
        textFieldPrice.text.toDouble() else 0.0
    }catch(ex: NumberFormatException){
      throw AppBaseException(PROPS.getProperty("alertMsgInvalidPriceValue"))
    }

    if(ownerName.isNullOrEmpty()) {
      throw AppBaseException(PROPS.getProperty("alertMsgOwnerNotSet"))
    }
    if(address.isNullOrEmpty()) {
      throw AppBaseException(PROPS.getProperty("alertMsgAddressNotSet"))
    }

    val obj = HouseInfo(
      0, hasReferrer, referrerId, date.toString(),
      address, area, ownerName, ownerPhoneNumbers, sindhType, price, isSwappable, phoneNumbers, hasPhone, hasWater,
      hasElectricity, hasGas, latitude, longitude, pictures, description,
      buildingLevelCount, buildingLevelNumber, bedroomCount, hasSeparateWay, hasFurniture, hasStorageRoom, hasParking,
      hasVideoDoorPhone, hasAirConditioner, hasPackagePlumbing, hasCabinet, hasWallDresser, hasLoan, loanAmount,
      houseType, terrainType)
    return obj
  }

  fun populateData(obj: HouseInfo): Unit {
    val decimalFormat = DecimalFormat("#.##")
    textFieldDate.text = obj.date
    textFieldReferrer.text = obj.referrerId.toString()
    textFieldPrice.text = decimalFormat.format(obj.price)
    textFieldArea.text = decimalFormat.format(obj.area)
    textFieldAddress.text = obj.address
    textFieldOwnerName.text = obj.ownerName
    textFieldPhoneNumbers.text = obj.ownerPhoneNumbers
    textFieldFacilityPhoneNumbers.text = obj.phoneNumbers

    checkBoxIsSwappable.isSelected = obj.isSwappable
    checkBoxHasWater.isSelected = obj.hasWater
    checkBoxHasElectricity.isSelected = obj.hasElectricity
    checkBoxHasGas.isSelected = obj.hasGas
    checkBoxHasPhone.isSelected = obj.hasPhone

    comboBoxSindhType.selectionModel.select(obj.sindhType)
    comboBoxTerrainType.selectionModel.select(obj.terrainType)
    comboBoxHouseType.selectionModel.select(obj.houseType)

    textAreaDescription.text = obj.description

    textFieldBuildingLevelCount.text = decimalFormat.format(obj.buildingLevelsCount)
    textFieldBuildingLevelNumber.text = decimalFormat.format(obj.levelNumber)
    textFieldBedroomCount.text = decimalFormat.format(obj.bedRoomsCount)
    checkBoxHasSeparateWay.isSelected = obj.hasSeparateWay
    checkBoxHasFurniture.isSelected = obj.hasFurniture
    checkBoxHasStorageRoom.isSelected = obj.hasStorageRoom
    checkBoxHasParking.isSelected = obj.hasParking
    checkBoxHasAirConditioner.isSelected = obj.hasAirConditioner
    checkBoxHasVideoDoorPhone.isSelected = obj.hasVideoDoorPhone
    checkBoxHasPackagePlumbing.isSelected = obj.hasPackagePlumbing
    checkBoxHasCabinet.isSelected = obj.hasCabinet
    checkBoxHasWallDresser.isSelected = obj.hasWallDresser
    checkBoxHasLoan.isSelected = obj.hasLoan
    textFieldLoanAmount.text = decimalFormat.format(obj.loanAmount)

    getPicturesPath(obj)
    editingObjId = obj.id
  }

  @FXML fun onButtonPrintPreviewAction(): Unit{
    try{
      val orientation = if(Config.layoutDirection == "LFT")
        NodeOrientation.LEFT_TO_RIGHT else NodeOrientation.RIGHT_TO_LEFT

      val houseInfo = getHouseInfo()
      HouseFormPrinter.print(FxApp.appObj, orientation, houseInfo)
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
            val houseInfo = getHouseInfo()
            val id = TableHouse.insert(houseInfo)
            if(id != -1){
              if(movePictures(id)){
                val pictures = getMovedFileName()
                if(TableHouse.updatePictures(pictures, id)){
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
            val houseInfo = getHouseInfo()
            movePictures(editingObjId)
            houseInfo.pictures = getMovedFileName()
            if(updateActionHandler.handle(houseInfo)) {
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
      val imageDir = File(APP_HOUSE_IMG_DIR_PATH + File.separator + id.toString())
      if(tempPictures.size > 0)
        if(!imageDir.exists())
          if(!imageDir.mkdir())
            break
      for(pic in tempPictures){
        val uuid = UUID.randomUUID()
        val targetFile = File(APP_HOUSE_IMG_DIR_PATH +
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

  fun getPicturesPath(obj: HouseInfo): Unit{
    val listNames = obj.pictures.split('#')
    if(listNames.size <= 0)
      return

    val imageDirPath = APP_HOUSE_IMG_DIR_PATH + File.separator + obj.id.toString()
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
        val obj = OBJ as HouseInfo
        textFieldReferrer.text = obj.id.toString()
      }
    }
  }
}