import xerial.sbt.Sonatype._

ThisBuild / version := "0.1.1"
ThisBuild / scalaVersion := "2.12.13"
ThisBuild / organization := "com.heroestools"
ThisBuild / licenses := Seq("GPLv3" -> url("https://www.gnu.org/licenses/gpl-3.0.en.html"))
ThisBuild / developers := List(
  Developer(
    id = "Martijn",
    name = "Martijn Hoekstra",
    email = "martijnhoekstra@gmail.com",
    url = url("https://www.github.com")
  )
)

lazy val sbtsourcedirs = project
  .in(file("sbtsourcedirs"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-sourcedirs",
    description := "npm-style matchers for scala version dependent sources",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++ List("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    libraryDependencies += "com.heroestools" %% "semver4s" % "0.1.0",
    sonatypeProjectHosting := Some(
      GitHubHosting("martijnhoekstra", "sbt-sourcedirs", "martijnhoekstra@gmail.com")
    ),
    publishTo := sonatypePublishToBundle.value
  )
