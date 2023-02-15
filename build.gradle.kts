plugins {
    val kotlinVersion = "1.6.20"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.13.0-RC2"
}

group = "com.rongxiaoli"
version = "0.2.0"

repositories {
    maven("https://repo1.maven.org/maven2")
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
dependencies {
    implementation("com.alibaba.fastjson2:fastjson2:2.0.21")
    implementation("com.google.code.gson:gson:2.10")
}