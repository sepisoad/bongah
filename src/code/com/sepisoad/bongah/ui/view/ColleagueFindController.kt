package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.Config
import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.db.TableColleague
import com.sepisoad.bongah.model.ColleagueInfo
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.helper.FindReferrerIdActionHandler
import com.sepisoad.bongah.ui.helper.UpdateActionHandler
import com.sepisoad.bongah.ui.ViewMode
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

class ColleagueFindController {
  lateinit var findReferrerIdActionHandler : FindReferrerIdActionHandler
  @FXML private lateinit var textFieldName: TextField
  @FXML private lateinit var labelPhoneNumbers: Label
  @FXML private lateinit var buttonFind: Button
  @FXML private lateinit var buttonUpdate: Button
  @FXML private lateinit var buttonSelect: Button
  @FXML private lateinit var scrollPaneResult: ScrollPane
  @FXML private lateinit var labelName: Label
  @FXML private lateinit var textFieldPhoneNumbers: TextField
  var colleagues = FXCollections.observableArrayList<ColleagueInfo>()
  var table: TableView<ColleagueInfo> = TableView(colleagues)
  var colId: TableColumn<ColleagueInfo, Int> = TableColumn()
  var colName: TableColumn<ColleagueInfo, String> =  TableColumn()
  var colPhoneNumbers: TableColumn<ColleagueInfo, String> = TableColumn()
  var colAddress: TableColumn<ColleagueInfo, String> = TableColumn()

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

    table.column(PROPS.getProperty("id"), ColleagueInfo::id)
    table.column(PROPS.getProperty("name"), ColleagueInfo::name)
    table.column(PROPS.getProperty("phoneNumbers"), ColleagueInfo::phoneNumbers)
    table.column(PROPS.getProperty("address"), ColleagueInfo::address)

    table.columns.addAll(colId, colName, colPhoneNumbers, colAddress)
    scrollPaneResult.content = table

  }

  @FXML fun onButtonFindAction(): Unit {
    colleagues.clear()

    val name = textFieldName.text
    val phoneNumbers = textFieldPhoneNumbers.text

    val colleagueInfoList = TableColleague.find(name, phoneNumbers)
    for(item: ColleagueInfo in colleagueInfoList){
      colleagues.add(item)
    }

    val colList = table.columns.toList()
    table.resizeColumnsToFitContent(colList)
  }

  @FXML fun onButtonUpdateAction(): Unit {
    if(!table.selectionModel.isEmpty){
      val loader = FXMLLoader()

      val cl = FxApp::class.java.classLoader
      loader.location = cl.getResource("res/ui/colleague_new.fxml")
      val root = loader.load<VBox>()
      var colleagueNewCtrl: ColleagueNewController
      colleagueNewCtrl = loader.getController()
      colleagueNewCtrl.viewMode = ViewMode.DEPENDENT
      colleagueNewCtrl.updateActionHandler = UpdateColleagueInfo()
      colleagueNewCtrl.populateData(table.selectionModel.selectedItem)

      val stage = Stage()
      val scene = Scene(root)
      stage.scene = scene

      if(Config.layoutDirection.equals("RTL"))
        root.nodeOrientation = NodeOrientation.RIGHT_TO_LEFT
      if(Config.layoutDirection.equals("LTR"))
        root.nodeOrientation = NodeOrientation.LEFT_TO_RIGHT

      colleagueNewCtrl.parent = stage

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

  inner class UpdateColleagueInfo: UpdateActionHandler {
    override fun handle(obj: Any): Boolean {
      val tObj = obj as ColleagueInfo
      if(table.selectionModel.isEmpty)
        return false
      val id = table.selectedItem?.id!!
      if(TableColleague.update(tObj, id)){
        colleagues.remove(table.selectedItem)
        tObj.id = id
        colleagues.add((tObj))
        return true
      }
      else return false
    }
  }
}