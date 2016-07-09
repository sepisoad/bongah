package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableShop
import com.sepisoad.bongah.model.*
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

class ShopFindController {
  @FXML private lateinit var textFieldPriceFrom: TextField
  @FXML private lateinit var textFieldAreaFrom: TextField
  @FXML private lateinit var labelOwnershipType: Label
  @FXML private lateinit var labelRoofType: Label
  @FXML private lateinit var checkBoxIsSwappable: CheckBox
  @FXML private lateinit var labelAddress: Label
  @FXML private lateinit var labelAreaTo: Label
  @FXML private lateinit var textFieldPriceTo: TextField
  @FXML private lateinit var comboBoxRoofType: ComboBox<RoofType>
  @FXML private lateinit var comboBoxSindh: ComboBox<SindhType>
  @FXML private lateinit var comboBoxOwnershipType: ComboBox<OwnershipType>
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

  var shops = FXCollections.observableArrayList<ShopInfo>()
  var table: TableView<ShopInfo> = TableView(shops)
  var colId: TableColumn<ShopInfo, Int> = TableColumn()
  var colName: TableColumn<ShopInfo, String> = TableColumn()
  var colArea: TableColumn<ShopInfo, Double> = TableColumn()
  var colPrice: TableColumn<ShopInfo, Double> = TableColumn()
  var colAddress: TableColumn<ShopInfo, String> = TableColumn()
  var colPhone: TableColumn<ShopInfo, String> = TableColumn()
  var colOwnershipType: TableColumn<ShopInfo, OwnershipType> = TableColumn()
  var colRoofType: TableColumn<ShopInfo, RoofType> = TableColumn()
  var colSindhType: TableColumn<ShopInfo, SindhType> = TableColumn()

  @FXML private fun initialize(): Unit {
    labelAddress.text = PROPS.getProperty("address")
    labelOwner.text = PROPS.getProperty("owner")
    labelPriceFrom.text = PROPS.getProperty("priceFrom")
    labelPriceTo.text = PROPS.getProperty("to")
    labelAreaFrom.text = PROPS.getProperty("areaFrom")
    labelAreaTo.text = PROPS.getProperty("to")
    labelSindhType.text = PROPS.getProperty("sindhType")
    labelOwnershipType.text = PROPS.getProperty("ownershipType")
    labelRoofType.text = PROPS.getProperty("roofType")
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
    comboBoxOwnershipType.items.add(OwnershipType.UNDEFINED)
    comboBoxOwnershipType.items.add(OwnershipType.ARENA)
    comboBoxOwnershipType.items.add(OwnershipType.COMPLETE)
    comboBoxOwnershipType.selectionModel.select(0)
    comboBoxRoofType.items.add(RoofType.UNDEFINED)
    comboBoxRoofType.items.add(RoofType.FREE)
    comboBoxRoofType.items.add(RoofType.UNDER_BUILDING)
    comboBoxRoofType.items.add(RoofType.INSIDE_BUILDING)
    comboBoxRoofType.selectionModel.select(0)

    table.column(PROPS.getProperty("id"), ShopInfo::id)
    table.column(PROPS.getProperty("owner"), ShopInfo::ownerName)
    table.column(PROPS.getProperty("area"), ShopInfo::area)
    table.column(PROPS.getProperty("price"), ShopInfo::price)
    table.column(PROPS.getProperty("ownershipType"), ShopInfo::ownershipType)
    table.column(PROPS.getProperty("roofType"), ShopInfo::roofType)
    table.column(PROPS.getProperty("sindhType"), ShopInfo::sindhType)
    table.column(PROPS.getProperty("address"), ShopInfo::address)
    table.column(PROPS.getProperty("phoneNumbers"), ShopInfo::ownerPhoneNumbers)

    table.columns.addAll(
      colId, colName, colArea, colPrice, colRoofType, colOwnershipType, colSindhType, colAddress, colPhone)
    scrollPaneResult.content = table
  }

  @FXML fun onButtonFindAction(/*event: ActionEvent*/){
    shops.clear()

    val priceFrom = textFieldPriceFrom.text
    val priceTo = textFieldPriceTo.text
    val areaFrom = textFieldAreaFrom.text
    val areaTo = textFieldAreaTo.text
    val owner = textFieldOwner.text
    val address = textFieldAddress.text
    val sindhType = comboBoxSindh.selectedItem ?: SindhType.UNDEFINED
    val ownershipType = comboBoxOwnershipType.selectedItem ?: OwnershipType.UNDEFINED
    val roofType = comboBoxRoofType.selectedItem ?: RoofType.UNDEFINED
    val isSwappable = checkBoxIsSwappable.isSelected

    val shopInfo = ShopInfo(
      0, false, 0, "",
      address, 0.0, owner, "", sindhType, 0.0, isSwappable, "", false, false, false, false, 0.0, 0.0, "", "",
      ownershipType, roofType)

    val results = TableShop.find(shopInfo, priceFrom, priceTo, areaFrom, areaTo)

    for(item in results){
      shops.add(item)
    }

    val colList = table.columns.toList()
    table.resizeColumnsToFitContent(colList)
  }

  @FXML fun onButtonUpdateAction(){
    if(!table.selectionModel.isEmpty){
      val loader = FXMLLoader()

      val cl = FxApp::class.java.classLoader
      loader.location = cl.getResource("res/ui/shop_new.fxml")

      val root = loader.load<VBox>()
      var shopNewCtrl: ShopNewController
      shopNewCtrl = loader.getController()
      shopNewCtrl.viewMode = ViewMode.DEPENDENT
      shopNewCtrl.updateActionHandler = UpdateShopInfo()
      shopNewCtrl.populateData(table.selectionModel.selectedItem)

      val stage = Stage()
      val scene = Scene(root)
      stage.scene = scene

      if(Config.layoutDirection.equals("RTL"))
        root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
      if(Config.layoutDirection.equals("LTR"))
        root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

      shopNewCtrl.parent = stage

      class OnAfterSaveActionHandler : OnAfterSaveAction {
        override fun handle() {
          stage.hide()
        }
      }

      shopNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()

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
          if(TableShop.delete(id)){
            shops.remove(table.selectedItem)
          }
          else{
            //TODO:
          }
        }
      }
    }
  }

  inner class UpdateShopInfo: UpdateActionHandler {
    override fun handle(obj: Any): Boolean {
      val tObj = obj as ShopInfo
      if(table.selectionModel.isEmpty)
        return false
      val id = table.selectedItem?.id!!
      if(TableShop.update(tObj, id)){
        shops.remove(table.selectedItem)
        tObj.id = id
        shops.add((tObj))
        return true
      }
      else return false
    }
  }
}