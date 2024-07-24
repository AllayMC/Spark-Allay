plugins {
    `java-library`
    id("com.google.protobuf") version "0.9.4"
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "org.allaymc"
description = "spark is a performance profiling plugin/mod for Minecraft clients, servers and proxies."
version = "1.0.0"

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    mavenLocal() // Used for Allay-API & spark-common
    maven("https://repo.opencollab.dev/maven-releases/")
    maven("https://repo.opencollab.dev/maven-snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://maven.powernukkitx.cn/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly("org.allaymc:Allay-API:1.0.0")
//    compileOnly(files("libs/Allay-API-1.0.0-all.jar"))

    implementation("me.lucko:spark-api:0.1-SNAPSHOT")
//    implementation("me.lucko:spark-common:1.10-SNAPSHOT") TODO: fix this
    implementation(files("libs/spark-common-1.10-SNAPSHOT.jar"))

    implementation("net.kyori:adventure-text-serializer-legacy:4.17.0")

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.processResources {
    filesMatching("plugin.json") {
        expand(
            "description" to project.description,
            "version" to project.version
        )
    }
}

tasks.shadowJar {
    archiveFileName = "spark-${project.version}-allay.jar"

    relocate("net.kyori.adventure", "org.allaymc.spark.lib.adventure")
    relocate("net.kyori.examinatio", "org.allaymc.spark.lib.adventure.examination")
    relocate("net.bytebuddy", "org.allaymc.spark.lib.bytebuddy")
    relocate("com.google.protobuf", "org.allaymc.spark.lib.protobuf")
    relocate("org.objectweb.asm", "org.allaymc.spark.lib.asm")
    relocate("one.profiler", "org.allaymc.spark.lib.asyncprofiler")
    relocate("me.lucko.bytesocks.client", "org.allaymc.spark.lib.bytesocks")
    relocate("org.java_websocket", "org.allaymc.spark.lib.bytesocks.ws")

    exclude("module-info.class")
    exclude("META-INF/maven/**")
    exclude("META-INF/proguard/**")
}
