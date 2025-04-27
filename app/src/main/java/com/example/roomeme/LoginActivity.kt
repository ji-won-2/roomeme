package com.example.roomeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        val btn_log = findViewById<Button>(R.id.btn_log)
        btn_log.setOnClickListener{

            val email = findViewById<EditText>(R.id.ID)!!
            val pwd = findViewById<EditText>(R.id.PW)!!

            auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        if (user != null && user.isEmailVerified) {
                            Toast.makeText(this, "로그인 슝슝", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, Roomie_MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "이메일 인증이 필요합니다.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}
