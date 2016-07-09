package com.sepisoad.bongah.ui.view

import com.sepisoad.bongah.app.PROPS
import com.sepisoad.bongah.ui.helper.OnAfterSaveAction
import com.sepisoad.bongah.ui.FxApp
import com.sepisoad.bongah.ui.ViewMode
import javafx.event.Event
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.*
import javafx.scene.layout.VBox

class MainViewController () {
  lateinit var appUi: FxApp
  private var landTabExists = false
  private var houseTabExists = false
  private var shopTabExists = false
  private var houseRentalTabExists = false
  private var shopRentalTabExists = false
  private var colleagueTabExists = false
  private var customerTabExists = false
  private var findLandTabExists = false
  private var findHouseTabExists = false
  private var findShopTabExists = false
  private var findHouseRentalTabExists = false
  private var findShopRentalTabExists = false
  private var findColleagueTabExists = false
  private var findCustomerTabExists = false
  private var aboutOwnerTabExists = false
  private var aboutAppTabExists = false
  private var settingsTabExists = false

  @FXML private lateinit var menuButtonLand: MenuButton
  @FXML private lateinit var menuButtonHouse: MenuButton
  @FXML private lateinit var menuButtonShop: MenuButton
  @FXML private lateinit var menuButtonRentalOrMortage: MenuButton
  @FXML private lateinit var menuButtonCollegial: MenuButton
  @FXML private lateinit var menuButtonCustomers: MenuButton

  @FXML private lateinit var menuItemNewLand: MenuItem
  @FXML private lateinit var menuItemNewHouse: MenuItem
  @FXML private lateinit var menuItemNewShop: MenuItem
  @FXML private lateinit var menuItemNewHouseRental: MenuItem
  @FXML private lateinit var menuItemNewShopRental: MenuItem
  @FXML private lateinit var menuItemFindLand: MenuItem
  @FXML private lateinit var menuItemFindHouse: MenuItem
  @FXML private lateinit var menuItemFindShop: MenuItem
  @FXML private lateinit var menuItemFindHouseRental: MenuItem
  @FXML private lateinit var menuItemFindShopRental: MenuItem
  @FXML private lateinit var menuItemNewColleague: MenuItem
  @FXML private lateinit var menuItemFindColleague: MenuItem
  @FXML private lateinit var menuItemNewCustomer: MenuItem
  @FXML private lateinit var menuItemFindCustomer: MenuItem
  @FXML private lateinit var buttonAboutApp: Button
  @FXML private lateinit var buttonSettings: Button
  @FXML private lateinit var tabPaneMain: TabPane


  @FXML private fun initialize(): Unit {
    menuButtonLand.text = PROPS.getProperty("land")
    menuButtonHouse.text = PROPS.getProperty("house")
    menuButtonShop.text = PROPS.getProperty("shop")
    menuButtonRentalOrMortage.text = PROPS.getProperty("rentalOrMortage")

    menuButtonCollegial.text = PROPS.getProperty("collegial")
    menuButtonCustomers.text = PROPS.getProperty("customers")
    menuItemNewLand.text = PROPS.getProperty("newLand")
    menuItemNewHouse.text = PROPS.getProperty("newHouse")
    menuItemNewShop.text = PROPS.getProperty("newShop")
    menuItemNewHouseRental.text = PROPS.getProperty("newHouseRental")
    menuItemNewShopRental.text = PROPS.getProperty("newShopRental")

    menuItemFindLand.text = PROPS.getProperty("findLand")
    menuItemFindHouse.text = PROPS.getProperty("findHouse")
    menuItemFindShop.text = PROPS.getProperty("findShop")
    menuItemFindHouseRental.text = PROPS.getProperty("findHouseRental")
    menuItemFindShopRental.text = PROPS.getProperty("findShopRental")

    menuItemNewColleague.text = PROPS.getProperty("newColleague")
    menuItemFindColleague.text = PROPS.getProperty("findColleague")
    menuItemNewCustomer.text = PROPS.getProperty("newCustomer")
    menuItemFindCustomer.text = PROPS.getProperty("findCustomer")
    buttonAboutApp.text = PROPS.getProperty("aboutApp")
    buttonSettings.text = PROPS.getProperty("settings")
  }

  //==============================================================

  @FXML fun onMenuItemNewLandAction(): Unit {
    if(!landTabExists){
      val tab = Tab(PROPS.getProperty("land"))
      tab.onClosed = EventHandler<Event> { landTabExists = false }
      tabPaneMain.tabs.add(tab)
      landTabExists = true
      val childNode = loadNewLandView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadNewLandView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/land_new.fxml")

    val root = loader.load<VBox>()
    var landNewCtrl: LandNewController
    landNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        landTabExists = false
      }
    }

    landNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()
    return root
  }

  //----------------

  @FXML fun onMenuItemFindLandAction(): Unit {
    if(!findLandTabExists){
      val tab = Tab(PROPS.getProperty("findLand"))
      tab.onClosed = EventHandler<Event> { findLandTabExists = false }
      tabPaneMain.tabs.add(tab)
      findLandTabExists = true

      val childNode = loadFindLandView()
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadFindLandView(): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/land_find.fxml")

    val root = loader.load<VBox>()
    var landFindCtrl: LandFindController
    landFindCtrl = loader.getController()
    return root
  }

  //==============================================================

  @FXML fun onMenuItemNewHouseAction(): Unit {
    if(!houseTabExists){
      val tab = Tab(PROPS.getProperty("house"))
      tab.onClosed = EventHandler<Event> { houseTabExists = false }
      tabPaneMain.tabs.add(tab)
      houseTabExists = true
      val childNode = loadNewHouseView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadNewHouseView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/house_new.fxml")

    val root = loader.load<VBox>()
    var houseNewCtrl: HouseNewController
    houseNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        houseTabExists = false
      }
    }

    houseNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()
    return root
  }

  //----------------

  @FXML fun onMenuItemFindHouseAction(): Unit {
    if(!findHouseTabExists){
      val tab = Tab(PROPS.getProperty("findHouse"))
      tab.onClosed = EventHandler<Event> { findHouseTabExists = false }
      tabPaneMain.tabs.add(tab)
      findHouseTabExists = true
      val childNode = loadFindHouseView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadFindHouseView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/house_find.fxml")

    val root = loader.load<VBox>()
    var houseFindCtrl: HouseFindController
    houseFindCtrl = loader.getController()
    return root
  }

  //==============================================================

  @FXML fun onMenuItemNewShopAction(): Unit {
    if(!shopTabExists){
      val tab = Tab(PROPS.getProperty("shop"))
      tab.onClosed = EventHandler<Event> { shopTabExists = false }
      tabPaneMain.tabs.add(tab)
      val childNode = loadNewShopView(tab)
      shopTabExists = true
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadNewShopView(tab: Tab): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/shop_new.fxml")

    val root = loader.load<VBox>()
    var shopNewCtrl: ShopNewController
    shopNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        shopTabExists = false
      }
    }

    shopNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()
    return root
  }
  //----------------

  @FXML fun onMenuItemFindShopAction(): Unit {
    if(!findShopTabExists){
      val tab = Tab(PROPS.getProperty("findShop"))
      tab.onClosed = EventHandler<Event> { findShopTabExists = false }
      tabPaneMain.tabs.add(tab)
      findShopTabExists = true
      val childNode = loadFindShopView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadFindShopView(tab: Tab): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/shop_find.fxml")

    val root = loader.load<VBox>()
    var shopFindCtrl: ShopFindController
    shopFindCtrl = loader.getController()
    return root
  }

  //==============================================================

  @FXML fun onMenuItemNewHouseRentalAction(): Unit {
    if(!houseRentalTabExists){
      val tab = Tab(PROPS.getProperty("newHouseRental"))
      tab.onClosed = EventHandler<Event> { houseRentalTabExists = false }
      tabPaneMain.tabs.add(tab)
      houseRentalTabExists = true
      var childNode = loadNewHouseRentalView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadNewHouseRentalView(tab: Tab): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/rental_house_new.fxml")

    val root = loader.load<VBox>()
    var rentalHouseNewCtrl: RentalHouseNewController
    rentalHouseNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        houseRentalTabExists = false
      }
    }

    rentalHouseNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()
    return root
  }

  //----------------

  @FXML fun onMenuItemFindHouseRentalAction(): Unit {
    if(!findHouseRentalTabExists){
      val tab = Tab(PROPS.getProperty("findHouseRental"))
      tab.onClosed = EventHandler<Event> { findHouseRentalTabExists = false }
      tabPaneMain.tabs.add(tab)
      findHouseRentalTabExists = true
      var childNode = loadFindRentalHouseView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadFindRentalHouseView(tab: Tab): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/rental_house_find.fxml")

    val root = loader.load<VBox>()
    var rentalHouseFindCtrl: RentalHouseFindController
    rentalHouseFindCtrl = loader.getController()
    return root
  }

  //==============================================================

  @FXML fun onMenuItemNewShopRentalAction(): Unit {
    if(!shopRentalTabExists){
      val tab = Tab(PROPS.getProperty("newHouseRental"))
      tab.onClosed = EventHandler<Event> { shopRentalTabExists = false }
      tabPaneMain.tabs.add(tab)
      shopRentalTabExists = true
      var childNode = loadNewShopRentalView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadNewShopRentalView(tab: Tab): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/rental_shop_new.fxml")

    val root = loader.load<VBox>()
    var rentalShopNewCtrl: RentalShopNewController
    rentalShopNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        shopRentalTabExists = false
      }
    }

    rentalShopNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()
    return root
  }

  @FXML fun onMenuItemFindShopRentalAction(): Unit {
    if(!findShopRentalTabExists){
      val tab = Tab(PROPS.getProperty("findHouseRental"))
      tab.onClosed = EventHandler<Event> { findShopRentalTabExists = false }
      tabPaneMain.tabs.add(tab)
      findShopRentalTabExists = true
      var childNode = loadFindRentalShopView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  private fun loadFindRentalShopView(tab: Tab): VBox {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/rental_shop_find.fxml")

    val root = loader.load<VBox>()
    var rentalShopFindCtrl: RentalShopFindController
    rentalShopFindCtrl = loader.getController()
    return root
  }

  //==============================================================

  @FXML fun onMenuItemNewColleagueAction(): Unit{
    if(!colleagueTabExists){
      val tab = Tab(PROPS.getProperty("newColleague"))
      tab.onClosed = EventHandler<Event> { colleagueTabExists = false }
      tabPaneMain.tabs.add(tab)
      colleagueTabExists = true
      val childNode = loadNewColleagueView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  fun loadNewColleagueView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/colleague_new.fxml")

    val root = loader.load<VBox>()
    var colleagueNewCtrl: ColleagueNewController
    colleagueNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        colleagueTabExists = false
      }
    }

    colleagueNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()

    return root
  }

  //----------------

  @FXML fun onMenuItemFindColleagueAction(): Unit{
    if(!findColleagueTabExists){
      val tab = Tab(PROPS.getProperty("findColleague"))
      tab.onClosed = EventHandler<Event> { findColleagueTabExists = false }
      tabPaneMain.tabs.add(tab)
      findColleagueTabExists = true

      val childNode = loadFindColleagueView()
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  fun loadFindColleagueView(): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/colleague_find.fxml")

    val root = loader.load<VBox>()
    var colleagueFindCtrl: ColleagueFindController
    colleagueFindCtrl = loader.getController()
    colleagueFindCtrl.viewMode = ViewMode.STAND_ALONE
    return root
  }

  //==============================================================

  @FXML fun onMenuItemNewCustomerAction(): Unit{
    if(!customerTabExists){
      val tab = Tab(PROPS.getProperty("newCustomer"))
      tab.onClosed = EventHandler<Event> { customerTabExists = false }
      tabPaneMain.tabs.add(tab)
      customerTabExists = true
      val childNode = loadNewCustomerView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  fun loadNewCustomerView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/customer_new.fxml")

    val root = loader.load<VBox>()
    var customerNewCtrl: CustomerNewController
    customerNewCtrl = loader.getController()

    class OnAfterSaveActionHandler : OnAfterSaveAction {
      override fun handle() {
        tabPaneMain.tabs.remove(tab)
        customerTabExists = false
      }
    }

    customerNewCtrl.onAfterSaveAction = OnAfterSaveActionHandler()

    return root
  }

  //----------------

  @FXML fun onMenuItemFindCustomerAction(): Unit{
    if(!findCustomerTabExists){
      val tab = Tab(PROPS.getProperty("findColleague"))
      tab.onClosed = EventHandler<Event> { findCustomerTabExists = false }
      tabPaneMain.tabs.add(tab)
      findCustomerTabExists = true

      val childNode = loadFindCustomerView()
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  fun loadFindCustomerView(): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/customer_find.fxml")

    val root = loader.load<VBox>()
    var customerFindCtrl: CustomerFindController
    customerFindCtrl = loader.getController()
    customerFindCtrl.viewMode = ViewMode.STAND_ALONE
    return root
  }

  //==============================================================
  @FXML fun onButtonSettingsOwnerAction(): Unit{
    if(!settingsTabExists){
      val tab = Tab(PROPS.getProperty("settings"))
      tab.onClosed = EventHandler<Event> { settingsTabExists = false }
      tabPaneMain.tabs.add(tab)
      settingsTabExists = true
      val childNode = loadSettingsView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  fun loadSettingsView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/settings.fxml")

    val root = loader.load<VBox>()
    var settingsCtrl: SettingsController
    settingsCtrl = loader.getController()

    return root
  }

  //-----------------------------------------

  @FXML fun onButtonAboutAppAction(): Unit{
    if(!aboutAppTabExists){
      val tab = Tab(PROPS.getProperty("aboutApp"))
      tab.onClosed = EventHandler<Event> { aboutAppTabExists = false }
      tabPaneMain.tabs.add(tab)
      aboutAppTabExists = true
      val childNode = loadAboutAppView(tab)
      tab.content = childNode
      tabPaneMain.selectionModel.select(tab)
    }
  }

  fun loadAboutAppView(tab: Tab): VBox? {
    val loader = FXMLLoader()

    val cl = FxApp::class.java.classLoader
    loader.location = cl.getResource("res/ui/about_app.fxml")

    val root = loader.load<VBox>()
    var aboutAppCtrl: AboutAppController
    aboutAppCtrl = loader.getController()

    return root
  }
}
