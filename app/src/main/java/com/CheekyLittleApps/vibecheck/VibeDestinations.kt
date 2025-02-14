package com.CheekyLittleApps.vibecheck

interface VibeDestination
{
    val route: String
}

object Overview : VibeDestination {
    override val route = "overview"
}

object Mood : VibeDestination {
    override val route = "vibe_destination"
}