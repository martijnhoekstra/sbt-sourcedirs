sys.props.get("plugin.version") match {
  case Some(x) => addSbtPlugin("com.heroestools" % "sbt-sourcedirs" % x)
  case _       => sys.error("""|The system property 'plugin.version' is not defined.
                         |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}

addSbtPlugin("ch.epfl.lamp" % "sbt-dotty" % "0.5.1")
