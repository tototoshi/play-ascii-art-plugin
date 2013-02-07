import sbt._
import sbt.Keys._

object Build extends Build {

  lazy val aaPlugin = Project(
    id = "play2-ascii-art-plugin",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "play2-ascii-art-plugin",
      organization := "com.github.tototoshi",
      version := "0.1.0",
      scalaVersion := "2.10.0",
      scalacOptions ++= Seq("-feature"),
      resolvers ++= Seq(
        "typesafe" at "http://repo.typesafe.com/typesafe/releases"
      ),
      libraryDependencies ++= Seq(
        "play" %% "play" % "2.1.0" % "provided",
        "play" %% "play-test" % "2.1.0" % "test"
      )
    )
  )
}
