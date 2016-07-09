package com.sepisoad.bongah.db

import com.sepisoad.bongah.model.ColleagueInfo
import javafx.beans.property.IntegerProperty
import javafx.beans.property.StringProperty
import jodd.db.DbSession
import jodd.db.oom.DbOomQuery
import java.sql.SQLException

object TableColleague {
  final val TABLE_NAME = "COLLEAGUE"

  fun insert(obj: ColleagueInfo): Boolean {
    var result = false
    val c = DbManager.getConnection()
    val q =
"""
INSERT INTO ${TABLE_NAME} (NAME, PHONENUMBERS, ADDRESS, DESCRIPTION)
VALUES('${obj.name}', '${obj.phoneNumbers}', '${obj.address}', '${obj.description}');
"""
    do{
      try {
        val sttmnt = c.createStatement()
        sttmnt.executeUpdate(q)
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

  fun find(name: String, phone: String): MutableList<ColleagueInfo>{
    var result: MutableList<ColleagueInfo> = mutableListOf()
    val c = DbManager.getConnection()
    val q =
"""
SELECT * FROM ${TABLE_NAME} WHERE
NAME LIKE '%${name}%' OR
PHONENUMBERS = '${phone}ک'
"""
    do{
      try {
        val sttmnt = c.createStatement()
        val rs = sttmnt.executeQuery(q)
        while(rs.next()){
          val o = ColleagueInfo(
            rs.getInt("ID"),
            rs.getString("NAME"),
            rs.getString("PHONENUMBERS"),
            rs.getString("ADDRESS"),
            rs.getString("DESCRIPTION")
          )
          result.add(o)
        }
        sttmnt.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace() //TODO
        break
      }

    }while(false)

    DbManager.disConnect(c)
    return result
  }

  fun update(obj: ColleagueInfo, id: Int): Boolean {
    var result = false
    val c = DbManager.getConnection()
    val q =
"""
UPDATE ${TABLE_NAME} SET
NAME = '${obj.name}',
PHONENUMBERS = '${obj.phoneNumbers}',
ADDRESS = '${obj.address}',
DESCRIPTION = '${obj.description}'
WHERE ID = '${id}';
"""
    do{
      try {
        val sttmnt = c.createStatement()
        sttmnt.executeUpdate(q)
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
}