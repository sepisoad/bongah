package com.sepisoad.bongah.app

import jodd.json.JsonParser

object Config{
  var language: String = "en"
  var windowWidth: Int = 0
  var windowHeight: Int = 0
  var layoutDirection: String = "LTR"
  var usePassword: Boolean = false
}

object ConfigManager{
  fun initConfigObject(config: String): Boolean {
    var result = false

    do{
      val jsonParser = JsonParser()
      val obj = jsonParser.parse(config, Config.javaClass) ?: break
      Config.language = obj.language
      Config.windowWidth = obj.windowWidth
      Config.windowHeight = obj.windowHeight
      Config.layoutDirection = obj.layoutDirection
      Config.usePassword = obj.usePassword

      result = true
    }while(false)

    return result
  }


}
