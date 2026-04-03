import com.google.cloud.tools.jib.gradle.JibExtension

plugins {
    id("org.springframework.boot") version "3.4.1" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id("com.google.cloud.tools.jib") version "3.4.4" apply false
    java
}

allprojects {
    group = "com.cloudnative.ecommerce"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "com.google.cloud.tools.jib")

    configure<JibExtension> {
        from {
            image = "gcr.io/distroless/java21-debian12"
        }
        to {
            image = "cloudnative/${project.name}:${project.version}"
        }
        container {
            jvmFlags = listOf("-XX:+UseZGC", "-XX:MaxRAMPercentage=75.0")
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        
        // Lombok para reducir boilerplate
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        
        // Hilos Virtuales (Project Loom) - Activado por defecto en Spring Boot 3.2+
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
