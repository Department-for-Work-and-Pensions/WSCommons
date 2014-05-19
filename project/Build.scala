import sbt._
import sbt.Keys._


object ApplicationBuild extends Build {
  val appName = "wsCommons"

  val appVersion = "1.0"

  var sAppN: Seq[Def.Setting[_]] = Seq(name := appName)
  var sAppV: Seq[Def.Setting[_]] = Seq(version := appVersion)
  var sOrg: Seq[Def.Setting[_]] = Seq(organization := "com.dwp.carers")

  var publ: Seq[Def.Setting[_]] = Seq(
    publishTo := Some("Artifactory Realm" at "http://build.3cbeta.co.uk:8080/artifactory/repo/"),
    publishTo <<= version {
      (v: String) =>
        Some("releases" at "http://build.3cbeta.co.uk:8080/artifactory/libs-release-local")
    })

  val main = Project(id = appName, base = file("."), settings = publ++sAppN ++ sAppV ++ sOrg)
}
