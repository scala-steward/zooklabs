import com.typesafe.sbt.packager.docker.{DockerChmodType, DockerVersion}
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

lazy val dockerSettings = List(
  dockerBaseImage := "adoptopenjdk/openjdk11:alpine-slim",
  dockerExposedPorts += 8080,
  dockerAlias := DockerAlias(Some("registry.heroku.com"), Some("zooklabs"), "web", None),
  dockerPackageMappings in Docker ++= List(
    baseDirectory.value / "scripts" / "entrypoint" -> "/opt/docker/bin/entrypoint"
  ),
  dockerEntrypoint := "/opt/docker/bin/entrypoint" +: dockerEntrypoint.value,
  dockerChmodType := DockerChmodType.UserGroupWriteExecute,
  dockerVersion := Some(DockerVersion(18, 9, 0, Some("ce"))) // required for github actions
)

lazy val releaseSettings = Seq(
  releaseUseGlobalVersion := true,
  releaseIgnoreUntrackedFiles := true,
  releaseTagName := s"v${(version in ThisBuild).value}",
  releaseTagComment := s"Release version ${(version in ThisBuild).value}",
  releaseCommitMessage := s"Set version to ${(version in ThisBuild).value} [ci skip]",
  releaseNextCommitMessage := s"Set version to ${(version in ThisBuild).value} [ci skip]",
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    setReleaseVersion,
    releaseStepCommand("docker:publish"),
    commitReleaseVersion,
    tagRelease,
    setNextVersion,
    commitNextVersion,
    pushChanges
  )
)

lazy val root = (project in file("."))
  .settings(name := "zooklabs", organization := "com.zooklabs")
  .enablePlugins(JavaAppPackaging, DockerPlugin, AshScriptPlugin)
  .configs(IntegrationTest)
  .settings(
    scalaVersion := "2.13.5",
    releaseSettings,
    dockerSettings,
    resolvers ++= Dependencies.resolvers,
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    libraryDependencies ++= Dependencies.dependencies,
    libraryDependencies ++= Dependencies.testDependencies,
    Defaults.itSettings,
    fork in run := true, // required for SBT to correctly allow IOApps to release resources on termination
    fork in Compile := true // required for google-cloud-nio to be installed as a filesystem provider
  )

lazy val zookcoreStub = project
  .in(file("zookcore-stub"))
  .settings(name := "zookcore", organization := "com.zooklabs")
  .settings(scalaVersion := "2.13.5", version := Dependencies.Version.zookcore)
