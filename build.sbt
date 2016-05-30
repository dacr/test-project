name := "test-project"

organization := "testproject"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-unchecked", "-deprecation" , "-feature", "-language:implicitConversions")

mainClass in assembly := Some("testproject.TestProject")

jarName in assembly := "testproject.jar"

libraryDependencies ++= Seq(
  "testdep" %% "test-dep" % "0.2-SNAPSHOT"
)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "junit" % "junit" % "4.12" % "test"
)

initialCommands in console := """import dummy._"""

sourceGenerators in Compile <+=
 (sourceManaged in Compile, version, name, jarName in assembly) map {
  (dir, version, projectname, jarexe) =>
  val file = dir / "testproject" / "MetaInfo.scala"
  val buildDate = (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new java.util.Date())
  IO.write(file,
  """package testproject
    |object MetaInfo { 
    |  val version="%s"
    |  val project="%s"
    |  val jarbasename="%s"
    |  val buildDate="%s"
    |}
    |""".stripMargin.format(version, projectname, jarexe.split("[.]").head, buildDate) )
  Seq(file)
}
