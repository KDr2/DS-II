import sbt.Keys._

resolvers += Resolver.typesafeIvyRepo("releases")

lazy val root = (project in file(".")).settings(
  name := "ScalaExplore",
  version := "0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= LibDepends.sbtDepends,
  libraryDependencies ++= LibDepends.basicDepends
).aggregate(s1, jst, chewbacca) // make watchSources work for the sub--projects


lazy val s1 = (project in file("scala1")).settings(
  name := "Scala1",
  version := "0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= LibDepends.basicDepends,
  mainClass in(Compile, run) := Some("com.kdr2.scala0.Main")
)

lazy val jst = (project in file("scalajst")).settings(
  name := "ScalaJST",
  version := "0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= LibDepends.libs(Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.0",
    "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
    "org.singlespaced" %%% "scalajs-d3" % "0.3.3"
  )),
  libraryDependencies ++= LibDepends.basicDepends,
  skip in packageJSDependencies := false,
  jsDependencies ++= Seq(
    "org.webjars" % "jquery" % "2.1.4" / "2.1.4/jquery.js" minified "2.1.4/jquery.min.js",
    "org.webjars" % "d3js" % "3.5.16" / "3.5.16/d3.js" minified "3.5.16/d3.min.js"
  )
).enablePlugins(ScalaJSPlugin)

lazy val chewbacca = (project in file("chewbacca")).settings(
  name := "Chewbacca",
  version := "0.1",
  scalaVersion := "2.11.8",
  libraryDependencies ++= LibDepends.basicDepends,
  libraryDependencies ++= LibDepends.akkaDepends
)

Build.ds3webJS in jst := {
  (fullOptJS in jst in Compile).toTask.value
  val files = Seq("scalajst-jsdeps.min.js", "scalajst-opt.js", "scalajst-opt.js.map")
  val currentDir = new java.io.File(".").getCanonicalFile
  val baseDir = currentDir.getParentFile.getParentFile
  IO.copy(
    files.map(file => (
      baseDir / "explore/scala/scalajst/target/scala-2.11" / file,
      baseDir / "castles/ds3-web/public/generated-js" / file
      )),
    true,
    true
  )
}

Build.hello in s1 := {
  (runMain in s1 in Compile).toTask(" com.kdr2.scala0.MainHello").value
}

Build.hello in jst := {
  println((name in jst).value)
  println("Hello!")
}

Build.hello in root := {
  println("root!")
}