name := """swagger-play"""

version := "0.2"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
        cache,
         ws,
	"com.typesafe.slick"           %% "slick"                 % "2.1.0",
	"org.postgresql"                % "postgresql"            % "9.4-1201-jdbc41",
	"com.typesafe.play"            %% "play-slick"            % "0.8.1",
        "com.wordnik"                  %% "swagger-play2"         % "1.3.8"
)
