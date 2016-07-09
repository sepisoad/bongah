package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableLand
import com.sepisoad.bongah.model.*
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.helper.OnAfterSaveAction
import com.sepisoad.bongah.ui.helper.UpdateActionHandler
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.helper.IntegerNumericChangeListener
import com.sepisoad.bongah.ui.helper.SqlChangeListener
import javafx.collections.FXCollections
import javafx.event.ActionEvent
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

class LandFindController {
  @FXML private lateinit var textFieldPriceFrom: TextField
  @FXML private lateinit var textFieldAreaFrom: TextField
  @FXML private lateinit var labelApplicationType: Label
  @FXML private lateinit var labelTerrainType: Label
  @FXML private lateinit var checkBoxIsSwappable: CheckBox
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var labelAreaTo: Label
  @FXML private lateinit var textFieldPriceTo: TextField
  @FXML private lateinit var comboBoxTerrain: ComboBox<TerrainType>
  @FXML private lateinit var comboBoxSindh: ComboBox<SindhType>
  @FXML private lateinit var comboBoxApplication: ComboBox<ApplicationType>
  @FXML private lateinit var textFieldOwner: TextField
  @FXML private lateinit var labelSindhType: Label
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

  var lands = FXCollections.observableArrayList<LandInfo>()
  var table: TableView<LandInfo> = TableView(lands)
  var colId: TableColumn<LandInfo, Int> = TableColumn()
  var colName: TableColumn<LandInfo, String> =  TableColumn()
  var colArea: TableColumn<LandInfo, Double> =  TableColumn()
  var colPrice: TableColumn<LandInfo, Double> =  TableColumn()
  var colAddress: TableColumn<LandInfo, String> = TableColumn()
  var colPhone: TableColumn<LandInfo, String> =  TableColumn()
  var colApplication: TableColumn<LandInfo, ApplicationType> = TableColumn()
  var colTerrainType: TableColumn<LandInfo, TerrainType> = TableColumn()
  var colSindhType: TableColumn<LandInfo, SindhType> = TableColumn()

  @FXML private fun initialize(): Unit {
    labelAddress.text = PROPS.getProperty("address")
    labelOwner.text = PROPS.getProperty("owner")
    labelPriceFrom.text = PROPS.getProperty("priceFrom")
    labelPriceTo.text = PROPS.getProperty("to")
    labelAreaFrom.text = PROPS.getProperty("areaFrom")
    labelAreaTo.text = PROPS.getProperty("to")
    labelSindhType.text = PROPS.getProperty("sindhType")
    labelApplicationType.text = PROPS.getProperty("applicationType")
    labelTerrainType.text = PROPS.getProperty("terrainType")
    checkBoxIsSwappable.text = PROPS.getProperty("isSwappable")
    buttonFind.text = PROPS.getProperty("find")
    buttonUpdate.text = PROPS.getProperty("update")
    buttonDelete.text = PROPS.getProperty("delete")

    textFieldPriceFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldPriceFrom))
    textFieldAreaFrom.textProperty().addListener(IntegerNumericChangeListener(textFieldAreaFrom))
    textFieldPriceTo.textProperty().addListener(IntegerNumericChangeListener(textFieldPriceTo))
    textFieldAreaTo.textProperty().addListener(IntegerNumericChangeListener(textFieldAreaTo))

    textFieldOwner.textProperty().addListener(SqlChangeListener(textFieldOwner))
    textFieldAddress.textProperty().addListener(SqlChangeListener(textFieldAddress))

    comboBoxSindh.items.add(SindhType.UNDEFINED)
    comboBoxSindh.items.add(SindhType.ENDOWMENT)
    comboBoxSindh.items.add(SindhType.URBAN)
    comboBoxSindh.selectionModel.select(0)
    comboBoxTerrain.items.add(TerrainType.UNDEFINED)
    comboBoxTerrain.items.add(TerrainType.COASTAL)
    comboBoxTerrain.items.add(TerrainType.FLAT_LAND)
    comboBoxTerrain.items.add(TerrainType.MOUNTAIN_SIDE)
    comboBoxTerrain.selectionModel.select(0)
    comboBoxApplication.items.add(ApplicationType.UNDEFINED)
    comboBoxApplication.items.add(ApplicationType.AGRICULTURAL)
    comboBoxApplication.items.add(ApplicationType.COMMERCIAL)
    comboBoxApplication.items.add(ApplicationType.RESIDENTIAL)
    comboBoxApplication.selectionModel.select(0)

    table.column(PROPS.getProperty("id"), LandInfo::id)
    table.column(PROPS.getProperty("owner"), LandInfo::ownerName)
    table.column(PROPS.getProperty("area"), LandInfo::area)
    table.column(PROPS.getProperty("price"), LandInfo::price)
    table.column(PROPS.getProperty("application"), LandInfo::applicationType)
    table.column(PROPS.getProperty("terrainType"), LandInfo::terrainType)
    table.column(PROPS.getProperty("sindhType"), LandInfo::sindhType)
    table.column(PROPS.getProperty("address"), LandInfo::address)
    table.column(PROPS.getProperty("phoneNumbers"), LandInfo::ownerPhoneNumbers)

    table.columns.addAll(
      colId, colName, colArea, colPrice, colApplication, colTerrainType, colSindhType, colAddress, colPhone)
    scrollPaneResult.content = table
  }

  @FXML fun onButtonFindAction(/*event: ActionEvent*/){
    lands.clear()

    val priceFrom = textFieldPriceFrom.text
    val priceTo = textFieldPriceTo.text
    val areaFrom = textFieldAreaFrom.text
    val areaTo = textFieldAreaTo.text
    val owner = textFieldOwner.text
    val address = textFieldAddress.text
    val sindhType = comboBoxSindh.selectedItem ?: SindhType.UNDEFINED
    val applicationType = comboBoxApplication.selectedItem ?: ApplicationType.UNDEFINED
    val terrainType = comboBoxTerrain.selectedItem ?: TerrainType.UNDEFINED
    val isSwappable = checkBoxIsSwappable.isSelected

    val landInfo = LandInfo(
      0, false, 0, "",
      address, 0.0, owner, "", sindhType, 0.0, isSwappable, "", false, false, false, false, 0.0, 0.0, "", "",
      applicationType, terrainType, false, false)

    val results = TableLand.find(landInfo, priceFrom, priceTo, areaFrom, areaTo)

    for(item in results){
      lands.add(item)
    }

    val colList = table.columns.toList()
    table.resizeColumnsToFitContent(colList)
  }

  @FXML fun onButtonUpdateAction(/*event: ActionEvent*/){
    if(!table.selectionModel.isEmpty){
      val loader = FXMLLoader()

      val cl = FxApp::class.java.classLoader
      loader.location = cl.getResource("res/ui/land_new.fxml")

      val root = loader.load<VBox>()
      var landNewCtrl: LandNewController
      landNewCtrl = loader.getController()
      landNewCtrl.viewMode = ViewMode.DEPENDENT
      landNewCtrl.updateActionHandler = UpdateLandInfo()
      landNewCtrl.populateData(table.selectionModel.selectedItem)

      val stage = Stage()
      val scene = Scene(root)
      stage.scene = scene

      if(Config.layoutDirection.equals("RTL"))
        root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
      if(Config.layoutDirection.equals("LTR"))
        root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

      landNewCtrl.parent = stage

      class OnAfterSaveActionHandler : OnAfterSaveAction {
        override fun handle() {
          stage.hide()
        }
      }

      landNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()

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
          if(TableLand.delete(id)){
            lands.remove(table.selectedItem)
          }
          else{
            //TODO:
          }
        }
      }
    }
  }

  inner class UpdateLandInfo: UpdateActionHandler {
    override fun handle(obj: Any): Boolean {
      val tObj = obj as LandInfo
      if(table.selectionModel.isEmpty)
        return false
      val id = table.selectedItem?.id!!
      if(TableLand.update(tObj, id)){
        lands.remove(table.selectedItem)
        tObj.id = id
        lands.add((tObj))
        return true
      }
      else return false
    }
  }
}