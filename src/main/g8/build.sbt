
ThisBuild / version := "1.0.0"
ThisBuild / organization := "$package$"
ThisBuild / scalaVersion := "2.12.6"

lazy val akkaVersion = "2.5.21"
lazy val akkaHttpVersion = "10.1.7"
lazy val youosVersion = "1.0.4"

name := "$name$"

ThisBuild / resolvers += "Artifactory Snapshots" at "https://artifacts.dev.inspired.ag/artifactory/youos-snapshots/"
ThisBuild / resolvers += "Artifactory Releases" at "https://artifacts.dev.inspired.ag/artifactory/youos-releases/"
ThisBuild / credentials += {
  (sys.env.get("ARTIFACTORY_USER"), sys.env.get("ARTIFACTORY_PASSWORD")) match {
    case (Some(user), Some(password)) => Credentials("Artifactory Realm", "artifacts.dev.inspired.ag", user, password)
    case _ => Credentials(new File(ivyPaths.value.ivyHome.getOrElse(new File("~/.ivy2")).getPath + "/.credentials"))
  }
}

lazy val root = (project in file("."))
  .settings(
    libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    libraryDependencies += "com.typesafe" % "config" % "1.3.3",
    libraryDependencies += "com.youos" %% "service-framework" % youosVersion,
    libraryDependencies += "com.youos" %% "user-modules" % youosVersion,
    libraryDependencies += "com.youos" %% "youos-test" % youosVersion % Test,

    mainClass in Compile := Some("$package$.Main"),

    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs@_*) => MergeStrategy.discard
      case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
      case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
      case "application.conf" => MergeStrategy.concat
      case "reference.conf" => MergeStrategy.concat
      case "permission.conf" => MergeStrategy.concat
      case "unwanted.txt" => MergeStrategy.discard
      case _ => MergeStrategy.first
    },
    assemblyJarName in assembly := "$name$.jar",

    test in assembly := {}, // skip tests
    parallelExecution in Test := false,
  )
