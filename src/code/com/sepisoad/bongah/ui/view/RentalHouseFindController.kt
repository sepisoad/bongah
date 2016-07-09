package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableRentalHouse
import com.sepisoad.bongah.model.HouseType
import com.sepisoad.bongah.model.RentalHouseInfo
import com.sepisoad.bongah.model.RentalType
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.helper.IntegerNumericChangeListener
import com.sepisoad.bongah.ui.helper.OnAfterSaveAction
import com.sepisoad.bongah.ui.helper.SqlChangeListener
import com.sepisoad.bongah.ui.helper.UpdateActionHandler
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.NodeOrientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import tornadofx.column
import tornadofx.resizeColumnsToFitContent
import tornadofx.selectedItem

class RentalHouseFindController {
  @FXML private lateinit var textFieldOwner: TextField
  @FXML private lateinit var textFieldAddress: TextField
  @FXML private lateinit var textFieldAdvanceFrom: TextField
  @FXML private lateinit var textFieldRentalFrom: TextField
  @FXML private lateinit var textFieldMortageFrom: TextField
  @FXML private lateinit var textFieldAreaFrom: TextField
  @FXML private lateinit var textFieldAdvanceTo: TextField
  @FXML private lateinit var textFieldRentalTo: TextField
  @FXML private lateinit var textFieldMortageTo: TextField
  @FXML private lateinit var textFieldAreaTo: TextField

  @FXML private lateinit var labelOwner: Label
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var labelAdvanceFrom: Label
  @FXML private lateinit var labelRentalFrom: Label
  @FXML private lateinit var labelMortageFrom: Label
  @FXML private lateinit var labelAreaFrom: Label
  @FXML private lateinit var labelAdvanceTo: Label
  @FXML private lateinit var labelRentalTo: Label
  @FXML private lateinit var labelMortageTo: Label
  @FXML private lateinit var labelAreaTo: Label

  @FXML private lateinit var labelHouseType: Label
  @FXML private lateinit var labelRentalType: Label
  @FXML private lateinit var comboBoxHouseType: ComboBox<HouseType>
  @FXML private lateinit var comboBoxRentalType: ComboBox<RentalType>

  @FXML private lateinit var scrollPaneResult: ScrollPane
  @FXML private lateinit var buttonFind: Button
  @FXML private lateinit var buttonUpdate: Button
  @FXML private lateinit var buttonDelete: Button

  var rentalHouses = FXCollections.observableArrayList<RentalHouseInfo>()
  var table: TableView<RentalHouseInfo> = TableView(rentalHouses)
  var colId: TableColumn<RentalHouseInfo, Int> = TableColumn()
  var colName: TableColumn<RentalHouseInfo, String> = TableColumn()
  var colArea: TableColumn<RentalHouseInfo, Double> = TableColumn()
  var colAdvance: TableColumn<RentalHouseInfo, Double> = TableColumn()
  var colRental: TableColumn<RentalHouseInfo, Double> = TableColumn()
  var colMortage: TableColumn<RentalHouseInfo, Double> = TableColumn()
  var colAddress: TableColumn<RentalHouseInfo, String> = TableColumn()
  var colPhone: TableColumn<RentalHouseInfo, String> = TableColumn()
  var colRentalType: TableColumn<RentalHouseInfo, RentalType> = TableColumn()
  var colHouseType: TableColumn<RentalHouseInfo, HouseType> = TableColumn()

  @FXML private fun initialize(): Unit {
    labelAddress.text = PROPS.getProperty("address")
    labelOwner.text = PROPS.getProperty("owner")

    labelAdvanceFrom.text = PROPS.getProperty("advanceFrom")
    labelAdvanceTo.text = PROPS.getProperty("to")
    labelRentalFrom.text = PROPS.getProperty("rentalFrom")
    labelRentalTo.text = PROPS.getProperty("to")
    labelMortageFrom.text = PROPS.getProperty("mortageFrom")
    labelMortageTo.text = PROPS.getProperty("to")

    labelAreaFrom.text = PROPS.getProperty("areaFrom")
    labelAreaTo.text = PROPS.getProperty("to")
    labelHouseType.text = PROPS.getProperty("houseType")
    labelRentalType.text = PROPS.getProperty("sindhType")

    buttonFind.text = PROPS.getProperty("find")
    buttonUpdate.text = PROPS.getProperty("update")
    buttonDelete.text = PROPS.getProperty("delete")

    textFieldAdvanceFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldAdvanceFrom))
    textFieldRentalFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldRentalFrom))
    textFieldMortageFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldMortageFrom))
    textFieldAreaFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldAreaFrom))
    textFieldAdvanceTo.textProperty().addListener(IntegerNumericChangeListener(textFieldAdvanceTo))
    textFieldRentalTo.textProperty().addListener(IntegerNumericChangeListener(textFieldRentalTo))
    textFieldMortageTo.textProperty().addListener(IntegerNumericChangeListener(textFieldMortageTo))
    textFieldAreaTo.textProperty().addListener(IntegerNumericChangeListener(textFieldAreaTo))

    textFieldOwner.textProperty().addListener(SqlChangeListener(textFieldOwner))
    textFieldAddress.textProperty().addListener(SqlChangeListener(textFieldAddress))

    comboBoxHouseType.items.add(HouseType.UNDEFINED)
    comboBoxHouseType.items.add(HouseType.URBAN_APARTMENT)
    comboBoxHouseType.items.add(HouseType.URBAN_VILLA)
    comboBoxHouseType.items.add(HouseType.RURAL_VILLA)
    comboBoxHouseType.selectionModel.select(0)
    comboBoxRentalType.items.add(RentalType.UNDEFINED)
    comboBoxRentalType.items.add(RentalType.RENTAL)
    comboBoxRentalType.items.add(RentalType.MORTAGE)
    comboBoxRentalType.items.add(RentalType.BOTH)
    comboBoxRentalType.selectionModel.select(0)

    table.column(PROPS.getProperty("id"), RentalHouseInfo::id)
    table.column(PROPS.getProperty("owner"), RentalHouseInfo::ownerName)
    table.column(PROPS.getProperty("area"), RentalHouseInfo::area)
    table.column(PROPS.getProperty("advance"), RentalHouseInfo::advanceAmount)
    table.column(PROPS.getProperty("rental"), RentalHouseInfo::rentalAmount)
    table.column(PROPS.getProperty("mortage"), RentalHouseInfo::mortageAmount)
    table.column(PROPS.getProperty("houseType"), RentalHouseInfo::houseType)
    table.column(PROPS.getProperty("rentalType"), RentalHouseInfo::rentalType)
    table.column(PROPS.getProperty("address"), RentalHouseInfo::address)
    table.column(PROPS.getProperty("phoneNumbers"), RentalHouseInfo::ownerPhoneNumbers)

    table.columns.addAll(
      colId, colName, colArea, colRentalType, colHouseType, colAdvance, colRental, colMortage, colAddress, colPhone)
    scrollPaneResult.content = table
  }

  @FXML fun onButtonFindAction(){
    rentalHouses.clear()

    val advanceFrom = textFieldAdvanceFrom.text
    val advanceTo = textFieldAdvanceTo.text
    val rentalFrom = textFieldRentalFrom.text
    val rentalTo = textFieldRentalTo.text
    val mortageFrom = textFieldMortageFrom.text
    val mortageTo = textFieldMortageTo.text
    val areaFrom = textFieldAreaFrom.text
    val areaTo = textFieldAreaTo.text
    val owner = textFieldOwner.text
    val address = textFieldAddress.text
    val houseType = comboBoxHouseType.selectedItem ?: HouseType.UNDEFINED
    val rentalType = comboBoxRentalType.selectedItem ?: RentalType.UNDEFINED

    val rentalHouseInfo = RentalHouseInfo(
      0, false, 0, "", address, 0.0, owner, "", rentalType, 0.0, 0.0, 0.0,
      0.0, 0.0, "", 0, 0, 0, false, false, false, false, false, houseType, ""
    )

    val results = TableRentalHouse.find(
      rentalHouseInfo, advanceFrom, advanceTo, rentalFrom, rentalTo, mortageFrom, mortageTo, areaFrom, areaTo
    )

    for(item in results){
      rentalHouses.add(item)
    }

    val colList = table.columns.toList()
    table.resizeColumnsToFitContent(colList)
  }

  @FXML fun onButtonUpdateAction(){
    if(!table.selectionModel.isEmpty){
      val loader = FXMLLoader()

      val cl = FxApp::class.java.classLoader
      loader.location = cl.getResource("res/ui/rental_house_new.fxml")

      val root = loader.load<VBox>()
      var rentalHouseNewCtrl: RentalHouseNewController
      rentalHouseNewCtrl = loader.getController()
      rentalHouseNewCtrl.viewMode = ViewMode.DEPENDENT
      rentalHouseNewCtrl.updateActionHandler = UpdateRentalHouseInfo()
      rentalHouseNewCtrl.populateData(table.selectionModel.selectedItem)

      val stage = Stage()
      val scene = Scene(root)
      stage.scene = scene

      if(Config.layoutDirection.equals("RTL"))
        root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
      if(Config.layoutDirection.equals("LTR"))
        root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

      rentalHouseNewCtrl.parent = stage

      class OnAfterSaveActionHandler : OnAfterSaveAction {
        override fun handle() {
          stage.hide()
        }
      }

      rentalHouseNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()

      scene.getStylesheets().add(cl.getResource("res/css/base.css").toExternalForm());

      root.setPrefSize(1000.0, 700.0)
      stage.initModality(Modality.APPLICATION_MODAL)
      stage.showAndWait()
    }
  }

  @FXML fun onButtonDeleteAction(){
    if(!table.selectionModel.isEmpty){
      val id = table.selectedItem?.id
      if(null != id){
        val msg = PROPS.getProperty("alertMsgDeleteConfirmation")
        val alert = Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.YES, ButtonType.NO)
        alert.showAndWait()

        if(alert.result == ButtonType.YES){
          if(TableRentalHouse.delete(id)){
            rentalHouses.remove(table.selectedItem)
          }
          else{
            //TODO:
          }
        }
      }
    }
  }

  inner class UpdateRentalHouseInfo: UpdateActionHandler {
    override fun handle(obj: Any): Boolean {
      val tObj = obj as RentalHouseInfo
      if(table.selectionModel.isEmpty)
        return false
      val id = table.selectedItem?.id!!
      if(TableRentalHouse.update(tObj, id)){
        rentalHouses.remove(table.selectedItem)
        tObj.id = id
        rentalHouses.add((tObj))
        return true
      }
      else return false
    }
  }
}
