plugins {
    `java-library`
}

group = "com.aitchn"
version = "3.23"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnlyApi("dev.folia:folia-api:26.2.build.5-beta")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.22.0")
    testImplementation("dev.folia:folia-api:26.2.build.5-beta")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
    options.encoding = "UTF-8"
}

tasks.jar {
    archiveBaseName.set("Prism-api")
}

tasks.named<Jar>("sourcesJar") {
    archiveBaseName.set("Prism-api")
}

val verifyApiVersion by tasks.registering {
    group = "verification"
    description = "Checks that the artifact version matches the PrismApi contract constants."
    val source = layout.projectDirectory.file("src/main/java/com/aitchn/prism/api/PrismApi.java")
    inputs.file(source)
    inputs.property("artifactVersion", version.toString())
    doLast {
        val content = source.asFile.readText(Charsets.UTF_8)
        val major = Regex("""API_MAJOR_VERSION\s*=\s*(\d+)""").find(content)?.groupValues?.get(1)
            ?: error("Missing API_MAJOR_VERSION")
        val minor = Regex("""API_MINOR_VERSION\s*=\s*(\d+)""").find(content)?.groupValues?.get(1)
            ?: error("Missing API_MINOR_VERSION")
        check("$major.$minor" == version.toString()) {
            "PrismApi declares $major.$minor but this artifact is ${version}"
        }
    }
}

tasks.named("check") {
    dependsOn(verifyApiVersion)
}

tasks.test {
    useJUnitPlatform()
}
