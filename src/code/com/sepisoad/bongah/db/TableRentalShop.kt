package com.sepisoad.bongah.db

import com.sepisoad.bongah.model.RentalShopInfo
import com.sepisoad.bongah.model.RentalType
import com.sepisoad.bongah.model.RoofType
import java.sql.SQLException
import kotlin.reflect.jvm.internal.impl.com.google.protobuf.LazyStringArrayList

object TableRentalShop {
  final val TABLE_NAME = "RENTALSHOP"

  fun insert(obj: RentalShopInfo): Int {
    var result = -1
    val c = DbManager.getConnection()
    val query =
      """
INSERT INTO ${TABLE_NAME}
(HASREFERRER, REFERRERID, DATE, ADDRESS, AREA, OWNERNAME,
 OWNERPHONENUMBERS, LATITUDE, LONGITUDE, PICTURES,
 ROOFTYPE , RENTALTYPE, ADVANCEAMOUNT , RENTALAMOUNT , MORTAGEAMOUNT ,
 PHONENUMBERS, HASPHONE, HASWATER, HASELECTRICITY, HASGAS,
 DESCRIPTION) VALUES
('${obj.hasReferrer}', '${obj.referrerId}', '${obj.date}', '${obj.address}', '${obj.area}', '${obj.ownerName}',
 '${obj.ownerPhoneNumbers}', '${obj.latitude}', '${obj.longitude}', '${obj.pictures}',
 '${obj.roofType}', '${obj.rentalType}','${obj.advanceAmount}', '${obj.rentalAmount}', '${obj.mortageAmount}',
 '${obj.phoneNumbers}', '${obj.hasPhone}', '${obj.hasWater}', '${obj.hasElectricity}', '${obj.hasGas}',
 '${obj.description}');
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

  fun find(rentalShopInfo: RentalShopInfo,
           advanceFrom: String?, advanceTo: String?,
           rentalFrom: String?, rentalTo: String?,
           mortageFrom: String?, mortageTo: String?,
           areaFrom: String?, areaTo: String?)
    : MutableList<RentalShopInfo>
  {
    var result: MutableList<RentalShopInfo> = mutableListOf()

    val connection = DbManager.getConnection()
    val roofType = " ROOFTYPE = '${rentalShopInfo.roofType}' "
    val rentalType = "RENTALTYPE = '${rentalShopInfo.rentalType}'"
    val address = " ADDRESS LIKE '%${rentalShopInfo.address}%' "
    val owner = " OWNERNAME LIKE '%${rentalShopInfo.ownerName}%' "

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
    if(rentalShopInfo.rentalType != RentalType.UNDEFINED)
      clauseArr.add(rentalType)
    if(rentalShopInfo.roofType != RoofType.UNDEFINED)
      clauseArr.add(roofType)
    if(!rentalShopInfo.address.isNullOrEmpty())
      clauseArr.add(address)
    if(!rentalShopInfo.ownerName.isNullOrEmpty())
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
          val hasPhone= if(rs.getString("HASPHONE") == "true") true else false
          val hasWater = if(rs.getString("HASWATER") == "true") true else false
          val hasGas = if(rs.getString("HASGAS") == "true") true else false
          val hasElectricity = if(rs.getString("HASELECTRICITY") == "true") true else false

          val rentalShopInfo = RentalShopInfo(
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
            rs.getString("PHONENUMBERS"),
            hasPhone,
            hasWater,
            hasElectricity,
            hasGas,
            RoofType.toRoofType(rs.getString("ROOFTYPE")),
            rs.getString("DESCRIPTION")
          )
          result.add(rentalShopInfo)
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

  fun update(obj: RentalShopInfo, id: Int): Boolean {
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
ROOFTYPE = '${obj.roofType}',
RENTALTYPE = '${obj.rentalType}',
ADVANCEAMOUNT = '${obj.advanceAmount}',
RENTALAMOUNT = '${obj.rentalAmount}',
MORTAGEAMOUNT = '${obj.mortageAmount}',
PHONENUMBERS = '${obj.phoneNumbers}',
HASPHONE = '${obj.hasPhone}',
HASWATER = '${obj.hasWater}',
HASELECTRICITY = '${obj.hasElectricity}',
HASGAS = '${obj.hasGas}',
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
