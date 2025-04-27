package com.example.roomeme


import android.annotation.SuppressLint

import android.os.Bundle

import android.widget.CalendarView

import androidx.appcompat.app.AppCompatActivity



class CalendarActivity : AppCompatActivity() {

    lateinit var calendarView: CalendarView



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // UI값 생성
        calendarView = findViewById(R.id.calendarView)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->


        }

    }
}

