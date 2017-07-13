name := "scala-simple-jsonparser"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "com.typesafe.play" % "play-json_2.11" % "2.3.4",
    "org.scala-lang" % "scala-reflect" % "2.12.2"
)