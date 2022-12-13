package com.example.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.notifications_ex.R


object NotificationsUtil {
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun createNotificationChannel(
        context: Context,
        nm: NotificationManager
    ) {

        nm.createNotificationChannel(
            NotificationChannel(
                "DEFAULT_CHANNEL",
                context.getString(R.string.hello_blank_fragment),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Selamlar"
                setShowBadge(true)
            }
        )
    }
}
