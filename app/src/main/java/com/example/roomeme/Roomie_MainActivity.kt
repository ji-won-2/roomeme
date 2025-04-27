package com.example.roomeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Roomie_MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roomie_main)


        val btn1 = findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener {
            Toast.makeText(this, "내 프로필 슝슝", Toast.LENGTH_LONG).show()
            val intent = Intent(this, My_inforActivity::class.java)
            startActivity(intent)
        }

        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener {
            Toast.makeText(this, "루미 슝슝", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Roomie_MC_Activity::class.java)
            startActivity(intent)
        }

        val btn3 = findViewById<Button>(R.id.btn3)
        btn3.setOnClickListener {
            Toast.makeText(this, "기숙사 일정표 슝슝", Toast.LENGTH_LONG).show()
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        val btn4 = findViewById<Button>(R.id.btn4)
        btn4.setOnClickListener {
            Toast.makeText(this, "루미 대나무 숲 슝슝", Toast.LENGTH_LONG).show()
            val intent = Intent(this, PandaActivity::class.java)
            startActivity(intent)
        }
    }
}