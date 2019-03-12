
package $package$

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.youos.module.UserModules
import com.youos.module.openapi.OpenApiModule
import com.youos.module.permission.PermissionModuleActor
import com.youos.module.rest.RestServiceModule
import com.youos.module.sendemail.SendEmailModule
import com.youos.service.AbstractMain
import com.youos.session.SessionModuleActor

import $package$.module.$projectname;format="norm"$.$projectname;format="Camel"$Module

object Main extends AbstractMain {
  def main(args: Array[String]): Unit = {
    setup()
  }

  override def setup(configOverrides: Config): Unit = {
    Main.start(configOverrides)
      .addModule[OpenApiModule]() // needs to be before other modules because of custom type additions
      .addModule[SessionModuleActor]()
      .addModule[RestServiceModule]()
      .addModule[SendEmailModule]()
      .addModule[PermissionModuleActor]()
      .addModules(UserModules())
      .addModule[$projectname;format="Camel"$Module]()
  }

  override def testSetup(configOverrides: Config): ActorSystem = {
    Main.start(configOverrides)
      .addModule[SessionModuleActor]()
      .addModule[SendEmailModule]()
      .addModule[PermissionModuleActor]()
      .addModules(UserModules())
      .addModule[$projectname;format="Camel"$Module]()

    system
  }
}
