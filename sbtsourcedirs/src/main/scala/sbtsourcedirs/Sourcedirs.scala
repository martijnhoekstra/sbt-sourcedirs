package sbtsourcedirs

import java.io.File
import semver4s._

import sbt._
import sbt.util.Logger
import sbt.Keys._

object Sourcedirs extends AutoPlugin {
  def semverDirs(
      scalaSource: File,
      scalaVersion: Version,
      allowAllPre: Boolean,
      log: Logger
  ): List[File] = {
    val basedir = scalaSource.getParentFile()
    val subdirs = basedir.listFiles(file =>
      file.isDirectory() && file.getName().take(5).equalsIgnoreCase("scala")
    )
    assert(subdirs != null, s"$basedir, parent of scala source $scalaSource does not exist")

    log.info(
      s"scanning for directories with semver ranges matching scala version ${scalaVersion.format}"
    )

    val preBehaviour = if (allowAllPre) PreReleaseBehaviour.Loose else PreReleaseBehaviour.Strict

    subdirs.toList.filter(file => {
      val pattern = file.getName().drop(5).dropWhile(_ == '-')
      pattern == "" || {
        val parsed = parseMatcher(pattern)

        if (parsed.isLeft) {
          log.warn(s"directory $file with invalid semver range pattern $pattern ignored")
        }

        def matches(matcher: Matcher) = {
          val result =
            if (allowAllPre) matcher.matches(scalaVersion, preBehaviour)
            else matcher.matches(scalaVersion)
          if (result) log.info(s"Including $file for scala version ${scalaVersion.format}")
          else log.info(s"excluding $file for scala version ${scalaVersion.format}")
          result
        }

        parsed.toOption.map(matches).getOrElse(false)
      }
    })
  }

  object autoImport {
    val sourcedirsMatchPrereleases =
      settingKey[Boolean]("pre-release versions can match source directory patterns")
  }
  import autoImport._

  override def requires = sbt.plugins.JvmPlugin

  // This plugin is automatically enabled for projects which are JvmPlugin.
  override def trigger = allRequirements

  override lazy val globalSettings: Seq[Setting[_]] = List(sourcedirsMatchPrereleases := true)

  // a group of settings that are automatically added to projects.
  override val projectSettings =
    inConfig(Compile)(List(unmanagedSourceDirectories := {
      parseVersion(scalaVersion.value).toOption.toList
        .flatMap(vv =>
          semverDirs(scalaSource.value, vv, sourcedirsMatchPrereleases.value, sLog.value)
        )
    })) ++
      inConfig(Test)(List(unmanagedSourceDirectories := {
        parseVersion(scalaVersion.value).toOption.toList
          .flatMap(vv =>
            semverDirs(
              scalaSource.value.getParentFile(),
              vv,
              sourcedirsMatchPrereleases.value,
              sLog.value
            )
          )
      }))

}
