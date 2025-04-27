package com.example.roomeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btn1 = findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener {
            Toast.makeText(this, "로그인 슝슝", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener {
            Toast.makeText(this, "회원가입 슝슝", Toast.LENGTH_LONG).show()
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }

}