plugins {
    id("java")
    id("application")
}

application {
    mainClass = "dev.aurakle.mocha.executable.Mocha"
}

dependencies {
    implementation(project(":library"))
    implementation(project(":compiler")) // temporary, for testing
}
