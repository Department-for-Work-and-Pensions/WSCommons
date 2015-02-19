import sbt._
import sbt.Keys._



object ApplicationBuild extends Build {
  val appName = "wsCommons"

  val appVersion = "2.3"

  val appDependencies = Seq(
    libraryDependencies += "com.typesafe.play" %% "play" % "2.3.8",
    libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.3.8"
  )

  var sR: Seq[Def.Setting[_]] = Seq(resolvers += "Carers repo" at "http://build.3cbeta.co.uk:8080/artifactory/repo/",
    resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/")

  var sAppN: Seq[Def.Setting[_]] = Seq(name := appName)
  var sAppV: Seq[Def.Setting[_]] = Seq(version := appVersion)
  var sOrg: Seq[Def.Setting[_]] = Seq(organization := "com.dwp.carers")

  var publ: Seq[Def.Setting[_]] = Seq(
    publishTo := Some("Artifactory Realm" at "http://build.3cbeta.co.uk:8080/artifactory/repo/"),
    publishTo <<= version {
      (v: String) =>
        Some("releases" at "http://build.3cbeta.co.uk:8080/artifactory/libs-release-local")
    })

  var creds: Seq[Def.Setting[_]] = Seq(credentials += Credentials("Artifactory Realm", "build.3cbeta.co.uk", "admin", "{DESede}GwYNYWCGg88uVuPjHixZ4g=="), isSnapshot := true)

  var appSettings: Seq[Def.Setting[_]] = publ ++ sAppN ++ sR ++ sAppV ++ sOrg ++ appDependencies ++ creds

  val main = Project(id = appName, base = file(".")).settings(appSettings: _*)

}
