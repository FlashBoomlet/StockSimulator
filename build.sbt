import sbt.Keys.javaOptions

// scalastyle:off

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

mainClass in(Compile, run) := Some("com.flashboomlet.Driver")

lazy val root =
  (project in file(".")).aggregate(
    goldWraithCore
  )

lazy val commonSettings = Seq(
  organization := "com.flashboomlet",
  scalaVersion := "2.11.8",
  resolvers ++= Seq(
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
    "Typesafe Releases" at "https://repo.typesafe.com/typesafe/maven-releases/",
    "Maven central" at "http://repo1.maven.org/maven2/",
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "scalac repo" at "https://raw.githubusercontent.com/ScalaConsultants/mvn-repo/master/"
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" % "akka-actor_2.11" % "2.4.10",
    "io.scalac" %% "slack-scala-bot-core" % "0.2.1",
    "com.typesafe" % "config" % "1.3.0",
    "org.reactivemongo" %% "reactivemongo" % "0.11.14",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    "org.scalaj" %% "scalaj-http" % "2.3.0",
    "org.scala-lang.modules" %% "scala-swing" % "1.0.1",
    "org.scalanlp" %% "breeze" % "0.12",
    "org.scalanlp" %% "breeze-natives" % "0.12",
    "org.scalanlp" %% "breeze-viz" % "0.12",
    "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.4",
    "com.github.tototoshi" %% "scala-csv" % "1.3.3"
  )
)


lazy val goldWraithCore = (project in file("goldWraithCore"))
.settings(commonSettings: _*)
.settings(
  name := "goldWraithCore",
  version := "0.1.0",
  javaOptions += "-Dlogback.configurationFile=../goldWraithCore/src/main/resources/logback.xml")

