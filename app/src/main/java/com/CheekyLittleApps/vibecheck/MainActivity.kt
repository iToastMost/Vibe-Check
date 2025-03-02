package com.CheekyLittleApps.vibecheck

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.CheekyLittleApps.vibecheck.data.MoodDatabase
import com.CheekyLittleApps.vibecheck.model.MoodEntry
import com.CheekyLittleApps.vibecheck.ui.moodentry.MoodEntryScreen
import com.CheekyLittleApps.vibecheck.ui.overview.OverviewScreen
import com.CheekyLittleApps.vibecheck.ui.viewmood.ViewMoodScreen
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity()
{
    private val moodDatabase by lazy {MoodDatabase.getDatabase(this).moodDao()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(applicationContext, MoodDatabase::class.java, "mood_entries").fallbackToDestructiveMigration(true).build()
        val viewModel = MainViewModel(db.moodDao())
        lifecycleScope.launch(Dispatchers.IO) {
            val moods = db.moodDao().getAll()
            // Use withContext to update UI if needed
            val moodDao = db.moodDao()
            val moodEntries: Flow<List<MoodEntry>> = moodDao.getAll()
            withContext(Dispatchers.Main) {
                // Update your UI with the fetched data
                setContent {
                    VibeApp(viewModel)
                }
            }
    }
}}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VibeApp(viewModel: MainViewModel)
{
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen = Overview.route

    Scaffold(
        topBar = {

        },
    ){
        NavHost(
            navController = navController,
            startDestination = Overview.route,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))},
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700))},
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
        ) {
            //TODO Fix onClickViewMood here to navigate with roomId
            composable(route = Overview.route){
                OverviewScreen(viewModel,
                    onClickAddEntry = {navController.navigateSingleTopTo(Mood.route)},
                    onClickViewMood = { roomId -> navController.navigateToMood(roomId)}
                    //onClickViewMood = {navController.navigateSingleTopTo(ViewMood.route)}
                )
            }

            composable(route = Mood.route) {
                MoodEntryScreen(viewModel,
                    onClickEntryAdded = {navController.navigateSingleTopTo(Overview.route)}
                )
            }

            //TODO Link up ViewMoodScreen navArgs here
            composable(
                route = ViewMood.routeWithArgs,
                arguments = ViewMood.arguments,
            ) { navBackStackEntry ->
                val roomId =
                    navBackStackEntry.arguments?.getInt("roomId") ?: 0

                ViewMoodScreen(viewModel, roomId,
                    onClickEntryAdded = { navController.navigateSingleTopTo(Overview.route)}
                )
            }
        }
    }


}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ){
        saveState = false
    }
    launchSingleTop = true
    restoreState = true
}

private fun NavHostController.navigateToMood(roomId: Int){
    this.navigateSingleTopTo("${ViewMood.route}/$roomId")
}
