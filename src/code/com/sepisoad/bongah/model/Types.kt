package com.sepisoad.bongah.model

import com.sepisoad.bongah.app.PROPS

enum class HouseType{
  UNDEFINED,
  RURAL_VILLA,
  URBAN_VILLA,
  URBAN_APARTMENT;

  override fun toString(): String{
    val value = when(this){
      RURAL_VILLA -> PROPS.getProperty("ruralVilla")
      URBAN_VILLA -> PROPS.getProperty("urbanVilla")
      URBAN_APARTMENT -> PROPS.getProperty("urbanApartment")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toHouseType(str: String): HouseType {
      when(str){
        PROPS.getProperty("ruralVilla") -> return RURAL_VILLA
        PROPS.getProperty("urbanVilla") -> return URBAN_VILLA
        PROPS.getProperty("urbanApartment") -> return URBAN_APARTMENT
        else -> return UNDEFINED
      }
    }
  }
}

enum class TerrainType{
  UNDEFINED,
  FLAT_LAND,
  MOUNTAIN_SIDE,
  COASTAL;

  override fun toString(): String{
    val value = when(this){
      FLAT_LAND -> PROPS.getProperty("flatLand")
      MOUNTAIN_SIDE -> PROPS.getProperty("mountainSide")
      COASTAL -> PROPS.getProperty("coastal")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toTerrainType(str: String): TerrainType {
      when(str){
        PROPS.getProperty("flatLand") -> return FLAT_LAND
        PROPS.getProperty("mountainSide") -> return MOUNTAIN_SIDE
        PROPS.getProperty("coastal") -> return COASTAL
        else -> return UNDEFINED
      }
    }
  }
}

enum class OwnershipType{
  UNDEFINED,
  ARENA,
  COMPLETE;

  override fun toString(): String{
    val value = when(this){
      ARENA -> PROPS.getProperty("arena")
      COMPLETE -> PROPS.getProperty("complete")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toOwnershipType(str: String): OwnershipType {
      when(str){
        PROPS.getProperty("arena") -> return ARENA
        PROPS.getProperty("complete") -> return COMPLETE
        else -> return UNDEFINED
      }
    }
  }
}

enum class RoofType {
  UNDEFINED,
  FREE,
  UNDER_BUILDING,
  INSIDE_BUILDING;

  override fun toString(): String{
    val value = when(this){
      FREE -> PROPS.getProperty("free")
      UNDER_BUILDING -> PROPS.getProperty("underBuilding")
      INSIDE_BUILDING -> PROPS.getProperty("insideBuilding")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object {
    fun toRoofType(str: String): RoofType {
      when (str) {
        PROPS.getProperty("free") -> return FREE
        PROPS.getProperty("underBuilding") -> return UNDER_BUILDING
        PROPS.getProperty("insideBuilding") -> return INSIDE_BUILDING
        else -> return UNDEFINED
      }
    }
  }
}

enum class DirectionType {
  UNDEFINED,
  NORTH,
  EAST,
  WEST,
  SOUTH,
  NORTH_EAST,
  NORTH_WEST,
  SOUTH_EAST,
  SOUTH_WEST;

  override fun toString(): String{
    val value = when(this){
      NORTH -> PROPS.getProperty("north")
      EAST -> PROPS.getProperty("east")
      WEST -> PROPS.getProperty("west")
      SOUTH -> PROPS.getProperty("south")
      NORTH_EAST -> PROPS.getProperty("northEast")
      NORTH_WEST -> PROPS.getProperty("northWest")
      SOUTH_EAST -> PROPS.getProperty("southEast")
      SOUTH_WEST -> PROPS.getProperty("southWest")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toDirectionType(str: String): DirectionType {
      when(str){
        PROPS.getProperty("north") -> return NORTH
        PROPS.getProperty("east") -> return EAST
        PROPS.getProperty("west") -> return WEST
        PROPS.getProperty("south") -> return SOUTH
        PROPS.getProperty("northEast") -> return NORTH_EAST
        PROPS.getProperty("northWest") -> return NORTH_WEST
        PROPS.getProperty("southEast") -> return SOUTH_EAST
        PROPS.getProperty("southWest") -> return SOUTH_WEST
        else -> return UNDEFINED
      }
    }
  }
}

enum class ApplicationType{
  UNDEFINED,
  AGRICULTURAL,
  COMMERCIAL,
  RESIDENTIAL;

  override fun toString(): String{
    val value = when(this){
      AGRICULTURAL -> PROPS.getProperty("agricultural")
      COMMERCIAL -> PROPS.getProperty("commercial")
      RESIDENTIAL -> PROPS.getProperty("residential")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toApplicationType(str: String): ApplicationType {
      when(str){
        PROPS.getProperty("agricultural") -> return AGRICULTURAL
        PROPS.getProperty("commercial") -> return COMMERCIAL
        PROPS.getProperty("residential") -> return RESIDENTIAL
        else -> return UNDEFINED
      }
    }
  }
}

enum class SindhType{
  UNDEFINED,
  ENDOWMENT,
  URBAN;

  override fun toString(): String{
    val value = when(this){
      ENDOWMENT -> PROPS.getProperty("endowment")
      URBAN -> PROPS.getProperty("urban")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toSindhType(str: String): SindhType {
      when(str){
        PROPS.getProperty("endowment") -> return ENDOWMENT
        PROPS.getProperty("urban") -> return URBAN
        else -> return UNDEFINED
      }
    }
  }
}

enum class RentalType {
  UNDEFINED,
  RENTAL,
  MORTAGE,
  BOTH;

  override fun toString(): String{
    val value = when(this){
      RENTAL -> PROPS.getProperty("rental")
      MORTAGE -> PROPS.getProperty("mortage")
      BOTH -> PROPS.getProperty("both")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toRentalType(str: String): RentalType {
      when(str){
        PROPS.getProperty("rental") -> return RENTAL
        PROPS.getProperty("mortage") -> return MORTAGE
        PROPS.getProperty("both") -> return BOTH
        else -> return UNDEFINED
      }
    }
  }
}

enum class LanguageType {
  UNDEFINED,
  ENGLISH,
  PERSIAN;

  override fun toString(): String{
    val value = when(this){
      ENGLISH -> PROPS.getProperty("englishLanguage")
      PERSIAN -> PROPS.getProperty("persianLanguage")
      else -> PROPS.getProperty("undefined")
    }
    return value
  }

  companion object{
    fun toLanguageType(str: String): LanguageType {
      when(str){
        PROPS.getProperty("englishLanguage") -> return ENGLISH
        PROPS.getProperty("persianLanguage") -> return PERSIAN
        else -> return UNDEFINED
      }
    }
  }
}