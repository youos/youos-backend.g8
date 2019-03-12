package $package$.module.$projectname;format="norm"$

import com.youos.actor.{AbstractModuleActor, EventHandler, RequestHandler, YouosModule}
import com.youos.user.profile.GetProfilesRequest
import com.youos.user.account.AccountProfilesChangedEvent
import $package$.api.{GetSomethingRequest, GetSomethingResponse}

import scala.concurrent.Future
import scala.collection.JavaConverters

/**
  * @author TODO name of author, such that other users know the main contact person for this module
  */
@YouosModule("$projectname;format="norm"$")
class $projectname;format="Camel"$Module extends AbstractModuleActor {
  import context.dispatcher // ExecutionContext for Futures

  private var demoConfiguration: String = ""

  override def preStart(): Unit = {
    super.preStart()

    demoConfiguration = moduleConfig.getString("demoConfiguration")
  }

  @RequestHandler
  def handleGetSomethingRequest(request: GetSomethingRequest): Future[GetSomethingResponse] = {
    // the pipe forwards session / authorization parameters
    query(request | GetProfilesRequest())
      .map(r => JavaConverters.asScalaBuffer(r.profiles).map(x => x.publicName))
      .map(GetSomethingResponse(_))
  }

  @EventHandler
  def onAccountProfilesChangedEvent(event: AccountProfilesChangedEvent): Unit = {
    log.debug(demoConfiguration + " something happened!!! " + event.getAccountId)
  }

}