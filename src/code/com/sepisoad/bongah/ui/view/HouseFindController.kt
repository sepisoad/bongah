package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableHouse
import com.sepisoad.bongah.db.TableLand
import com.sepisoad.bongah.model.*
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.helper.IntegerNumericChangeListener
import com.sepisoad.bongah.ui.helper.OnAfterSaveAction
import com.sepisoad.bongah.ui.helper.SqlChangeListener
import com.sepisoad.bongah.ui.helper.UpdateActionHandler
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.geometry.NodeOrientation
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.util.Callback
import tornadofx.column
import tornadofx.resizeColumnsToFitContent
import tornadofx.selectedItem
import java.util.*

class HouseFindController{
  @FXML private lateinit var textFieldPriceFrom: TextField
  @FXML private lateinit var textFieldAreaFrom: TextField
  @FXML private lateinit var checkBoxIsSwappable: CheckBox
  @FXML private lateinit var checkBoxHasLoan: CheckBox
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var labelAreaTo: Label
  @FXML private lateinit var textFieldPriceTo: TextField

  @FXML private lateinit var labelHouseType: Label
  @FXML private lateinit var labelTerrainType: Label
  @FXML private lateinit var labelSindhType: Label
  @FXML private lateinit var comboBoxHouseType: ComboBox<HouseType>
  @FXML private lateinit var comboBoxTerrainType: ComboBox<TerrainType>
  @FXML private lateinit var comboBoxSindhType: ComboBox<SindhType>

  @FXML private lateinit var textFieldOwner: TextField
  @FXML private lateinit var labelOwner: Label
  @FXML private lateinit var labelAreaFrom: Label
  @FXML private lateinit var labelPriceTo: Label
  @FXML private lateinit var textFieldAreaTo: TextField
  @FXML private lateinit var textFieldAddress: TextField
  @FXML private lateinit var labelPriceFrom: Label
  @FXML private lateinit var scrollPaneResult: ScrollPane
  @FXML private lateinit var buttonFind: Button
  @FXML private lateinit var buttonUpdate: Button
  @FXML private lateinit var buttonDelete: Button

  var houses = FXCollections.observableArrayList<HouseInfo>()
  var table: TableView<HouseInfo> = TableView(houses)
  var colId: TableColumn<HouseInfo, Int> = TableColumn()
  var colName: TableColumn<HouseInfo, String> = TableColumn()
  var colArea: TableColumn<HouseInfo, Double> = TableColumn()
  var colPrice: TableColumn<HouseInfo, Double> = TableColumn()
  var colAddress: TableColumn<HouseInfo, String> = TableColumn()
  var colPhone: TableColumn<HouseInfo, String> = TableColumn()
  var colApplication: TableColumn<HouseInfo, ApplicationType> = TableColumn()
  var colTerrainType: TableColumn<HouseInfo, TerrainType> = TableColumn()
  var colSindhType: TableColumn<HouseInfo, SindhType> = TableColumn()

  @FXML private fun initialize(): Unit {
    labelAddress.text = PROPS.getProperty("address")
    labelOwner.text = PROPS.getProperty("owner")
    labelPriceFrom.text = PROPS.getProperty("priceFrom")
    labelPriceTo.text = PROPS.getProperty("to")
    labelAreaFrom.text = PROPS.getProperty("areaFrom")
    labelAreaTo.text = PROPS.getProperty("to")
    labelHouseType.text = PROPS.getProperty("houseType")
    labelSindhType.text = PROPS.getProperty("sindhType")
    labelTerrainType.text = PROPS.getProperty("terrainType")
    checkBoxIsSwappable.text = PROPS.getProperty("isSwappable")
    checkBoxHasLoan.text = PROPS.getProperty("hasLoan")
    buttonFind.text = PROPS.getProperty("find")
    buttonUpdate.text = PROPS.getProperty("update")
    buttonDelete.text = PROPS.getProperty("delete")

    textFieldPriceFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldPriceFrom))
    textFieldAreaFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldAreaFrom))
    textFieldPriceTo.textProperty().addListener(IntegerNumericChangeListener(textFieldPriceTo))
    textFieldAreaTo.textProperty().addListener(IntegerNumericChangeListener(textFieldAreaTo))

    textFieldOwner.textProperty().addListener(SqlChangeListener(textFieldOwner))
    textFieldAddress.textProperty().addListener(SqlChangeListener(textFieldAddress))

    comboBoxHouseType.items.add(HouseType.UNDEFINED)
    comboBoxHouseType.items.add(HouseType.URBAN_APARTMENT)
    comboBoxHouseType.items.add(HouseType.URBAN_VILLA)
    comboBoxHouseType.items.add(HouseType.RURAL_VILLA)
    comboBoxHouseType.selectionModel.select(0)
    comboBoxTerrainType.items.add(TerrainType.UNDEFINED)
    comboBoxTerrainType.items.add(TerrainType.COASTAL)
    comboBoxTerrainType.items.add(TerrainType.FLAT_LAND)
    comboBoxTerrainType.items.add(TerrainType.MOUNTAIN_SIDE)
    comboBoxTerrainType.selectionModel.select(0)
    comboBoxSindhType.items.add(SindhType.UNDEFINED)
    comboBoxSindhType.items.add(SindhType.ENDOWMENT)
    comboBoxSindhType.items.add(SindhType.URBAN)
    comboBoxSindhType.selectionModel.select(0)

    table.column(PROPS.getProperty("id"), HouseInfo::id)
    table.column(PROPS.getProperty("owner"), HouseInfo::ownerName)
    table.column(PROPS.getProperty("area"), HouseInfo::area)
    table.column(PROPS.getProperty("price"), HouseInfo::price)
    table.column(PROPS.getProperty("houseType"), HouseInfo::houseType)
    table.column(PROPS.getProperty("terrainType"), HouseInfo::terrainType)
    table.column(PROPS.getProperty("sindhType"), HouseInfo::sindhType)
    table.column(PROPS.getProperty("address"), HouseInfo::address)
    table.column(PROPS.getProperty("phoneNumbers"), HouseInfo::ownerPhoneNumbers)

    table.columns.addAll(
      colId, colName, colArea, colPrice, colApplication, colTerrainType, colSindhType, colAddress, colPhone)
    scrollPaneResult.content = table

  }

  @FXML fun onButtonFindAction(){
    houses.clear()

    val priceFrom = textFieldPriceFrom.text
    val priceTo = textFieldPriceTo.text
    val areaFrom = textFieldAreaFrom.text
    val areaTo = textFieldAreaTo.text
    val owner = textFieldOwner.text
    val address = textFieldAddress.text
    val houseType = comboBoxHouseType.selectedItem ?: HouseType.UNDEFINED
    val terrainType = comboBoxTerrainType.selectedItem ?: TerrainType.UNDEFINED
    val sindhType = comboBoxSindhType.selectedItem ?: SindhType.UNDEFINED
    val isSwappable = checkBoxIsSwappable.isSelected
    val hasLoan = checkBoxHasLoan.isSelected

    val houseInfo = HouseInfo(
      0, false, 0, "",
      address, 0.0, owner, "", sindhType, 0.0, isSwappable, "", false, false, false, false, 0.0, 0.0, "", "",
      0, 0, 0, false, false, false, false, false,false,false,false, false, hasLoan, 0.0, houseType, terrainType
    )

    val results = TableHouse.find(houseInfo, priceFrom, priceTo, areaFrom, areaTo)

    for(item in results){
      houses.add(item)
    }

    val colList = table.columns.toList()
    table.resizeColumnsToFitContent(colList)
  }

  @FXML fun onButtonUpdateAction(){
    if(!table.selectionModel.isEmpty){
      val loader = FXMLLoader()

      val cl = FxApp::class.java.classLoader
      loader.location = cl.getResource("res/ui/house_new.fxml")

      val root = loader.load<VBox>()
      var houseNewCtrl: HouseNewController
      houseNewCtrl = loader.getController()
      houseNewCtrl.viewMode = ViewMode.DEPENDENT
      houseNewCtrl.updateActionHandler = UpdateHouseInfo()
      houseNewCtrl.populateData(table.selectionModel.selectedItem)

      val stage = Stage()
      val scene = Scene(root)
      stage.scene = scene

      if(Config.layoutDirection.equals("RTL"))
        root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
      if(Config.layoutDirection.equals("LTR"))
        root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

      houseNewCtrl.parent = stage

      class OnAfterSaveActionHandler : OnAfterSaveAction {
        override fun handle() {
          stage.hide()
        }
      }

      houseNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()

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
          if(TableHouse.delete(id)){
            houses.remove(table.selectedItem)
          }
          else{
            //TODO:
          }
        }
      }
    }
  }

  inner class UpdateHouseInfo: UpdateActionHandler {
    override fun handle(obj: Any): Boolean {
      val tObj = obj as HouseInfo
      if(table.selectionModel.isEmpty)
        return false
      val id = table.selectedItem?.id!!
      if(TableHouse.update(tObj, id)){
        houses.remove(table.selectedItem)
        tObj.id = id
        houses.add((tObj))
        return true
      }
      else return false
    }
  }
}
