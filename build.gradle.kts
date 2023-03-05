plugins {
    id("java")
    id("maven-publish")
}

group = "ml.windleaf"
version = "1.0.3"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/central")
}

dependencies {
    implementation("org.reflections:reflections:0.10.2")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.23")
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
    implementation("commons-io:commons-io:2.11.0")
}

fun compare(s: String, vararg names: String): Boolean {
    var result = false
    names.forEach {
        if (s.contains(it)) result = true
    }
    return result
}

tasks.jar {
    val dependencies = configurations.compileClasspath.get().filter {
        compare(it.name,
            "reflections",
            "fastjson2",
            "commons-io")
    }.map(::zipTree)
    from(dependencies)
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