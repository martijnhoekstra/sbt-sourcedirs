import xerial.sbt.Sonatype._

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.13"
ThisBuild / organization := "com.heroestools"
ThisBuild / licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / developers := List(
  Developer(id="Martijn", name="Martijn Hoekstra", email="martijnhoekstra@gmail.com", url=url("https://www.github.com"))
)

lazy val sbtPlugin = project.in(file("sbtsourcedirs"))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "sbt-sourcedirs",
    description := "npm-style matchers for scala version dependent sources",
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++ List("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
    libraryDependencies += "com.heroestools" %% "semver4s" % "0.1.0",
    publishTo := sonatypePublishToBundle.value,
    sonatypeProjectHosting := Some(GitHubHosting("martijnhoekstra", "sbt-sourcedirs", "martijnhoekstra@gmail.com"))
  )
