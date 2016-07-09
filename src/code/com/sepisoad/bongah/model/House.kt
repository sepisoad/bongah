package com.sepisoad.bongah.model

import tornadofx.getProperty
import tornadofx.property

class HouseInfo
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

  buildingLevelsCount: Int,
  levelNumber: Int,
  bedRoomsCount: Int,
  hasSeparateWay: Boolean,
  hasFurniture: Boolean,
  hasStorageRoom: Boolean,
  hasParking: Boolean,
  hasVideoDoorPhone: Boolean,
  hasAirConditioner: Boolean,
  hasPackagePlumbing: Boolean,
  hasCabinet: Boolean,
  hasWallDresser: Boolean,
  hasLoan: Boolean,
  loanAmount: Double,
  houseType: HouseType,
  terrainType: TerrainType
)
{
  var id by property<Int>()
  fun idProperty() = getProperty(HouseInfo::id)
  var hasReferrer by property<Boolean>()
  fun hasReferrerProperty() = getProperty(HouseInfo::hasReferrer)
  var referrerId by property<Int>()
  fun referrerIdProperty() = getProperty(HouseInfo::referrerId)
  var date by property<String>()
  fun dateProperty() = getProperty(HouseInfo::date)

  var address by property<String>()
  fun addressProperty() = getProperty(HouseInfo::address)
  var area by property<Double>()
  fun areaProperty() = getProperty(HouseInfo::area)
  var ownerName by property<String>()
  fun ownerNameProperty() = getProperty(HouseInfo::ownerName)
  var ownerPhoneNumbers by property<String>()
  fun ownerPhoneNumbersProperty() = getProperty(HouseInfo::ownerPhoneNumbers)
  var sindhType by property<SindhType>()
  fun sindhTypeProperty() = getProperty(HouseInfo::sindhType)
  var price by property<Double>()
  fun priceProperty() = getProperty(HouseInfo::price)
  var isSwappable by property<Boolean>()
  fun isSwappableProperty() = getProperty(HouseInfo::isSwappable)
  var phoneNumbers by property<String>()
  fun phoneNumbersProperty() = getProperty(HouseInfo::phoneNumbers)
  var hasPhone by property<Boolean>()
  fun hasPhoneProperty() = getProperty(HouseInfo::hasPhone)
  var hasWater by property<Boolean>()
  fun hasWaterProperty() = getProperty(HouseInfo::hasWater)
  var hasElectricity by property<Boolean>()
  fun hasElectricityProperty() = getProperty(HouseInfo::hasElectricity)
  var hasGas by property<Boolean>()
  fun hasGasProperty() = getProperty(HouseInfo::hasGas)
  var latitude by property<Double>()
  fun latitudeProperty() = getProperty(HouseInfo::latitude)
  var longitude by property<Double>()
  fun longitudeProperty() = getProperty(HouseInfo::longitude)
  var pictures by property<String>()
  fun picturesProperty() = getProperty(HouseInfo::pictures)
  var description by property<String>()
  fun descriptionProperty() = getProperty(HouseInfo::description)

  var buildingLevelsCount by property<Int>()
  fun buildingLevelsCountProperty() = getProperty(HouseInfo::buildingLevelsCount)
  var levelNumber by property<Int>()
  fun levelNumberProperty() = getProperty(HouseInfo::levelNumber)
  var bedRoomsCount by property<Int>()
  fun bedRoomsCountProperty() = getProperty(HouseInfo::bedRoomsCount)
  var hasSeparateWay by property<Boolean>()
  fun hasSeparateWayProperty() = getProperty(HouseInfo::hasSeparateWay)
  var hasFurniture by property<Boolean>()
  fun hasFurnitureProperty() = getProperty(HouseInfo::hasFurniture)
  var hasStorageRoom by property<Boolean>()
  fun hasStorageRoomProperty() = getProperty(HouseInfo::hasStorageRoom)
  var hasParking by property<Boolean>()
  fun hasParkingProperty() = getProperty(HouseInfo::hasParking)
  var hasVideoDoorPhone by property<Boolean>()
  fun hasVideoDoorPhoneProperty() = getProperty(HouseInfo::hasVideoDoorPhone)
  var hasAirConditioner by property<Boolean>()
  fun hasAirConditionerProperty() = getProperty(HouseInfo::hasAirConditioner)
  var hasPackagePlumbing by property<Boolean>()
  fun hasPackagePlumbingProperty() = getProperty(HouseInfo::hasPackagePlumbing)
  var hasFullPackage by property<Boolean>()
  fun hasFullPackageProperty() = getProperty(HouseInfo::hasFullPackage)
  var hasCabinet by property<Boolean>()
  fun hasCabinetProperty() = getProperty(HouseInfo::hasCabinet)
  var hasWallDresser by property<Boolean>()
  fun hasWallDresserProperty() = getProperty(HouseInfo::hasWallDresser)
  var hasLoan by property<Boolean>()
  fun hasLoanProperty() = getProperty(HouseInfo::hasLoan)
  var loanAmount by property<Double>()
  fun loanAmountProperty() = getProperty(HouseInfo::loanAmount)
  var houseType by property<HouseType>()
  fun houseTypeProperty() = getProperty(HouseInfo::houseType)
  var terrainType by property<TerrainType>()
  fun terrainTypeProperty() = getProperty(HouseInfo::terrainType)

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

    this.buildingLevelsCount = buildingLevelsCount
    this.levelNumber = levelNumber
    this.bedRoomsCount = bedRoomsCount
    this.hasSeparateWay = hasSeparateWay
    this.hasFurniture = hasFurniture
    this.hasStorageRoom = hasStorageRoom
    this.hasParking = hasParking
    this.hasVideoDoorPhone = hasVideoDoorPhone
    this.hasAirConditioner = hasAirConditioner
    this.hasPackagePlumbing = hasPackagePlumbing
    this.hasFullPackage = hasFullPackage
    this.hasCabinet = hasCabinet
    this.hasWallDresser = hasWallDresser
    this.hasLoan = hasLoan
    this.loanAmount = loanAmount
    this.houseType = houseType
    this.terrainType = terrainType
  }
}



