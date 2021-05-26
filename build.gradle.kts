val coroutinesVersion: String by System.getProperties()

val postgresVersion: String by System.getProperties()
val flywayVersion: String by System.getProperties()
val exposedVersion: String by System.getProperties()

val jwtVersion: String by System.getProperties()

val junitVersion: String by System.getProperties()
val mokkVersion: String by System.getProperties()

plugins {
    val springBootVersion: String by System.getProperties()
    val springDependencyManagementVersion: String by System.getProperties()
    val kotlinVersion: String by System.getProperties()
    val flywayVersion: String by System.getProperties()
    val jpaKotlinPluginVersion: String by System.getProperties()

    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version springDependencyManagementVersion
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.flywaydb.flyway") version flywayVersion
    id("org.jetbrains.kotlin.plugin.jpa") version jpaKotlinPluginVersion
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$coroutinesVersion")

    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("io.mockk:mockk:$mokkVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

flyway {
    url = "jdbc:postgresql://localhost:5500/finances"
    user = "ilia"
    password = "gjkbnt["
}
