import sbt._

object Dependencies {
  object Version {
    val http4s         = "0.21.1"
    val doobie         = "0.8.6"
    val circe          = "0.13.0"
    val logback        = "1.2.3"
    val refined        = "0.9.15"
    val log4Cats       = "1.1.1"
    val ciris          = "1.1.1"
    val fs2            = "2.4.2"
    val scalatest      = "3.2.0"
    val googleCloudNio = "0.121.2"
    val flyway         = "6.5.0"
    val zookCore       = "1.0.2"
    val cats           = "2.2.0"
    val catsEffect     = "2.1.4"
    val catsEffectTime = "0.1.2"
    val postgres       = "42.2.9"
    val logbackClassic = "1.2.3"
    val scalaJwt       = "4.2.0"
  }

  object Library {

    val http4s: Seq[ModuleID]   = Seq(
      "org.http4s" %% "http4s-core",
      "org.http4s" %% "http4s-server",
      "org.http4s" %% "http4s-blaze-server",
      "org.http4s" %% "http4s-blaze-client",
      "org.http4s" %% "http4s-circe",
      "org.http4s" %% "http4s-dsl"
    ).map(_ % Version.http4s)

    val doobie: Seq[ModuleID]   = Seq(
      "org.tpolecat" %% "doobie-core",
      "org.tpolecat" %% "doobie-hikari",
      "org.tpolecat" %% "doobie-postgres",
      "org.tpolecat" %% "doobie-refined"
    ).map(_ % Version.doobie)

    val circe: Seq[ModuleID]    = Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser",
      "io.circe" %% "circe-refined"
    ).map(_ % Version.circe)

    val refined: Seq[ModuleID]  = Seq(
      "eu.timepit" %% "refined",
      "eu.timepit" %% "refined-cats"
    ).map(_ % Version.refined)

    val log4cats: Seq[ModuleID] = Seq(
      "io.chrisdavenport" %% "log4cats-core",
      "io.chrisdavenport" %% "log4cats-slf4j"
    ).map(_ % Version.log4Cats)

    val logbackClassic          = "ch.qos.logback" % "logback-classic" % Version.logbackClassic

    val ciris: Seq[ModuleID] = Seq(
      "is.cir" %% "ciris",
      "is.cir" %% "ciris-refined"
    ).map(_ % Version.ciris)

    val fs2: Seq[ModuleID]   = Seq("co.fs2" %% "fs2-core", "co.fs2" %% "fs2-io").map(_ % Version.fs2)

    val googleCloudNio = "com.google.cloud" % "google-cloud-nio" % Version.googleCloudNio

    val scalatest = "org.scalatest" %% "scalatest" % Version.scalatest

    val flyway = "org.flywaydb" % "flyway-core" % Version.flyway

    val postgres = "org.postgresql" % "postgresql" % Version.postgres

    val zookCore = "com.zooklabs" %% "zookcore" % Version.zookCore

    val cats           = "org.typelevel"     %% "cats-core"        % Version.cats
    val catsEffect     = "org.typelevel"     %% "cats-effect"      % Version.catsEffect
    val catsEffectTime = "io.chrisdavenport" %% "cats-effect-time" % Version.catsEffectTime

    val scalaJwt: Seq[ModuleID] = List(
      "com.pauldijou" %% "jwt-core",
      "com.pauldijou" %% "jwt-circe"
    ).map(_ % Version.scalaJwt)

  }

  object Resolvers {
    val zookcore: MavenRepository = "zookcore" at "https://maven.pkg.github.com/BearRebel/ZookCore"
  }

  lazy val dependencies: List[ModuleID] =
    List(
      Library.googleCloudNio,
      Library.flyway,
      Library.postgres,
      Library.zookCore,
      Library.cats,
      Library.catsEffect,
      Library.catsEffectTime,
      Library.logbackClassic
    ) ++
      Library.http4s ++
      Library.doobie ++
      Library.circe ++
      Library.ciris ++
      Library.refined ++
      Library.scalaJwt ++
      Library.log4cats ++
      Library.fs2

  lazy val testDependencies: List[ModuleID] = List(Library.scalatest)
    .map(_ % "it,test")

  lazy val resolvers: List[MavenRepository] = List(Resolvers.zookcore)

}