package com.sepisoad.bongah.model

import tornadofx.getProperty
import tornadofx.property

class RentalShopInfo
(
  id: Int,
  hasReferrer: Boolean,
  referrerId: Int,
  date: String,
  address: String,
  area: Double,
  ownerName: String,
  ownerPhoneNumbers: String,
  rentalType: RentalType,
  advanceAmount: Double,
  rentalAmount: Double,
  mortageAmount: Double,
  latitude: Double,
  longitude: Double,
  pictures: String,
  phoneNumbers: String,
  hasPhone: Boolean,
  hasWater: Boolean,
  hasElectricity: Boolean,
  hasGas: Boolean,
  roofType: RoofType,
  description: String
)
{
  var id by property<Int>()
  fun idProperty() = getProperty(RentalShopInfo::id)
  var hasReferrer by property<Boolean>()
  fun hasReferrerProperty() = getProperty(RentalShopInfo::hasReferrer)
  var referrerId by property<Int>()
  fun referrerIdProperty() = getProperty(RentalShopInfo::referrerId)
  var date by property<String>()
  fun dateProperty() = getProperty(RentalShopInfo::date)

  var address by property<String>()
  fun addressProperty() = getProperty(RentalShopInfo::address)
  var area by property<Double>()
  fun areaProperty() = getProperty(RentalShopInfo::area)
  var ownerName by property<String>()
  fun ownerNameProperty() = getProperty(RentalShopInfo::ownerName)
  var ownerPhoneNumbers by property<String>()
  fun ownerPhoneNumbersProperty() = getProperty(RentalShopInfo::ownerPhoneNumbers)

  var rentalType by property<RentalType>()
  fun rentalTypeProperty() = getProperty(RentalShopInfo::rentalType)
  var advanceAmount by property<Double>()
  fun advanceAmountProperty() = getProperty(RentalShopInfo::advanceAmount)
  var rentalAmount by property<Double>()
  fun rentalAmountProperty() = getProperty(RentalShopInfo::rentalAmount)
  var mortageAmount by property<Double>()
  fun mortageAmountProperty() = getProperty(RentalShopInfo::mortageAmount)

  var latitude by property<Double>()
  fun latitudeProperty() = getProperty(RentalShopInfo::latitude)
  var longitude by property<Double>()
  fun longitudeProperty() = getProperty(RentalShopInfo::longitude)
  var pictures by property<String>()
  fun picturesProperty() = getProperty(RentalShopInfo::pictures)
  var description by property<String>()
  fun descriptionProperty() = getProperty(RentalShopInfo::description)

  var phoneNumbers by property<String>()
  fun phoneNumbersProperty() = getProperty(RentalShopInfo::phoneNumbers)
  var hasPhone by property<Boolean>()
  fun hasPhoneProperty() = getProperty(RentalShopInfo::hasPhone)
  var hasWater by property<Boolean>()
  fun hasWaterProperty() = getProperty(RentalShopInfo::hasWater)
  var hasElectricity by property<Boolean>()
  fun hasElectricityProperty() = getProperty(RentalShopInfo::hasElectricity)
  var hasGas by property<Boolean>()
  fun hasGasProperty() = getProperty(RentalShopInfo::hasGas)
  var roofType by property<RoofType>()
  fun roofTypeProperty() = getProperty(RentalShopInfo::roofType)

  init{
    this.id = id
    this.hasReferrer = hasReferrer
    this.referrerId = referrerId
    this.date = date

    this.address = address
    this.area = area
    this.ownerName = ownerName
    this.ownerPhoneNumbers = ownerPhoneNumbers
    this.rentalType = rentalType
    this.advanceAmount = advanceAmount
    this.rentalAmount = rentalAmount
    this.mortageAmount = mortageAmount
    this.latitude = latitude
    this.longitude = longitude
    this.pictures = pictures
    this.description = description

    this.phoneNumbers = phoneNumbers
    this.hasPhone = hasPhone
    this.hasWater = hasWater
    this.hasElectricity = hasElectricity
    this.hasGas = hasGas
    this.roofType = roofType
  }
}

