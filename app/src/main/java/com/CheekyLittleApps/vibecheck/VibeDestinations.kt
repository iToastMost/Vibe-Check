package com.CheekyLittleApps.vibecheck

import androidx.navigation.NavType
import androidx.navigation.navArgument

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

object ViewMood : VibeDestination {
    override val route = "view_mood"
    //val roomIdArg = 0
    val routeWithArgs = "${route}/{roomId}"
    val arguments = listOf(
        navArgument("roomId") { type = NavType.IntType }
    )
}