package $package$.test

import com.youos.common.YouosConfig
import com.youos.test.lib.YouosSystemQueryLike
import com.youos.types.DeviceType
import com.youos.user.account.{CreateAccountRoleRequest, LoginRequest, RegisterAccountRequest}

import scala.util.Random

/**
  * @author salim
  */
trait TestUsers extends YouosSystemQueryLike {

  val DEVICE_TYPE = DeviceType.BROWSER
  val DEVICE_NAME = "test-client"
  val PASSWORD = "password"

  /** Create a new guest user and return its login. */
  def createGuestUser(): String = {
    val registerResponse = query(RegisterAccountRequest(languageCode = YouosConfig.defaultLanguageCode))
    query(LoginRequest(accountId = registerResponse.accountId, refreshToken = registerResponse.refreshToken,
      deviceType = DEVICE_TYPE, deviceName = DEVICE_NAME)).token
  }

  /** Create a new random verified user and return its login. */
  def createRandomVerifiedUser(additionalRoles: String*): String = {
    val emailAddress: String = Random.alphanumeric.take(24).dropWhile(_.isDigit).mkString + "@example.com"
    val registerResponse = query(RegisterAccountRequest(emailAddress, languageCode = YouosConfig.defaultLanguageCode, password = PASSWORD))

    for (role <- additionalRoles) {
      val request = CreateAccountRoleRequest(registerResponse.accountId, role)
      request.ecosystem = "default"
      request.token = "does-not-matter"
      internalQuery(request)
    }

    query(LoginRequest(email = emailAddress, verificationKey = registerResponse.verificationKey,
      deviceType = DEVICE_TYPE, deviceName = DEVICE_NAME)).token
  }

  /** Login admin user. */
  def loginAdminUser(): String = {
    query(LoginRequest(email = "admin@example.com", password = "admin",
      deviceType = DEVICE_TYPE, deviceName = DEVICE_NAME)).token
  }
}
