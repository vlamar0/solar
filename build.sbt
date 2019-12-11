name := """solar"""
organization := "rtc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  ws,
  guice,
  "org.typelevel"          %% "cats-core"             % "1.6.1",
)
