package com.sepisoad.bongah.model

import tornadofx.getProperty
import tornadofx.property

class ColleagueInfo
(
  id: Int,
  name: String,
  phoneNumbers: String,
  address: String,
  description: String
)
{
  var id by property<Int>()
  fun idProperty() = getProperty(ColleagueInfo::id)
  var name by property<String>()
  fun nameProperty() = getProperty(ColleagueInfo::name)
  var phoneNumbers by property<String>()
  fun phoneNumbersProperty() = getProperty(ColleagueInfo::phoneNumbers)
  var address by property<String>()
  fun addressProperty() = getProperty(ColleagueInfo::address)
  var description by property<String>()
  fun descriptionProperty() = getProperty(ColleagueInfo::description)

  init {
    this.id = id
    this.name = name
    this.phoneNumbers = phoneNumbers
    this.address = address
    this.description = description
  }
}