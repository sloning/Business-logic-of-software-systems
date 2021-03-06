plugins {
    id("org.springframework.boot") version "2.6.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
}

group = "com.bloss.mainservice"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("org.springframework.boot:spring-boot-starter-jta-atomikos")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.auth0:java-jwt:3.18.3")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    implementation("org.springdoc:springdoc-openapi-security:1.6.6")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.6")
    implementation("org.postgresql:postgresql")
    implementation("com.rabbitmq.jms:rabbitmq-jms:2.4.0")

    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter:7.17.0")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest:7.17.0")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:7.17.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
