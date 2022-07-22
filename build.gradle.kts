plugins {
    id("java")
}

group = "ml.windleaf"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:2.18.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}