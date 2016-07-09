package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableCustomer
import com.sepisoad.bongah.model.CustomerInfo
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.ViewMode
import com.sepisoad.bongah.ui.helper.FindReferrerIdActionHandler
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

class CustomerFindController {
  lateinit var findReferrerIdActionHandler : FindReferrerIdActionHandler
  @FXML private lateinit var textFieldName: TextField
  @FXML private lateinit var labelPhoneNumbers: Label
  @FXML private lateinit var buttonFind: Button
  @FXML private lateinit var buttonUpdate: Button
  @FXML private lateinit var buttonSelect: Button
  @FXML private lateinit var scrollPaneResult: ScrollPane
  @FXML private lateinit var labelName: Label
  @FXML private lateinit var textFieldPhoneNumbers: TextField
  var customers = FXCollections.observableArrayList<CustomerInfo>()
  var table: TableView<CustomerInfo> = TableView(customers)
  var colId: TableColumn<CustomerInfo, Int> = TableColumn()
  var colName: TableColumn<CustomerInfo, String> =  TableColumn()
  var colPhoneNumbers: TableColumn<CustomerInfo, String> = TableColumn()
  var colAddress: TableColumn<CustomerInfo, String> = TableColumn()

  var viewMode = ViewMode.STAND_ALONE
    set (value){
      when(value){
        ViewMode.STAND_ALONE -> {
          buttonUpdate.isVisible = true
          buttonSelect.isVisible = false
        }

        ViewMode.DEPENDENT -> {
          buttonUpdate.isVisible = false
          buttonSelect.isVisible = false
        }
      }
    }

  @FXML fun initialize(): Unit{
    textFieldName.textProperty().addListener(SqlChangeListener(textFieldName))
    textFieldPhoneNumbers.textProperty().addListener(SqlChangeListener(textFieldPhoneNumbers))

    labelPhoneNumbers.text = PROPS.getProperty("phoneNumbers")
    labelName.text = PROPS.getProperty("name")
    buttonFind.text = PROPS.getProperty("find")
    buttonUpdate.text = PROPS.getProperty("update")
    buttonSelect.text = PROPS.getProperty("select")

    table.column(PROPS.getProperty("id"), CustomerInfo::id)
    table.column(PROPS.getProperty("name"), CustomerInfo::name)
    table.column(PROPS.getProperty("phoneNumbers"), CustomerInfo::phoneNumbers)
    table.column(PROPS.getProperty("address"), CustomerInfo::address)

    table.columns.addAll(colId, colName, colPhoneNumbers, colAddress)
    scrollPaneResult.content = table
  }

  @FXML fun onButtonFindAction(): Unit {
    customers.clear()

    val name = textFieldName.text
    val phoneNumbers = textFieldPhoneNumbers.text

    val CustomerInfoList = TableCustomer.find(name, phoneNumbers)
    for(item: CustomerInfo in CustomerInfoList){
      customers.add(item)
    }

    val colList = table.columns.toList()
    table.resizeColumnsToFitContent(colList)
  }

  @FXML fun onButtonUpdateAction(): Unit {
    if(!table.selectionModel.isEmpty){
      val loader = FXMLLoader()

      val cl = FxApp::class.java.classLoader
      loader.location = cl.getResource("res/ui/customer_new.fxml")
      val root = loader.load<VBox>()
      var customerNewCtrl: CustomerNewController
      customerNewCtrl = loader.getController()
      customerNewCtrl.viewMode = ViewMode.DEPENDENT
      customerNewCtrl.updateActionHandler = UpdateCustomerInfo()
      customerNewCtrl.populateData(table.selectionModel.selectedItem)

      val stage = Stage()
      val scene = Scene(root)
      stage.scene = scene

      if(Config.layoutDirection.equals("RTL"))
        root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
      if(Config.layoutDirection.equals("LTR")) {
        root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT
      }

      customerNewCtrl.parent = stage

      root.setPrefSize(500.0, 200.0)
      stage.initModality(Modality.APPLICATION_MODAL)
      stage.showAndWait()
    }
  }

  @FXML fun onButtonSelectAction(): Unit {

  }

  fun findReferrerIdAction(): Unit {
    findReferrerIdActionHandler.handle(table.selectionModel.selectedItem)
  }

  inner class UpdateCustomerInfo: UpdateActionHandler {
    override fun handle(obj: Any): Boolean {
      val tObj = obj as CustomerInfo
      if(table.selectionModel.isEmpty)
        return false
      val id = table.selectedItem?.id!!
      if(TableCustomer.update(tObj, id)){
        customers.remove(table.selectedItem)
        tObj.id = id
        customers.add((tObj))
        return true
      }
      else return false
    }
  }
}