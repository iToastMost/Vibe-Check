package com.CheekyLittleApps.vibecheck

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
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
import com.CheekyLittleApps.vibecheck.ui.screens.alarm.AlarmScreen
import com.CheekyLittleApps.vibecheck.ui.screens.moodentry.MoodEntryScreen
import com.CheekyLittleApps.vibecheck.ui.screens.overview.OverviewScreen
import com.CheekyLittleApps.vibecheck.ui.screens.viewmood.ViewMoodScreen
import com.CheekyLittleApps.vibecheck.viewmodel.MainViewModel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity()
{
    private val moodDatabase by lazy {MoodDatabase.getDatabase(this).moodDao()}
    val DAYS_FOR_IMMEDIATE_UPDATE = 3
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val context = this

        createNotificationChannel()

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
                    VibeApp(viewModel, context)
                }
            }
        }
    }
        private fun createNotificationChannel(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "alarm_id"
                val channelName = "alarm_name"
                val importance = NotificationManager.IMPORTANCE_LOW
                val channel = NotificationChannel(channelId, channelName, importance)

                val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun sendNotification(){
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = "vibecheck://vibe_destination".toUri()

        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1234, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Vibe Checkin In")
            .setContentText("How are we feeling today?")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)){
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101)
                return
            }
            notify(NOTIFICATION_ID, builder.build())
        }

    }

    companion object{
        const val CHANNEL_ID = "channel"
        const val NOTIFICATION_ID = 1234
        const val DESCRIPTION = "Test notification"
    }

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun VibeApp(viewModel: MainViewModel, context: Context) {
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
            composable(
                route = Overview.route,
            ){
                OverviewScreen(viewModel,
                    onClickAddEntry = {navController.navigateSingleTopTo(Mood.route)},
                    onClickViewMood = { roomId -> navController.navigateToMood(roomId)},
                    onClickSendNotification = { sendNotification() },
                    onClickAlarm = {navController.navigateSingleTopTo(Alarm.route)},
                    context
                    //onClickViewMood = {navController.navigateSingleTopTo(ViewMood.route)}
                )
            }

            composable(
                route = Mood.route,
                deepLinks = Mood.deepLinks
            ) {
                MoodEntryScreen(viewModel,
                    onClickEntryAdded = {navController.navigateSingleTopTo(Overview.route)}
                )
            }

            composable(
                route = ViewMood.routeWithArgs,
                arguments = ViewMood.arguments,
            ) { navBackStackEntry ->
                val roomId =
                    navBackStackEntry.arguments?.getInt("roomId") ?: 0

                ViewMoodScreen(viewModel, roomId,
                    onClickEntryAdded = { navController.navigateSingleTopTo(Overview.route) }
                )
            }

            composable(
                route = Alarm.route
            ) {
                AlarmScreen(viewModel,
                    onClickAlarm = { navController.navigateSingleTopTo(Alarm.route) },
                    onClickEntryAdded = { navController.navigateSingleTopTo(Overview.route) }
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

}
