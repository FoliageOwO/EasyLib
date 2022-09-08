plugins {
    id("java")
    id("maven-publish")
}

group = "ml.windleaf"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
    implementation("org.apache.logging.log4j:log4j-core:2.18.0")
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
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
        implementation("org.jetbrains:annotations:23.0.0")
        implementation("org.apache.maven:maven-artifact:3.8.5")
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
    }
}

publishing {
    publications {
        create("maven_public", MavenPublication::class) {
            groupId = "ml.windleaf"
            artifactId = "EasyLib"
            version = version
            from(components.getByName("java"))
        }
    }
}