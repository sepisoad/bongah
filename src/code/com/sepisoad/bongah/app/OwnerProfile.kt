package com.sepisoad.bongah.app

import jodd.io.FileUtil
import jodd.json.JsonParser
import java.math.BigInteger
import java.net.InetAddress
import java.net.NetworkInterface
import java.security.InvalidKeyException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

class OwnerProfile {
  var name = ""
  var address = ""
  var telephone = ""
  var mobile = ""
  var email = ""
  var mac = ""
  var application = ""
  var version = ""
}

object OwnerProfileManager{
  fun initAppOwnerObject(appOwner: String): Boolean {
    var result = false

    do{
      val jsonParser = JsonParser()
      val obj = jsonParser.parse(appOwner, OwnerProfile::class.java)
      if(obj == null)
        break

      result = true
    }while(false)

    return result
  }

  @Throws(ClassCastException::class)
  fun decryptProfile(profilePath: String, decryptionKey: String): OwnerProfile{
    var op = OwnerProfile();
    val encryptedProfileData: String = loadProfileDataAsString(profilePath)

    if (encryptedProfileData.isNullOrEmpty())
      return op

    val rawOwnerProfileString = decryptString(encryptedProfileData, decryptionKey);
    if(rawOwnerProfileString.isNullOrEmpty())
      return op


    val jp = JsonParser()
    val opMap: Map<String, String> = jp.parse(rawOwnerProfileString)

    op.mac = opMap.get("name") ?: ""
    op.address = opMap.get("address") ?: ""
    op.telephone = opMap.get("telephone") ?: ""
    op.mobile = opMap.get("mobile") ?: ""
    op.application = opMap.get("application") ?: ""
    op.mac = opMap.get("mac") ?: ""
    op.version = opMap.get("version") ?: ""
    op.email = opMap.get("email") ?: ""

    return op
  }

  @Throws(NoSuchPaddingException::class, NoSuchAlgorithmException::class, InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
  fun decryptString(input: String, key: String): String {
    var result: String? = null
    val keyByteArray = key.toByteArray()
    val secretKeySpec = SecretKeySpec(keyByteArray, "Blowfish")
    val cipher = Cipher.getInstance("Blowfish")
    val encryptedInput = BigInteger(input, 16)
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
    val decodedBytes = cipher.doFinal(encryptedInput.toByteArray())
    result = String(decodedBytes)
    return result
  }

  fun loadProfileDataAsString(fileName: String): String {
    var result = FileUtil.readString(fileName)
    return result
  }

  fun getDigest(input: String): String{
    val md5 = MessageDigest.getInstance("MD5");
    md5.reset()
    md5.update(input.toByteArray())
    val digest = md5.digest()
    var bigInt = BigInteger(1, digest);
    var result = bigInt.toString(16);
    while(result.length < 32 ){
      result = "0" + result;
    }

    return result;
  }

  fun getSystemMacAddress(): String {
    var result = "";
    val ip = InetAddress.getLocalHost()
    val ni = NetworkInterface.getByInetAddress(ip);
    val mac = ni.hardwareAddress
    val sb = StringBuilder();
    for((i, v) in mac.withIndex()){
      result += String.format("%02X:", mac[i]);
    }
    result = result.substring(0, result.lastIndex)

    return result;
  }
};