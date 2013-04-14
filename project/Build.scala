import sbt._
import sbt.Keys._

object Build extends Build {

  lazy val aaPlugin = Project(
    id = "play2-ascii-art-plugin",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "play2-ascii-art-plugin",
      organization := "com.github.tototoshi",
      version := "0.1.1",
      scalaVersion := "2.10.0",
      scalacOptions ++= Seq("-feature"),
      resolvers ++= Seq(
        "typesafe" at "http://repo.typesafe.com/typesafe/releases"
      ),
      libraryDependencies ++= Seq(
        "play" %% "play" % "2.1.0" % "provided",
        "play" %% "play-test" % "2.1.0" % "test"
      )
    ) ++ publishingSettings
  )

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
