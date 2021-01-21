## sbt-sourcedirs

version-dependent source directories for sbt. e.g.

* projectdir/src/main/scala<2.13 will be used for scala versions below 2.13.0
* projectdir/src/main/scala>=2.13 will be used for scala versions 2.13.0 and later
* projectdir/src/main/scala^2.13.2 will be used for scala 2.13.2 <= version < 3.0.0
* projectdir/src/main/scala>=3.0.0-M2 < 3.0.0-RC will be used for all dotty milestone releases after 2 (but not RCs)

### status

unpublished

### installation

in your build.sbt:

```scala
addSbtPlugin("com.heroestools" %% "sbt-sourcedirs" % "0.1.0")
```

### features

If your normal scala directory is in project/src/main/scala, then sbt automatically
makes projectdir/src/main/scala-2.12 and projectdir/scr/main/scala-2.13 available
for version-specific code.

This plugin adds semver ranges to the mix. Any directory scala$range or scala-$range
where range is an npm-style semver range as described in
https://docs.npmjs.com/cli/v6/using-npm/semver#ranges will be used if the scala
version matches the range.

The most common usage is splitting <=2.12 and >=2.13 to have one directory
for everything below 2.13.0, and one for 2.13 and the 3 series. If/when scala 3.1
will have its own stdlib and you still need 2.12, then maybe that will become
"<=2.12", "2.13 - 3.1" and "3.1"

### settings

The plugin exposes the boolean setting `sourcedirsMatchPrereleases`, which, by default
is true, so that >=2.13 also matches the milestone and RC releases of upcoming versions.

You can set it to false if you don't want to match prerelease tags unless they're
explicitly part of the range.

Example

sourcedirsMatchPrereleases := false

//now projectdir/src/main/scala>2.13.0 will no longer match scala 3.0.0-RC1


### limitations

Creating new directories after loading your project in sbt doesn't automatically
add them until you reload the project.