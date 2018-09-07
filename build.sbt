name := "hiveUDFs"

version := "0.1"

scalaVersion := "2.12.6"

resolvers ++= Seq(
  "Cloudera" at "https://repository.cloudera.com/content/repositories/releases/",
  "Cloudera plugins" at "http://repository.cloudera.com/cloudera/cloudera-repos/")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5",
  "org.apache.hive" % "hive-exec" % "1.1.0-cdh5.7.0" % "provided",
  "org.apache.hadoop" % "hadoop-common" % "2.6.0-cdh5.7.0"  % "provided"
)

assemblyJarName in assembly := "customUfds.jar"