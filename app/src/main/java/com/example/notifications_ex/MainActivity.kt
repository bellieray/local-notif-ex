package com.example.notifications_ex

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.notifications_ex.databinding.ActivityMainBinding
import com.example.util.NotificationsUtil.createNotificationChannel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        createTimer()
    }

    private fun createTimer() {
        val second = 3L

        object : CountDownTimer((second * 1000), 1000) {
            override fun onTick(p0: Long) {
                val timeResult = String.format(
                    "%02d:%02d",
                    (p0 / 1000 / 60),
                    (p0 / 1000 % 60)
                )
                binding.root.post {
                    binding.timer.text = timeResult
                }
            }

            override fun onFinish() {
                showNotification()
            }

        }.start()

    }


    fun showNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(this, notificationManager)
        }


        val builder =
            NotificationCompat.Builder(applicationContext, "DEFAULT_CHANNEL")
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .setContentTitle("Info Title")
                .setContentText("***Time has finished***")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setColor(ContextCompat.getColor(applicationContext, R.color.purple_200))


        val nf = builder.build()
        notificationManager.notify(System.currentTimeMillis().toInt(), nf)
    }

    @AfterPermissionGranted(11)
    private fun checkPermission() {
        if ((EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ))
        ) return
        EasyPermissions.requestPermissions(
            PermissionRequest.Builder(
                this,
                11,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            )
                .setRationale(R.string.permission_rationale_camera_for_qr_search)
                .setPositiveButtonText(R.string.ok)
                .setNegativeButtonText(R.string.cancel)
                .build()
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .setPositiveButton(R.string.settings)
                .setTitle(R.string.app_name)
                .setNegativeButton(R.string.cancel)
                .setRationale(R.string.permission_rationale_camera_for_qr_search_permanently_denied)
                .build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE && !EasyPermissions.hasPermissions(
                this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            )
        ) {
            Toast.makeText(this, "İzin vermedin hiç bir zaman", Toast.LENGTH_SHORT).show()
        }
    }
}