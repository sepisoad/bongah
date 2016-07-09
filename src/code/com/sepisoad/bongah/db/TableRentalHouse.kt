package com.sepisoad.bongah.db

import com.sepisoad.bongah.model.RentalHouseInfo
import com.sepisoad.bongah.model.HouseType
import com.sepisoad.bongah.model.RentalType
import com.sepisoad.bongah.model.TerrainType
import java.sql.SQLException
import kotlin.reflect.jvm.internal.impl.com.google.protobuf.LazyStringArrayList

object TableRentalHouse {
  final val TABLE_NAME = "RENTALHOUSE"

  fun insert(obj: RentalHouseInfo): Int {
    var result = -1
    val c = DbManager.getConnection()
    val query =
      """
INSERT INTO ${TABLE_NAME}
(HASREFERRER, REFERRERID, DATE, ADDRESS, AREA, OWNERNAME,
 OWNERPHONENUMBERS, LATITUDE, LONGITUDE, PICTURES,
 HOUSETYPE, RENTALTYPE, ADVANCEAMOUNT , RENTALAMOUNT , MORTAGEAMOUNT ,
 BUILDINGLEVELSCOUNT, LEVELNUMBER, BEDROOMSCOUNT,
 HASSEPARATEWAY, HASSTORAGEROOM, HASPARKING, HASCABINET,
 HASWALLDRESSER, DESCRIPTION) VALUES
('${obj.hasReferrer}', '${obj.referrerId}', '${obj.date}', '${obj.address}', '${obj.area}', '${obj.ownerName}',
 '${obj.ownerPhoneNumbers}', '${obj.latitude}', '${obj.longitude}', '${obj.pictures}',
 '${obj.houseType}', '${obj.rentalType}','${obj.advanceAmount}', '${obj.rentalAmount}', '${obj.mortageAmount}',
 '${obj.buildingLevelsCount}', '${obj.levelNumber}', '${obj.bedRoomsCount}',
 '${obj.hasSeparateWay}', '${obj.hasStorageRoom}', '${obj.hasParking}', '${obj.hasCabinet}',
 '${obj.hasWallDresser}', '${obj.description}');
"""
    do{
      try {
        val sttmnt = c.createStatement()
        sttmnt.executeUpdate(query)
        val rs = sttmnt.executeQuery("SELECT ID from ${TABLE_NAME} order by ID DESC limit 1")
        rs.next()
        result = rs.getInt("ID")
        sttmnt.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace() //TODO
        break
      }

    }while(false)

    DbManager.disConnect(c)
    return result
  }

  fun find(rentalHouseInfo: RentalHouseInfo,
           advanceFrom: String?, advanceTo: String?,
           rentalFrom: String?, rentalTo: String?,
           mortageFrom: String?, mortageTo: String?,
           areaFrom: String?, areaTo: String?)
    : MutableList<RentalHouseInfo>
  {
    var result: MutableList<RentalHouseInfo> = mutableListOf()

    val connection = DbManager.getConnection()
    val houseType = " HOUSETYPE = '${rentalHouseInfo.houseType}' "
    val rentalType = "RENTALTYPE = '${rentalHouseInfo.rentalType}'"
    val address = " ADDRESS LIKE '%${rentalHouseInfo.address}%' "
    val owner = " OWNERNAME LIKE '%${rentalHouseInfo.ownerName}%' "

    var advance = ""
    if(!advanceFrom.isNullOrEmpty() and !advanceTo.isNullOrEmpty())
      advance = " ADVANCEAMOUNT BETWEEN ${advanceFrom} AND ${advanceTo} "
    if(!advanceFrom.isNullOrEmpty() and advanceTo.isNullOrEmpty())
      advance = " ADVANCEAMOUNT > ${advanceFrom} "
    if(advanceFrom.isNullOrEmpty() and !advanceTo.isNullOrEmpty())
      advance = " ADVANCEAMOUNT < ${advanceTo} "

    var rental = ""
    if(!rentalFrom.isNullOrEmpty() and !rentalTo.isNullOrEmpty())
      rental = " RENTALAMOUNT BETWEEN ${rentalFrom} AND ${rentalTo} "
    if(!rentalFrom.isNullOrEmpty() and rentalTo.isNullOrEmpty())
      rental = " RENTALAMOUNT > ${rentalFrom} "
    if(rentalFrom.isNullOrEmpty() and !rentalTo.isNullOrEmpty())
      rental = " RENTALAMOUNT < ${rentalTo} "

    var mortage = ""
    if(!mortageFrom.isNullOrEmpty() and !mortageTo.isNullOrEmpty())
      mortage = " MORTAGEAMOUNT BETWEEN ${mortageFrom} AND ${mortageTo} "
    if(!mortageFrom.isNullOrEmpty() and mortageTo.isNullOrEmpty())
      mortage = " MORTAGEAMOUNT > ${mortageFrom} "
    if(mortageFrom.isNullOrEmpty() and !mortageTo.isNullOrEmpty())
      mortage = " MORTAGEAMOUNT < ${mortageTo} "

    var area = ""
    if(!areaFrom.isNullOrEmpty() and !areaTo.isNullOrEmpty())
      area = " AREA BETWEEN ${areaFrom} AND ${areaTo} "
    if(!areaFrom.isNullOrEmpty() and areaTo.isNullOrEmpty())
      area = " AREA > ${areaFrom} "
    if(areaFrom.isNullOrEmpty() and !areaTo.isNullOrEmpty())
      area = " AREA < ${areaTo} "

    val clauseArr = LazyStringArrayList()
    if(!advance.isBlank())
      clauseArr.add(advance)
    if(!rental.isBlank())
      clauseArr.add(rental)
    if(!mortage.isBlank())
      clauseArr.add(mortage)
    if(!area.isBlank())
      clauseArr.add(area)
    if(rentalHouseInfo.rentalType != RentalType.UNDEFINED)
      clauseArr.add(rentalType)
    if(rentalHouseInfo.houseType != HouseType.UNDEFINED)
      clauseArr.add(houseType)
    if(!rentalHouseInfo.address.isNullOrEmpty())
      clauseArr.add(address)
    if(!rentalHouseInfo.ownerName.isNullOrEmpty())
      clauseArr.add(owner)

    var query = "SELECT * FROM ${TABLE_NAME} "
    if(clauseArr.size == 1){
      query += " WHERE ${clauseArr[0]}"
    }
    else if(clauseArr.size > 1){
      query += " WHERE ${clauseArr[0]}"
      clauseArr.removeAt(0)
      for(clause in clauseArr){
        query += " AND ${clause}"
      }
    }

    do{
      try {
        val statement = connection.createStatement()
        val rs = statement.executeQuery(query)
        while(rs.next()){
          val hasReferrer = if(rs.getString("HASREFERRER") == "true") true else false
          val hasSeparateway = if(rs.getString("HASSEPARATEWAY") == "true") true else false
          val hasStorageRoom = if(rs.getString("HASSTORAGEROOM") == "true") true else false
          val hasParking = if(rs.getString("HASPARKING") == "true") true else false
          val hasCabinet = if(rs.getString("HASCABINET") == "true") true else false
          val hasWallDresser = if(rs.getString("HASWALLDRESSER") == "true") true else false

          val rentalHouseInfo = RentalHouseInfo(
            rs.getInt(("ID")),
            hasReferrer,
            rs.getInt("REFERRERID"),
            rs.getString("DATE"),
            rs.getString("ADDRESS"),
            rs.getDouble("AREA"),
            rs.getString("OWNERNAME"),
            rs.getString("OWNERPHONENUMBERS"),
            RentalType.toRentalType(rs.getString("RENTALTYPE")),
            rs.getDouble("ADVANCEAMOUNT"),
            rs.getDouble("RENTALAMOUNT"),
            rs.getDouble("MORTAGEAMOUNT"),
            rs.getDouble("LATITUDE"),
            rs.getDouble("LONGITUDE"),
            rs.getString("PICTURES"),
            rs.getInt("BUILDINGLEVELSCOUNT"),
            rs.getInt("LEVELNUMBER"),
            rs.getInt("BEDROOMSCOUNT"),
            hasSeparateway,
            hasStorageRoom,
            hasParking,
            hasCabinet,
            hasWallDresser,
            HouseType.toHouseType(rs.getString("HOUSETYPE")),
            rs.getString("DESCRIPTION")
            )
          result.add(rentalHouseInfo)
        }
        statement.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace() //TODO
        break
      }

    }while(false)

    DbManager.disConnect(connection)

    return result
  }

  fun update(obj: RentalHouseInfo, id: Int): Boolean {
    var result = false
    val c = DbManager.getConnection()
    val query =
      """
UPDATE ${TABLE_NAME} SET
HASREFERRER = '${obj.hasReferrer}',
REFERRERID = '${obj.referrerId}',
DATE = '${obj.date}',
ADDRESS = '${obj.address}',
AREA = '${obj.area}',
OWNERNAME = '${obj.ownerName}',
OWNERPHONENUMBERS = '${obj.ownerPhoneNumbers}',
LATITUDE = '${obj.latitude}',
LONGITUDE = '${obj.longitude}',
PICTURES = '${obj.pictures}',
HOUSETYPE = '${obj.houseType}',
RENTALTYPE = '${obj.rentalType}',
ADVANCEAMOUNT = '${obj.advanceAmount}',
RENTALAMOUNT = '${obj.rentalAmount}',
MORTAGEAMOUNT = '${obj.mortageAmount}',
BUILDINGLEVELSCOUNT = '${obj.buildingLevelsCount}',
LEVELNUMBER = '${obj.levelNumber}',
BEDROOMSCOUNT = '${obj.bedRoomsCount}',
HASSEPARATEWAY = '${obj.hasSeparateWay}',
HASSTORAGEROOM = '${obj.hasStorageRoom}',
HASPARKING = '${obj.hasParking}',
HASCABINET = '${obj.hasCabinet}',
HASWALLDRESSER = '${obj.hasWallDresser}',
DESCRIPTION = '${obj.description}'

WHERE ID = '${id}';
"""

    do{
      try {
        val sttmnt = c.createStatement()
        sttmnt.executeUpdate(query)
        sttmnt.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace() //TODO
        break
      }
      result = true
    }while(false)

    DbManager.disConnect(c)
    return result
  }

  fun updatePictures(pictures: String, id: Int): Boolean {
    var result = false
    val c = DbManager.getConnection()
    val query =
      """
UPDATE ${TABLE_NAME} SET
PICTURES = '${pictures}'
WHERE ID = '${id}';
"""
    do{
      try {
        val sttmnt = c.createStatement()
        sttmnt.executeUpdate(query)
        sttmnt.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace() //TODO
        break
      }
      result = true
    }while(false)

    DbManager.disConnect(c)
    return result
  }

  fun delete(id: Int): Boolean{
    var result = false
    val c = DbManager.getConnection()
    val query =
      """
DELETE FROM ${TABLE_NAME}
WHERE ID = '${id}';
"""

    do{
      try {
        val sttmnt = c.createStatement()
        sttmnt.executeUpdate(query)
        sttmnt.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace()
        break
      }
      result = true
    }while(false)

    return result
  }
}
