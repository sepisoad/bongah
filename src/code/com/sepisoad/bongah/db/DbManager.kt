package com.sepisoad.bongah.db

import com.sepisoad.bongah.app.APP_HOME_PATH
import com.sepisoad.bongah.app.DB_QUERY_TIMEOUT
import com.sepisoad.bongah.app.RES_SQL_CREATE_TABLES
import com.sepisoad.bongah.app.RES_SQL_DROP_TABLES
import com.sepisoad.bongah.ui.FxApp
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*

object DbManager{
  private lateinit var dbDriver: String
  private lateinit var dbUrl: String
  private lateinit var dbUsername: String
  private lateinit var dbPassword: String
  fun init(driverClass: String, driverPath: String, url: String, username: String, password: String): Boolean {
    var result = false

    do{
      dbDriver = driverClass
      dbUrl = driverPath + url
      dbUsername = username
      dbPassword = password

      Class.forName(dbDriver)

        if(!dbExists(url))
          if(!createDb())
            break
      result = true
    }while(false)

    return result
  }

  fun getConnection(): Connection {
    return DriverManager.getConnection(dbUrl, dbUsername, dbPassword)
  }

  fun disConnect(connection: Connection): Unit {
    try {
      connection.close()
    }catch(ex: SQLException){
      ex.printStackTrace() // TODO
    }
  }

  fun dbExists(url: String): Boolean {
    var result = false

    do{
      val dbFile = File(url)
      if(!dbFile.exists())
        break
      result = true
    }while(false)

    return result
  }

  fun createDb(): Boolean {
    var result: Boolean = false

    do{
      val c = getConnection()
      try {
        val sttmnt = c.createStatement()
        sttmnt?.queryTimeout = DB_QUERY_TIMEOUT
        val query = loadCreateTablesQuery()
        sttmnt?.executeUpdate(query)
        TableLogin.insert_single_recrod()
      }catch(ex: SQLException){
        ex.printStackTrace()
        break
      }finally{
        disConnect(c)
      }

      result = true
    }while(false)

    return result
  }

  fun loadCreateTablesQuery(): String{
    var result = ""
    val cl = FxApp::class.java.classLoader
    val inStream = cl.getResourceAsStream(RES_SQL_CREATE_TABLES)

    val reader = BufferedReader(InputStreamReader(inStream))
    while(true) {
      val line = reader.readLine() ?: break
      result += line
    }

    return result
  }
}
