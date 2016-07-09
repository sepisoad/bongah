package com.sepisoad.bongah.app

class AppBaseException(msg: String) : Exception(msg){
}

class AppExAddressNotSet : Exception(){
  override val message: String?
    get() = "address field is not set"
}

class AppExOwnerNotSet : Exception(){
  override val message: String?
    get() = "owner field is not set"
}

class AppExInvalidPriceFormat : Exception(){
  override val message: String?
    get() = "price format is not valid"
}

class AppExInvalidAreaFormat : Exception(){
  override val message: String?
    get() = "price format is not valid"
}
