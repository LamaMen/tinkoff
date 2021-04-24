dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.postgresql:postgresql:42.2.19")
    implementation("org.jetbrains.exposed:exposed-core:0.30.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.30.1")

    implementation("org.flywaydb:flyway-core:7.8.1")
    implementation("org.springframework:spring-jdbc")
}