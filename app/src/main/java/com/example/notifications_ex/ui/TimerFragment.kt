package com.example.notifications_ex.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.notifications_ex.databinding.FragmentTimerBinding
import com.example.util.NotificationsUtil

class TimerFragment : Fragment() {
    private var _binding: FragmentTimerBinding? = null
    private val binding = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTimerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.timer.text = ("${createTimer().get("minutes")} : ${createTimer().get("second")}")

    }

    private fun createTimer(): HashMap<String, Long> {
        val second = 600L
        val list = hashMapOf<String, Long>()
        object : CountDownTimer((second * 1000), 1000) {
            override fun onTick(p0: Long) {
                val minutes = p0 / 60
                val seconds = p0 % 60

                list["minute"] = minutes
                list["second"] = seconds
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }

        }.start()

        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}