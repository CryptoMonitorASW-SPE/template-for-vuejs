import io.github.andreabrighi.gradle.gitsemver.conventionalcommit.ConventionalCommit
import com.github.gradle.node.npm.task.NpmTask
import com.github.gradle.node.task.NodeTask

plugins {
    id("org.danilopianini.git-sensitive-semantic-versioning") version "4.0.2"
    // Apply the Node.js plugin
    id("com.github.node-gradle.node") version "7.1.0"
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        // Add the plugin to the classpath
        classpath("io.github.andreabrighi:conventional-commit-strategy-for-git-sensitive-semantic-versioning-gradle-plugin:1.0.15")
    }
}

gitSemVer {
    maxVersionLength.set(20)
    commitNameBasedUpdateStrategy(ConventionalCommit::semanticVersionUpdate)
}

node {
    version.set("22.13.1")
    download.set(true)
    nodeProjectDir.set(file(project.projectDir))
}

tasks.register<Delete>("cleanBuild") {
    group = "build"
    description = "Delete dist and build directories"
    doFirst {
        delete("dist")
        delete("build")
    }
}

tasks.register<NpmTask>("npmCiRoot") {
    group = "npm"
    description = "Install npm dependencies in the root project"
    workingDir = file("..") 
    args.set(listOf("ci"))
}

tasks.register<NpmTask>("npmCiApp") {
    group = "npm"
    description = "Install npm dependencies in the app directory"
    args.set(listOf("ci"))
}

tasks.register("npmCiAll") {
    group = "npm"
    description = "Install npm dependencies in both root and app directories"
    dependsOn("npmCiRoot", "npmCiApp")
}

tasks.register<NpmTask>("buildVite") {
    group = "build"
    description = "Build the frontend with Vite"
    dependsOn("npmCiApp") 
    args.set(listOf("run", "build"))
}

tasks.register<NpmTask>("runFrontend") {
    group = "application"
    description = "Run the frontend in development mode"
    dependsOn("buildVite")
    args.set(listOf("run", "dev"))
}

tasks.register<NpmTask>("preview") {
    group = "application"
    description = "Preview the production build"
    dependsOn("buildVite")
    args.set(listOf("run", "preview"))
}

tasks.register("printVersion") {
    doLast {
        println("Project version: ${project.version}")
    }
}

tasks.register("preRunAll") {
    group = "application"
    description = "Clean, install dependencies and run tests (if available)"
    dependsOn("cleanBuild", "npmCiAll")
}

tasks.register("allInOne") {
    group = "application"
    description = "Run build and tests, then start the frontend application"
    dependsOn("preRunAll")
    finalizedBy("runFrontend")
}
