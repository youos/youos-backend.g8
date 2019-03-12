package $package$.api

import com.youos.requests.openapi._
import com.youos.requests.AbstractAuthenticatedClientRequest
import com.youos.types.PagingOptions

@Doc("TODO this documentation appears in swagger / open-api.")
@GET(value = "something", permission = "something.get")
case class GetSomethingRequest
(
  @Doc("TODO ID of something")
  @Param(required = true)
  id: String,

) extends AbstractAuthenticatedClientRequest {

  override type ResponseType = GetSomethingResponse

  override def responseClassTag = reflect.classTag
}
