lazy val root = (project in file("."))
  .settings(
    version := "0.1",
    scalaVersion := "2.12.6",
    crossScalaVersions := List("2.12.8", "2.13.3", "3.0.0-M3")
  )
