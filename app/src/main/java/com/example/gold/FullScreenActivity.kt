package com.example.gold

import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowInsets
import android.view.WindowManager
import com.example.gold.databinding.ActivityFullScreenBinding
import com.example.gold.databinding.BottomSheetNoInternetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FullScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        if (isNetworkState()) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(3000)

                startActivity(Intent(this@FullScreenActivity, MainActivity::class.java))
                finish()

            }
        } else {

            val dialog = BottomSheetDialog(this@FullScreenActivity)
            dialog.setCancelable(false)
            val view = BottomSheetNoInternetBinding.inflate(layoutInflater)
            view.btnRetry.setOnClickListener {
                recreate()
            }
            view.btnSetting.setOnClickListener {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }
            dialog.setContentView(view.root)
            dialog.show()


        }

    }

    private fun isNetworkState(): Boolean {

        val result: Boolean

        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNet =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            result = when {
                actNet.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNet.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                actNet.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {

            val netInfo = connectivityManager.activeNetworkInfo

            result = when (netInfo?.type) {
                ConnectivityManager.TYPE_ETHERNET -> true
                ConnectivityManager.TYPE_WIFI -> true
                ConnectivityManager.TYPE_MOBILE -> true
                else -> false
            }

        }
        return result
    }
}