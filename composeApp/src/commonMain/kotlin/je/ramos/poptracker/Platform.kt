package je.ramos.poptracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform