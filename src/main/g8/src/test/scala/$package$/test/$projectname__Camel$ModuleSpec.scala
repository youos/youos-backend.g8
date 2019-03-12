package $package$.test

import com.youos.test.lib.AbstractSystemSpec
import $package$.Main
import $package$.api.GetSomethingRequest

/**
  * @author TODO name of author, such that other users know the main contact person for this test (when it fails)
  */
class $projectname;format="Camel"$ModuleSpec extends AbstractSystemSpec(Main) with TestUsers {

  "GetSomethingRequest" should {
    "Require authentication" in {
      val getSomethingRequest = GetSomethingRequest("123")

      assertError("error.request.missing.parameter") {
        query(getSomethingRequest)
      }
    }

    "Return a valid response" in {
      val token = createRandomVerifiedUser("verified")

      val getSomethingRequest = GetSomethingRequest("123")
      val response = query(getSomethingRequest, token)

      assertResult(1)(response.somethings.size)
    }

  }
}
