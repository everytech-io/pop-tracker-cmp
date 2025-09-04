package io.everytech.poptracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform