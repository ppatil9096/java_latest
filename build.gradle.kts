plugins {
    java
    application
    eclipse
    alias(libs.plugins.versions)
    alias(libs.plugins.version.catalog.update)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "com.kousenit"
version = "1.0"

application {
    mainClass.set("com.kousenit.fileio.ProcessDictionary")
}

repositories {
    mavenCentral()
}

dependencies {
    // JSON parsers
    implementation(libs.gson)
    implementation(libs.bundles.moshi)
    implementation(libs.bundles.jackson)

    // Logging
    implementation(libs.slf4j.nop)

    // Testing
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj)
    testImplementation(libs.bundles.mockito)
    testImplementation(libs.wiremock)
}

tasks.withType<JavaCompile>().forEach {
    it.options.compilerArgs.plusAssign("--enable-preview")
}

tasks.test {
    useJUnitPlatform()
    maxParallelForks = Runtime.getRuntime().availableProcessors() / 2 + 1
}

// See: https://github.com/redhat-developer/vscode-java/wiki/Enabling-Java-preview-features
//Buildship doesn't use that hook (https://discuss.gradle.org/t/when-does-buildship-eclipse-customization-run/20781/2)
//you need to run `gradlew eclipse` separately
//eclipse.jdt.file.withProperties { props: Properties ->
//    props["org.eclipse.jdt.core.compiler.problem.enablePreviewFeatures"] = "enabled"
//    props["org.eclipse.jdt.core.compiler.problem.reportPreviewFeatures"] = "ignore"
//}

//eclipse {
//    jdt {
//        file {
//            withProperties {
//                set("org.eclipse.jdt.core.compiler.problem.enablePreviewFeatures", "enabled")
//                set("org.eclipse.jdt.core.compiler.problem.reportPreviewFeatures", "ignore")
//            }
//        }
//    }
//}