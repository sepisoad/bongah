package com.sepisoad.bongah.ui.form

import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.model.RentalShopInfo
import com.sepisoad.bongah.ui.FxApp
import javafx.application.Application
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.NodeOrientation
import javafx.geometry.Orientation
import javafx.print.PageOrientation
import javafx.print.Paper
import javafx.print.Printer
import javafx.print.PrinterJob
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.scene.transform.Transform
import javafx.stage.Modality
import javafx.stage.Stage
import java.text.DecimalFormat

object RentalShopFormPrinter {
  fun print(appObj: Application, orientation: NodeOrientation, obj: RentalShopInfo) {
    val root = VBox()
    val scroll = ScrollPane()
    val buttonPrint = Button(PROPS.getProperty("print"))
    var vBoxBtn = VBox(buttonPrint)
    val documentRoot = VBox()

    buttonPrint.prefWidth = 50.0
    vBoxBtn.padding = Insets(10.0, 10.0, 0.0, 10.0)
    scroll.content = documentRoot
    root.children.addAll(scroll, vBoxBtn)

    val stage = Stage()
    val scene = Scene(root)

    buttonPrint.onAction = EventHandler {
      val pj = PrinterJob.createPrinterJob()
      val pl = pj.printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM)
      pj.jobSettings.pageLayout = pl

      if (pj.showPrintDialog(stage)) {
        val rotate = Transform.rotate(90.0, documentRoot.width / 2, documentRoot.height / 2)
        val affine = Transform.affine(0.0, 1.0, 1.0, 0.0, 0.0, 0.0)
        documentRoot.transforms.add(rotate)
        documentRoot.transforms.add(affine)
        pj.printPage(documentRoot)
        pj.endJob()
        stage.hide()
      }
    }

    stage.scene = scene
    documentRoot.nodeOrientation = orientation

    renderForm(documentRoot, obj)

    val cl = FxApp::class.java.classLoader
    scene.stylesheets.add(cl.getResource("res/css/print.css").toExternalForm())

    documentRoot.setPrefSize(Paper.A4.width, Paper.A5.height)

    stage.isResizable = false
    stage.initModality(Modality.APPLICATION_MODAL)
    stage.showAndWait()
  }

  fun renderForm(root: VBox, obj: RentalShopInfo){
    val decimalFormat = DecimalFormat("#.##")
    root.spacing = 10.0
    root.padding = Insets(35.0, 10.0, 15.0, 100.0)

    val hBoxDocInfo = HBox()
    hBoxDocInfo.spacing = 10.0
    val textDocTypeTitle = Text(PROPS.getProperty("documentType") + ": ")
    val textDocTypeValue = Text(PROPS.getProperty("rentalShop"))
    val textDocIdTitle = Text(PROPS.getProperty("id") + ": ")
    val textDocIdValue = Text(obj.id.toString())
    val textDocReferrerTitle = Text(PROPS.getProperty("referrerCode") + ": ")
    val textDocReferrerValue = Text(obj.referrerId.toString())
    val textDocDateTitle = Text(PROPS.getProperty("date") + ": ")
    val textDateValue = Text(obj.date)
    hBoxDocInfo.children.addAll(
      textDocTypeTitle, textDocTypeValue,
      Separator(Orientation.VERTICAL),
      textDocIdTitle, textDocIdValue,
      Separator(Orientation.VERTICAL),
      textDocReferrerTitle, textDocReferrerValue,
      Separator(Orientation.VERTICAL),
      textDocDateTitle, textDateValue
    )
    //============================================
    val hBoxAddress = HBox()
    hBoxAddress.spacing = 10.0
    val textAddressTitle = Text(PROPS.getProperty("address") + ": ")
    val textAddressValue = Text(obj.address)

    textAddressValue.wrappingWidth = 300.0

    hBoxAddress.children.addAll(
      textAddressTitle, textAddressValue
    )
    //============================================
    val hBoxInfo1 = HBox()
    hBoxInfo1.spacing = 10.0
    val textOwnerNameTitle = Text(PROPS.getProperty("owner") + ": ")
    val textOwnerNameValue = Text(obj.ownerName)
    val textOwnerPhoneNumbersTitle = Text(PROPS.getProperty("phoneNumbers") + ": ")
    val textOwnerPhoneNumbersValue = Text(obj.ownerPhoneNumbers)

    hBoxInfo1.children.addAll(
      textOwnerNameTitle, textOwnerNameValue,
      Separator(Orientation.VERTICAL),
      textOwnerPhoneNumbersTitle, textOwnerPhoneNumbersValue
    )
    //============================================
    val hBoxInfo2 = HBox()
    hBoxInfo2.spacing = 10.0
    val textAreaTitle = Text(PROPS.getProperty("area") + ": ")
    val textAreaValue = Text(decimalFormat.format(obj.area))
    val textAdvanceAmountTitle = Text(PROPS.getProperty("advanceAmount") + ": ")
    val textAdvanceAmountValue = Text(decimalFormat.format(obj.advanceAmount))
    val textRentalAmountTitle = Text(PROPS.getProperty("rentalAmount") + ": ")
    val textRentalAmountValue = Text(decimalFormat.format(obj.rentalAmount))
    val textMortageAmountTitle = Text(PROPS.getProperty("mortageAmount") + ": ")
    val textMortageAmountValue = Text(decimalFormat.format(obj.mortageAmount))

    hBoxInfo2.children.addAll(
      textAreaTitle, textAreaValue,
      Separator(Orientation.VERTICAL),
      textAdvanceAmountTitle, textAdvanceAmountValue,
      Separator(Orientation.VERTICAL),
      textRentalAmountTitle, textRentalAmountValue,
      Separator(Orientation.VERTICAL),
      textMortageAmountTitle, textMortageAmountValue
    )
    //============================================
    val hBoxInfo3 = HBox()
    hBoxInfo3.spacing = 10.0
    val textRentalTypeTitle = Text(PROPS.getProperty("rentalType") + ": ")
    val textRentalTypeValue = Text(obj.rentalType.toString())
    val textRoofTypeTitle = Text(PROPS.getProperty("roofType") + ": ")
    val textRoofTypeValue = Text(obj.roofType.toString())

    hBoxInfo3.children.addAll(
      textRentalTypeTitle, textRentalTypeValue,
      Separator(Orientation.VERTICAL),
      textRoofTypeTitle, textRoofTypeValue
    )
    //============================================
    val hBoxFlags1 = HBox()
    hBoxFlags1.spacing = 10.0
    val textHasPhone = if(obj.hasPhone)
      Text(PROPS.getProperty("statementHasPhone")) else
      Text(PROPS.getProperty("statementDoesNotHavePhone"))
    val textHasGas = if(obj.hasGas)
      Text(PROPS.getProperty("statementHasGas")) else
      Text(PROPS.getProperty("statementDoesNotHaveGas"))
    val textWater = if(obj.hasWater)
      Text(PROPS.getProperty("statementHasWater")) else
      Text(PROPS.getProperty("statementDoesNotHaveWater"))
    val textElectricity = if(obj.hasElectricity)
      Text(PROPS.getProperty("statementHasElectricity")) else
      Text(PROPS.getProperty("statementDoesNotHaveElectricity"))

    Text(PROPS.getProperty("statementDoesNotHaveStorageRoom"))

    hBoxFlags1.children.addAll(
      textHasPhone,
      Separator(Orientation.VERTICAL),
      textHasGas,
      Separator(Orientation.VERTICAL),
      textWater,
      Separator(Orientation.VERTICAL),
      textElectricity
    )
    //============================================
    val hBoxFlags2 = HBox()
    hBoxFlags2.spacing = 10.0

    hBoxFlags2.children.addAll(

      Separator(Orientation.VERTICAL),

      Separator(Orientation.VERTICAL),

      Separator(Orientation.VERTICAL)

    )
    //============================================
    val hBoxFlags3 = HBox()
    hBoxFlags3.spacing = 10.0

    hBoxFlags3.children.addAll(

      Separator(Orientation.VERTICAL),

      Separator(Orientation.VERTICAL)

    )
    //============================================
    val hBoxDescription = HBox()
    hBoxDescription.spacing = 10.0
    val textDescriptionTitle = Text(PROPS.getProperty("descriptions") + ": ")
    val textDescriptionValue = Text(obj.description)

    textDescriptionValue.wrappingWidth = 500.0

    hBoxDescription.children.addAll(
      textDescriptionTitle, textDescriptionValue
    )
    //============================================
    root.children.addAll(
      hBoxDocInfo,
      Separator(Orientation.HORIZONTAL),
      hBoxAddress,
      Separator(Orientation.HORIZONTAL),
      hBoxInfo1,
      Separator(Orientation.HORIZONTAL),
      hBoxInfo2,
      Separator(Orientation.HORIZONTAL),
      hBoxInfo3,
      Separator(Orientation.HORIZONTAL),
      hBoxFlags1,
      Separator(Orientation.HORIZONTAL),
      //      hBoxFlags2,
//      Separator(Orientation.HORIZONTAL),
//      hBoxFlags3,
//      Separator(Orientation.HORIZONTAL),
      hBoxDescription
    )
  }
}