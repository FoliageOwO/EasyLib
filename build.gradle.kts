plugins {
    id("java")
    id("maven-publish")
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

allprojects {
    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:23.0.0")
        compileOnly("org.apache.maven:maven-artifact:3.8.5")
    }

    tasks {
        compileJava {
            dependsOn(clean)
            options.encoding = "UTF-8"
        }
    }
}

publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId = "ml.windleaf"
            artifactId = "PlugApi"
            version = version
            from(components.getByName("java"))
        }
    }
}