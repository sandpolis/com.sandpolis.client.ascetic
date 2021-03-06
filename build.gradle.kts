//============================================================================//
//                                                                            //
//                         Copyright © 2015 Sandpolis                         //
//                                                                            //
//  This source file is subject to the terms of the Mozilla Public License    //
//  version 2. You may not use this file except in compliance with the MPL    //
//  as published by the Mozilla Foundation.                                   //
//                                                                            //
//============================================================================//

import com.bmuschko.gradle.docker.tasks.container.*
import com.bmuschko.gradle.docker.tasks.image.*

plugins {
	id("java-library")
	id("sandpolis-java")
	id("sandpolis-module")
	id("sandpolis-publish")
	id("sandpolis-soi")
	id("com.bmuschko.docker-remote-api") version "6.6.0"
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.2")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.2")

	// https://github.com/qos-ch/logback
	implementation("ch.qos.logback:logback-core:1.3.0-alpha5")
	implementation("ch.qos.logback:logback-classic:1.3.0-alpha5")

	// https://github.com/mabe02/lanterna
	implementation("com.googlecode.lanterna:lanterna:3.1.1")

	if (project.getParent() == null) {
		implementation("com.sandpolis:core.client:+")
		implementation("com.sandpolis:core.net:+")
		implementation("com.sandpolis:core.instance:+")
	} else {
		implementation(project(":module:com.sandpolis.core.client"))
		implementation(project(":module:com.sandpolis.core.net"))
		implementation(project(":module:com.sandpolis.core.instance"))
	}
}

task<Sync>("assembleLib") {
	dependsOn(tasks.named("jar"))
	from(configurations.runtimeClasspath)
	from(tasks.named("jar"))
	into("${buildDir}/lib")
}

task<DockerBuildImage>("buildImage") {
	dependsOn(tasks.named("assembleLib"))
	inputDir.set(file("."))
	images.add("sandpolis/client/ascetic:${project.version}")
	images.add("sandpolis/client/ascetic:latest")
}

task<Exec>("runImage") {
	dependsOn(tasks.named("buildImage"))
	commandLine("docker", "run", "--rm", "-e", "S7S_DEVELOPMENT_MODE=true", "-e", "S7S_LOG_LEVELS=io.netty=WARN,java.util.prefs=OFF,com.sandpolis=TRACE", "sandpolis/client/ascetic:latest")
}
