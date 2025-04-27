package com.example.roomeme

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.roomeme.model.User

class My_inforActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_infor)

        // Firebase 인증 객체 초기화
        auth = FirebaseAuth.getInstance()
        // Firebase Realtime Database 객체 초기화
        database = FirebaseDatabase.getInstance().reference

        // 현재 사용자의 이메일을 가져옵니다.
        val userEmail = auth.currentUser?.email

        val usersRef = database.child("users")
        usersRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.mapNotNull { it.getValue(User::class.java) }

                for (user in users) {
                    // 사용자 이메일이 현재 사용자의 이메일과 일치하는지 확인합니다.
                    if (user.userEmail == userEmail) {
                        val alone = user.alone
                        val cigarette = user.cigarette
                        val clean = user.clean
                        val cleanRoom = user.cleanRoom
                        val domitory = user.domitory
                        val gender = user.gender
                        val home = user.home
                        val meal = user.meal
                        val shower = user.shower
                        val sleep = user.sleep
                        val wakeup = user.wakeup
                        val sleepHabit1 = user.sleepHabit1
                        val sleepHabit2 = user.sleepHabit2

                        val userInfo = StringBuilder()
                        if (gender != null) {
                            userInfo.append("#$gender ")
                        }
                        if (domitory != null) {
                            userInfo.append("#$domitory ")
                        }
                        if (cigarette != null) {
                            userInfo.append("#$cigarette\n")
                        }


                        if (wakeup != null) {
                            userInfo.append("#$wakeup ")
                        }
                        if (sleep != null) {
                            userInfo.append("#$sleep ")
                        }
                        if (shower != null) {
                            userInfo.append("#$shower\n")
                        }


                        if (clean != null) {
                            userInfo.append("#$clean ")
                        }
                        if (alone != null) {
                            userInfo.append("#$alone ")
                        }
                        if (cleanRoom != null) {
                            userInfo.append("#$cleanRoom\n")
                        }

                        if (sleepHabit1 != null) {
                            userInfo.append("#$sleepHabit1 ")
                        }
                        if (sleepHabit2 != null) {
                            userInfo.append("#$sleepHabit2\n")
                        }


                        if (home != null) {
                            userInfo.append("#$home ")
                        }
                        if (meal != null) {8
                            userInfo.append("#$meal\n")
                        }



                        // 필요한 정보를 추가로 계속해서 작성합니다.

                        Log.d("My_inforActivity", "사용자 정보: $userInfo")

                        // 사용자 정보를 TextView에 설정합니다.
                        val userInfoTextView: TextView = findViewById(R.id.nameTextView)
                        userInfoTextView.text = userInfo.toString()
                        break // 일치하는 사용자를 찾은 후 루프를 종료합니다.
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("My_inforActivity", "사용자 정보 검색 오류: ${error.message}")
            }
        })
    }
}
