package com.sepisoad.bongah.app

import com.sepisoad.bongah.db.DbManager
import com.sepisoad.bongah.ui.FxApp
import jodd.io.FileUtil
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStreamReader
import java.util.*
import javax.swing.JOptionPane

object App {
  @JvmField var props = Properties()
  val logger = LoggerFactory.getLogger(App::class.java);

  fun start(args: Array<String>): Unit {
    initApp()
    if(!checkAppHomeDir())
      return
    if(!checkConfigFile())
      return
    if(!checkImageDir())
      return
    if(!applyConfigLanguage())
      return
    if(!checkDbFile())
      return
    if(!checkAppOwnerProfile())
      return
    FxApp.bootstrap(args)
  }

  private fun initApp(): Unit {
    PROPS = props
    APP_HOME_PATH = System.getProperty("user.home") + File.separator + APP_NAME
    APP_IMG_DIR_PATH = APP_HOME_PATH  + File.separator + APP_IMG_DIR_NAME
    APP_LAND_IMG_DIR_PATH = APP_IMG_DIR_PATH + File.separator + APP_LAND_IMG_DIR_NAME
    APP_HOUSE_IMG_DIR_PATH = APP_IMG_DIR_PATH + File.separator + APP_HOUSE_IMG_DIR_NAME
    APP_SHOP_IMG_DIR_PATH = APP_IMG_DIR_PATH + File.separator + APP_SHOP_IMG_DIR_NAME
    APP_RENTAL_IMG_DIR_PATH = APP_IMG_DIR_PATH + File.separator + APP_RENTAL_IMG_DIR_NAME
    APP_RENTAL_HOUSE_IMG_DIR_PATH = APP_RENTAL_IMG_DIR_PATH + File.separator + APP_RENTAL_HOUSE_IMG_SUB_DIR_NAME
    APP_RENTAL_SHOP_IMG_DIR_PATH = APP_RENTAL_IMG_DIR_PATH + File.separator + APP_RENTAL_SHOP_IMG_SUB_DIR_NAME
    APP_CONFIG_PATH = APP_HOME_PATH + File.separator + APP_CONFIG_NAME
    APP_PROFILE_PATH = APP_HOME_PATH + File.separator + APP_PROFILE_NAME
    APP_DB_PATH = APP_HOME_PATH + File.separator + APP_DB_FILE_NAME
    APP_RES_DIR_PATH = APP_HOME_PATH + File.separator + APP_RES_DIR_NAME
  }

  private fun checkAppHomeDir(): Boolean {
    var result: Boolean = true
    val homeDir = File(APP_HOME_PATH)

    if(!homeDir.exists()){
      if(!homeDir.mkdir())
        result = false
    }
    return result
  }

  fun checkAppOwnerProfile(): Boolean {
    var result: Boolean = false

    do{
      if(!ownerProfileExists()){
        val alertMsg = PROPS.getProperty("alertMsgOwnerProfileIsMissing")
        val title = PROPS.getProperty("error")
        JOptionPane.showMessageDialog(null, alertMsg, title, JOptionPane.ERROR_MESSAGE);
        break
      }
      val mac = OwnerProfileManager.getSystemMacAddress();
      try {
        var op = OwnerProfileManager.decryptProfile(PROFILE_NAME, PROFILE_ENC_KEY)
        if(!mac.equals(op.mac)){
          val alertMsg = PROPS.getProperty("alertMsgOwnerProfileIsNotRegistered")
          val title = PROPS.getProperty("error")
          JOptionPane.showMessageDialog(null, alertMsg, title, JOptionPane.ERROR_MESSAGE)
          break;
        }
      }catch(ex: Exception){
        val alertMsg = PROPS.getProperty("alertMsgOwnerProfileIsCorrupted")
        val title = PROPS.getProperty("error")
        JOptionPane.showMessageDialog(null, alertMsg, title, JOptionPane.ERROR_MESSAGE);
        break
      }

      result = true
    }while(false)

    return result
  }

  fun ownerProfileExists(): Boolean{
    val file = File(PROFILE_NAME)
    if(file.exists())
      return true
    else
      return false
  }

  fun checkConfigFile(): Boolean {
    var result: Boolean = false
    val configFile = File(APP_CONFIG_PATH)
    var config: String

    do{
      if(!configFile.exists()){
        config = loadDefaultConfigFile()
        if(config.isEmpty())
          break
        if(!saveDefaultConfigFile())
          break

      }else{
        config = loadConfigFile()
        if(config.isEmpty())
          break
      }
      ConfigManager.initConfigObject(config)
      result = true
    }while(false)

    return result
  }

  fun loadDefaultConfigFile(): String {
    var content = ""

    val cl = FxApp::class.java.classLoader
    val stream = cl.getResourceAsStream(RES_DEFAULT_CONFIG)

    val scanner = Scanner(stream)

    var canGo = true
    do{
      if(scanner.hasNext())
        content = content + scanner.nextLine()
      else
        canGo = false
    }while(canGo)

    return content
  }

  fun saveDefaultConfigFile(): Boolean{
    var result = false

    do{
      val cl = FxApp::class.java.classLoader
      val stream = cl.getResourceAsStream(RES_DEFAULT_CONFIG)
      val scanner = Scanner(stream)
      var content = ""
      var canGo = true
      do{
        if(scanner.hasNext())
          content = content + scanner.nextLine()
        else
          canGo = false
      }while(canGo)

      if(content.isNullOrEmpty())
        break
      try {
        FileUtil.writeString(APP_CONFIG_PATH, content)
      }catch(ex: Exception){
        //TODO
        break;
      }

      result = true
    }while(false)

    return result
  }

  fun loadConfigFile(): String {
    var content = ""

    val cl = FxApp::class.java.classLoader
    print("DEBUG: ${APP_CONFIG_PATH}")
    val file = File(APP_CONFIG_PATH);

    val scanner = Scanner(file)

    var canGo = true
    do{
      if(scanner.hasNext())
        content = content + scanner.nextLine()
      else
        canGo = false
    }while(canGo)

    return content
  }

  fun applyConfigLanguage(): Boolean {
    var result: Boolean

    do{
      when(Config.language){
        "en" -> applyEnglishLanguage()
        "fa" -> applyPersianLanguage()
        "af" -> Unit
      }
      result = true
    }while(false)

    return result
  }

  fun applyEnglishLanguage(): Unit {
    val cl = FxApp::class.java.classLoader
    val i18nRes = cl.getResourceAsStream("res/strings/strings.properties");
    var reader = InputStreamReader(i18nRes, "UTF-8")
    props.load(reader)
    Config.layoutDirection = "LTR"
  }

  fun applyPersianLanguage(): Unit {
    val cl = FxApp::class.java.classLoader
    val i18nRes = cl.getResourceAsStream("res/strings/strings_fa.properties");
    var reader = InputStreamReader(i18nRes, "UTF-8")
    props.load(reader)
    Config.layoutDirection = "RTL"
  }

  private fun checkImageDir(): Boolean{
    var result = false
    val imagesDir = File(APP_IMG_DIR_PATH)
    val landImagesDir = File(APP_LAND_IMG_DIR_PATH)
    val houseImagesDir = File(APP_HOUSE_IMG_DIR_PATH)
    val shopImagesDir = File(APP_SHOP_IMG_DIR_PATH)
    val rentalImagesDir = File(APP_RENTAL_IMG_DIR_PATH)
    val rentalHouseImagesDir = File(APP_RENTAL_HOUSE_IMG_DIR_PATH)
    val rentalShopImagesDir = File(APP_RENTAL_SHOP_IMG_DIR_PATH)

    do{
      if(!imagesDir.exists())
        if(!imagesDir.mkdir())
          break

      if(!landImagesDir.exists())
        if(!landImagesDir.mkdir())
          break

      if(!houseImagesDir.exists())
        if(!houseImagesDir.mkdir())
          break

      if(!shopImagesDir.exists())
        if(!shopImagesDir.mkdir())
          break

      if(!rentalImagesDir.exists())
        if(!rentalImagesDir.mkdir())
          break

      if(!rentalHouseImagesDir.exists())
        if(!rentalHouseImagesDir.mkdir())
          break

      if(!rentalShopImagesDir.exists())
        if(!rentalShopImagesDir.mkdir())
          break

      result = true
    }while(false)

    return result
  }

  private fun checkDbFile(): Boolean {
    var result = false

    do{
      if(!DbManager.init(DB_DRIVER_CLASS, DB_DRIVER, APP_DB_PATH, DB_USERNAME, DB_PASSWORD))
        break
      result = true
    }while(false)

    return result
  }
}