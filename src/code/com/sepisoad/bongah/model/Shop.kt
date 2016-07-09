package com.sepisoad.bongah.model

import tornadofx.getProperty
import tornadofx.property

class ShopInfo
(
  id: Int,
  hasReferrer: Boolean,
  referrerId: Int,
  date: String,

  address: String,
  area: Double,
  ownerName: String,
  ownerPhoneNumbers: String,
  sindhType: SindhType,
  price: Double,
  isSwappable: Boolean,
  phoneNumbers: String,
  hasPhone: Boolean,
  hasWater: Boolean,
  hasElectricity: Boolean,
  hasGas: Boolean,
  latitude: Double,
  longitude: Double,
  pictures: String,
  description: String,

  ownershipType: OwnershipType,
  roofType: RoofType
)
{
  var id by property<Int>()
  fun idProperty() = getProperty(ShopInfo::id)
  var hasReferrer by property<Boolean>()
  fun hasReferrerProperty() = getProperty(ShopInfo::hasReferrer)
  var referrerId by property<Int>()
  fun referrerIdProperty() = getProperty(ShopInfo::referrerId)
  var date by property<String>()
  fun dateProperty() = getProperty(ShopInfo::date)

  var address by property<String>()
  fun addressProperty() = getProperty(ShopInfo::address)
  var area by property<Double>()
  fun areaProperty() = getProperty(ShopInfo::area)
  var ownerName by property<String>()
  fun ownerNameProperty() = getProperty(ShopInfo::ownerName)
  var ownerPhoneNumbers by property<String>()
  fun ownerPhoneNumbersProperty() = getProperty(ShopInfo::ownerPhoneNumbers)
  var sindhType by property<SindhType>()
  fun sindhTypeProperty() = getProperty(ShopInfo::sindhType)
  var price by property<Double>()
  fun priceProperty() = getProperty(ShopInfo::price)
  var isSwappable by property<Boolean>()
  fun isSwappableProperty() = getProperty(ShopInfo::isSwappable)
  var phoneNumbers by property<String>()
  fun phoneNumbersProperty() = getProperty(ShopInfo::phoneNumbers)
  var hasPhone by property<Boolean>()
  fun hasPhoneProperty() = getProperty(ShopInfo::hasPhone)
  var hasWater by property<Boolean>()
  fun hasWaterProperty() = getProperty(ShopInfo::hasWater)
  var hasElectricity by property<Boolean>()
  fun hasElectricityProperty() = getProperty(ShopInfo::hasElectricity)
  var hasGas by property<Boolean>()
  fun hasGasProperty() = getProperty(ShopInfo::hasGas)
  var latitude by property<Double>()
  fun latitudeProperty() = getProperty(ShopInfo::latitude)
  var longitude by property<Double>()
  fun longitudeProperty() = getProperty(ShopInfo::longitude)
  var pictures by property<String>()
  fun picturesProperty() = getProperty(ShopInfo::pictures)
  var description by property<String>()
  fun descriptionProperty() = getProperty(ShopInfo::description)

  var ownershipType by property<OwnershipType>()
  fun ownershipTypeProperty() = getProperty(ShopInfo::ownershipType)
  var roofType by property<RoofType>()
  fun roofTypeProperty() = getProperty(ShopInfo::roofType)

  init{
    this.id = id
    this.hasReferrer = hasReferrer
    this.referrerId = referrerId
    this.date = date

    this.address = address
    this.area = area
    this.ownerName = ownerName
    this.ownerPhoneNumbers = ownerPhoneNumbers
    this.sindhType = sindhType
    this.price = price
    this.isSwappable = isSwappable
    this.phoneNumbers = phoneNumbers
    this.hasPhone = hasPhone
    this.hasWater = hasWater
    this.hasElectricity = hasElectricity
    this.hasGas = hasGas
    this.latitude = latitude
    this.longitude = longitude
    this.pictures = pictures
    this.description = description

    this.ownershipType = ownershipType
    this.roofType = roofType
  }
}
