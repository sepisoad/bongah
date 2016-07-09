package com.sepisoad.bongah.model

import tornadofx.getProperty
import tornadofx.property

class RentalHouseInfo
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
  buildingLevelsCount: Int,
  levelNumber: Int,
  bedRoomsCount: Int,
  hasSeparateWay: Boolean,
  hasStorageRoom: Boolean,
  hasParking: Boolean,
  hasCabinet: Boolean,
  hasWallDresser: Boolean,
  houseType: HouseType,
  description: String
)
{
  var id by property<Int>()
  fun idProperty() = getProperty(RentalHouseInfo::id)
  var hasReferrer by property<Boolean>()
  fun hasReferrerProperty() = getProperty(RentalHouseInfo::hasReferrer)
  var referrerId by property<Int>()
  fun referrerIdProperty() = getProperty(RentalHouseInfo::referrerId)
  var date by property<String>()
  fun dateProperty() = getProperty(RentalHouseInfo::date)

  var address by property<String>()
  fun addressProperty() = getProperty(RentalHouseInfo::address)
  var area by property<Double>()
  fun areaProperty() = getProperty(RentalHouseInfo::area)
  var ownerName by property<String>()
  fun ownerNameProperty() = getProperty(RentalHouseInfo::ownerName)
  var ownerPhoneNumbers by property<String>()
  fun ownerPhoneNumbersProperty() = getProperty(RentalHouseInfo::ownerPhoneNumbers)

  var rentalType by property<RentalType>()
  fun rentalTypeProperty() = getProperty(RentalHouseInfo::rentalType)
  var advanceAmount by property<Double>()
  fun advanceAmountProperty() = getProperty(RentalHouseInfo::advanceAmount)
  var rentalAmount by property<Double>()
  fun rentalAmountProperty() = getProperty(RentalHouseInfo::rentalAmount)
  var mortageAmount by property<Double>()
  fun mortageAmountProperty() = getProperty(RentalHouseInfo::mortageAmount)

  var latitude by property<Double>()
  fun latitudeProperty() = getProperty(RentalHouseInfo::latitude)
  var longitude by property<Double>()
  fun longitudeProperty() = getProperty(RentalHouseInfo::longitude)
  var pictures by property<String>()
  fun picturesProperty() = getProperty(RentalHouseInfo::pictures)
  var description by property<String>()
  fun descriptionProperty() = getProperty(RentalHouseInfo::description)

  var buildingLevelsCount by property<Int>()
  fun buildingLevelsCountProperty() = getProperty(RentalHouseInfo::buildingLevelsCount)
  var levelNumber by property<Int>()
  fun levelNumberProperty() = getProperty(RentalHouseInfo::levelNumber)
  var bedRoomsCount by property<Int>()
  fun bedRoomsCountProperty() = getProperty(RentalHouseInfo::bedRoomsCount)
  var hasSeparateWay by property<Boolean>()
  fun hasSeparateWayProperty() = getProperty(RentalHouseInfo::hasSeparateWay)
  var hasStorageRoom by property<Boolean>()
  fun hasStorageRoomProperty() = getProperty(RentalHouseInfo::hasStorageRoom)
  var hasParking by property<Boolean>()
  fun hasParkingProperty() = getProperty(RentalHouseInfo::hasParking)
  var hasCabinet by property<Boolean>()
  fun hasCabinetProperty() = getProperty(RentalHouseInfo::hasCabinet)
  var hasWallDresser by property<Boolean>()
  fun hasWallDresserProperty() = getProperty(RentalHouseInfo::hasWallDresser)
  var houseType by property<HouseType>()
  fun houseTypeProperty() = getProperty(RentalHouseInfo::houseType)

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

    this.buildingLevelsCount = buildingLevelsCount
    this.levelNumber = levelNumber
    this.bedRoomsCount = bedRoomsCount
    this.hasSeparateWay = hasSeparateWay
    this.hasStorageRoom = hasStorageRoom
    this.hasParking = hasParking
    this.hasCabinet = hasCabinet
    this.hasWallDresser = hasWallDresser
    this.houseType = houseType
  }
}