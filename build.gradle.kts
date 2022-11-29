import enforcer.rules.DependencyConvergence
import enforcer.rules.RequireGradleVersion
import enforcer.rules.RequireJavaVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.kordamp.gradle.plugin.enforcer.api.EnforcerPhase.BEFORE_BUILD
import org.kordamp.gradle.plugin.enforcer.api.EnforcerPhase.BEFORE_PROJECT
import se.bjurr.gitchangelog.plugin.gradle.GitChangelogTask

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id("io.kotest.multiplatform") version "5.0.2"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    id("org.kordamp.gradle.project-enforcer") version "0.9.0"
    id("info.solidsoft.pitest") version "1.7.0"
    id("se.bjurr.gitchangelog.git-changelog-gradle-plugin") version "1.71.4"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.31"
    id("org.jetbrains.kotlinx.kover") version "0.5.0"
    id("org.sonarqube") version "3.3"
}

group = "io.redbee"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2021.0.4"
extra["springBootVersion"] = "2.7.4"
extra["testcontainersVersion"] = "1.16.3"
extra["kotestVersion"] = "5.5.1"
extra["arrowVersion"] = "1.0.1"
extra["archUnitVersion"] = "1.0.0"
extra["mockkVersion"] = "1.13.2"
extra["logstashVersion"] = "7.2"
extra["r2dbcVersion"] = "1.0.0.RC1"
extra["springDocOpenApiVersion"] = "1.6.12"

dependencies {

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.kafka:spring-kafka")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    // Logback
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")

    // Swagger
    implementation("org.springdoc:springdoc-openapi-webflux-ui:${property("springDocOpenApiVersion")}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${property("springDocOpenApiVersion")}")

    // JDBC driver
    implementation("io.r2dbc:r2dbc-mssql:${property("r2dbcVersion")}")

    // Arrow
    implementation ("io.arrow-kt:arrow-fx-coroutines:${property("arrowVersion")}")

    // Tests
    testImplementation("com.tngtech.archunit:archunit:${property("archUnitVersion")}")
    testImplementation("com.tngtech.archunit:archunit-junit5:${property("archUnitVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation ("io.kotest:kotest-runner-junit5:${property("kotestVersion")}")
    testImplementation ("io.kotest:kotest-assertions-core:${property("kotestVersion")}")
    testImplementation ("io.kotest:kotest-property:${property("kotestVersion")}")
    testImplementation("io.mockk:mockk:${property("mockkVersion")}")



}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-starter-parent:${property("springBootVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
    }
}

configure<org.springframework.boot.gradle.dsl.SpringBootExtension> {
    buildInfo()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.register<GitChangelogTask>("changelog") {
    fromRepo = projectDir.absolutePath
    toRef = "main"
    file = file(project.file("CHANGELOG.md").absolutePath)
    templateContent = file(project.file("changelog.mustache").absolutePath).readText()
}

enforce {
    rule(DependencyConvergence::class.java) {
        phases.set(listOf(BEFORE_PROJECT))
    }
    rule(RequireJavaVersion::class.java) {
        version.set("[11,12)")
        phases.set(listOf(BEFORE_BUILD))
    }
    rule(RequireGradleVersion::class.java) {
        version.set("[7.0,7.2]")
    }
}

pitest {
    testPlugin.set("Kotest")
    outputFormats.set(setOf("XML", "HTML"))
    targetClasses.set(setOf("com.prismamp.domain.*"))
}

sourceSets {
    test {
        resources {
            srcDirs("src/test/kotlin")
            exclude("**/*.kt")
        }
    }
}

tasks.compileJava {
    enabled = false
}

tasks.compileTestJava {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
    extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
        isEnabled = true
    }
    finalizedBy(tasks.koverVerify, tasks.koverReport)
}

kover {
    isDisabled = false
    coverageEngine.set(kotlinx.kover.api.CoverageEngine.JACOCO)
    jacocoEngineVersion.set("0.8.7")
}

tasks.koverHtmlReport {
    isEnabled = true
    htmlReportDir.set(layout.buildDirectory.dir("reports/kover/html"))
}

tasks.koverXmlReport {
    isEnabled = true
    xmlReportFile.set(layout.buildDirectory.file("reports/kover/xml/test-results.xml"))
}

tasks.koverVerify {
    rule {
        name = "Minimum line coverage percentage rate"
        bound {
            minValue = 90
        }
    }
}

tasks.bootJar {
    archiveFileName.set("api.jar")
}

tasks.register<Copy>("installGitHook") {
    group = "verification"
    description = "install pre-commit & pre-push linting hooks"

    // copy pre-commit hook
    from("scripts/pre-commit")
    into(".git/hooks")

    // copy pre-push hook
    from("scripts/pre-push")
    into(".git/hooks")

    fileMode = 0b111111101
}

tasks.build {
    dependsOn("installGitHook")
}
