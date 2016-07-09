package com.sepisoad.bongah.db

import java.sql.SQLException

object TableLogin {
  final val TABLE_NAME = "LOGIN"

  fun insert_single_recrod(){
    val c = DbManager.getConnection()
    val q =
"""
INSERT INTO ${TABLE_NAME} (PASSWORD) VALUES("EMPTY")
"""
    do{
      try {
        val sttmnt = c.createStatement()
        val rs = sttmnt.executeUpdate(q);
        sttmnt.closeOnCompletion()
      }catch(ex: SQLException) {
        ex.printStackTrace() //TODO
        break
      }
    }while(false)

    DbManager.disConnect(c)
  }

  fun get(): String{
    var result = ""
    val c = DbManager.getConnection()
    val q =
"""
SELECT PASSWORD FROM ${TABLE_NAME} WHERE
ID = '${1}'
"""
    do{
      try {
        val sttmnt = c.createStatement()
        val rs = sttmnt.executeQuery(q)
        while(rs.next()){
          result = rs.getString("PASSWORD");
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

  fun update(password: String): Boolean {
    var result = false
    val c = DbManager.getConnection()
    val q =
      """
UPDATE ${TABLE_NAME} SET
PASSWORD = '${password}',
WHERE ID = '${1}';
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