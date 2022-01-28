package com.example.loginapp.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.loginapp.R
import com.example.loginapp.activities.HomeScreenActivity
import com.example.loginapp.activities.LoginScreenActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotification: FirebaseMessagingService() {

    private val channelId: String = "notification"
    private val channelName: String = "com.example.loginapp"

    override fun onNewToken(p0: String) {
        Log.d("newFT", p0)

    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d("mess", p0.notification?.title.toString())
        if (p0.notification != null) {
            generateNotification(p0.notification?.title!!, p0.notification?.body!!)
        }
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, LoginScreenActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, "notification")
            .setSmallIcon(R.drawable.ic_notification)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.example.loginapp", R.layout.notification)

        remoteView.setTextViewText(R.id.notificationTitle, title)
        remoteView.setTextViewText(R.id.notificationMessage, message)
        remoteView.setImageViewResource(R.id.appLogo, R.drawable.logo)
        return remoteView
    }
}