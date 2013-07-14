import com.typesafe.sbt.SbtScalariform.scalariformSettings
import sbt._
import sbt.Keys._

object Build extends Build {

  lazy val _version = "0.2.0-SNAPSHOT"
  lazy val _scalaVersion = "2.10.0"
  lazy val _playVersion = "2.1.1"

  lazy val plugin = Project(
    id = "play2-ascii-art-plugin",
    base = file("plugin"),
    settings = Project.defaultSettings ++ Seq(
      name := "play2-ascii-art-plugin",
      organization := "com.github.tototoshi",
      version := _version,
      scalaVersion := _scalaVersion,
      scalacOptions ++= Seq("-feature"),
      resolvers ++= Seq(
        "typesafe" at "http://repo.typesafe.com/typesafe/releases"
      ),
      libraryDependencies ++= Seq(
        "play" %% "play" % _playVersion % "provided",
        "play" %% "play-test" % _playVersion % "test"
      )
    ) ++ publishingSettings ++ scalariformSettings
  )

  lazy val _testAppName = "test-app"
  lazy val _testAppVersion = _playVersion
  lazy val _testAppDependencies = Seq()

  val testapp =
    play.Project(
      _testAppName,
      _testAppVersion,
      _testAppDependencies,
      path = file("testapp")
    ).settings(scalariformSettings:_*)
  .settings(resourceDirectories in Test <+= baseDirectory / "conf")
  .dependsOn(plugin)
  .aggregate(plugin)

  val publishingSettings = Seq(
    publishMavenStyle := true,
    publishTo <<= version { (v: String) => _publishTo(v) },
    publishArtifact in Test := false,
    pomExtra := _pomExtra
  )

  def _publishTo(v: String) = {
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots")
    else Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }

  val _pomExtra =
    <url>http://github.com/tototoshi/play-ascii-art-plugin</url>
    <licenses>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:tototoshi/play-ascii-art-plugin.git</url>
      <connection>scm:git:git@github.com:tototoshi/play-ascii-art-plugin.git</connection>
    </scm>
    <developers>
      <developer>
        <id>tototoshi</id>
        <name>Toshiyuki Takahashi</name>
        <url>http://tototoshi.github.com</url>
      </developer>
    </developers>

}
