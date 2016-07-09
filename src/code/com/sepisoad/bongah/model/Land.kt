package com.sepisoad.bongah.model

import tornadofx.getProperty
import tornadofx.property

class LandInfo
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

  applicationType: ApplicationType,
  terrainType: TerrainType,
  isHouseIncluded: Boolean,
  isLandAcres: Boolean
)
{
  var id by property<Int>()
  fun idProperty() = getProperty(LandInfo::id)
  var hasReferrer by property<Boolean>()
  fun hasReferrerProperty() = getProperty(LandInfo::hasReferrer)
  var referrerId by property<Int>()
  fun referrerIdProperty() = getProperty(LandInfo::referrerId)
  var date by property<String>()
  fun dateProperty() = getProperty(LandInfo::date)

  var address by property<String>()
  fun addressProperty() = getProperty(LandInfo::address)
  var area by property<Double>()
  fun areaProperty() = getProperty(LandInfo::area)
  var ownerName by property<String>()
  fun ownerNameProperty() = getProperty(LandInfo::ownerName)
  var ownerPhoneNumbers by property<String>()
  fun ownerPhoneNumbersProperty() = getProperty(LandInfo::ownerPhoneNumbers)
  var sindhType by property<SindhType>()
  fun sindhTypeProperty() = getProperty(LandInfo::sindhType)
  var price by property<Double>()
  fun priceProperty() = getProperty(LandInfo::price)
  var isSwappable by property<Boolean>()
  fun isSwappableProperty() = getProperty(LandInfo::isSwappable)
  var phoneNumbers by property<String>()
  fun phoneNumbersProperty() = getProperty(LandInfo::phoneNumbers)
  var hasPhone by property<Boolean>()
  fun hasPhoneProperty() = getProperty(LandInfo::hasPhone)
  var hasWater by property<Boolean>()
  fun hasWaterProperty() = getProperty(LandInfo::hasWater)
  var hasElectricity by property<Boolean>()
  fun hasElectricityProperty() = getProperty(LandInfo::hasElectricity)
  var hasGas by property<Boolean>()
  fun hasGasProperty() = getProperty(LandInfo::hasGas)
  var latitude by property<Double>()
  fun latitudeProperty() = getProperty(LandInfo::latitude)
  var longitude by property<Double>()
  fun longitudeProperty() = getProperty(LandInfo::longitude)
  var pictures by property<String>()
  fun picturesProperty() = getProperty(LandInfo::pictures)
  var description by property<String>()
  fun descriptionProperty() = getProperty(LandInfo::description)

  var applicationType by property<ApplicationType>()
  fun applicationTypeProperty() = getProperty(LandInfo::applicationType)
  var terrainType by property<TerrainType>()
  fun terrainTypeProperty() = getProperty(LandInfo::terrainType)
  var isHouseIncluded by property<Boolean>()
  fun isHouseIncludedProperty() = getProperty(LandInfo::isHouseIncluded)
  var isLandAcres by property<Boolean>()
  fun isLandAcresProperty() = getProperty(LandInfo::isLandAcres)

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

    this.applicationType = applicationType
    this.terrainType = terrainType
    this.isHouseIncluded = isHouseIncluded
    this.isLandAcres = isLandAcres
  }
}


