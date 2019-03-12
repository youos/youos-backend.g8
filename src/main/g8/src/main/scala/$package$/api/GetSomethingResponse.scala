package $package$.api

import com.youos.requests.AbstractResponse
import com.youos.requests.openapi.Doc

case class GetSomethingResponse
(
  @Doc("TODO")
  somethings: Seq[String],

) extends AbstractResponse {

}
