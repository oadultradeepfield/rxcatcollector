plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.oadultradeepfield"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.oadultradeepfield.rxcatcollector.Main"
        )
    }
}

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.6")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
}

application {
    mainClass.set("com.oadultradeepfield.rxcatcollector.Main")
}