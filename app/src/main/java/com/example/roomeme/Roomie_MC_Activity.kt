package com.example.roomeme

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roomeme.databinding.ActivityRoomieMcBinding
import com.example.roomeme.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Roomie_MC_Activity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomieMcBinding
    private lateinit var matcherAdapter: MatcherAdapter
    private lateinit var database: DatabaseReference
    private val userList: MutableList<User> = ArrayList()
    private var myUserInfo: User = User()
    private lateinit var auth: FirebaseAuth

    private val TAG = "FirebaseSample"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_roomie_mc)
        auth = Firebase.auth
        with(binding) {

            with(rvMatchers) {
                matcherAdapter = MatcherAdapter()
                adapter = matcherAdapter
            }
        }

        // 파이어베이스 데이터베이스 초기화
        database = Firebase.database.reference

        // 모든 유저 정보 가져오기
        getAllUserInfo()
    }

    // 모든 유저 정보 가져오기
    private fun getAllUserInfo() {
        database.child("users").get().addOnSuccessListener { snapShot ->
            snapShot.children.forEachIndexed { _, result ->
                val user = result.getValue(User::class.java) ?: User()

                // 내 유저정보인지 검사
                val userEmail = FirebaseAuth.getInstance().currentUser?.email
                if(user.userEmail == userEmail) {
                    myUserInfo = user
                } else {
                    userList.add(user)
                }
            }

            // 필수 3가지 조건 검사
            val matchedUsers = userList.filter {
                it.gender == myUserInfo.gender &&
                        it.domitory == myUserInfo.domitory &&
                        it.cigarette == myUserInfo.cigarette
            }

            // 랜덤으로 3명의 룸메이트 추천
            val recommendedUsers = matchedUsers.shuffled().take(3)

            // 매칭률 표시
            recommendedUsers.forEach { user ->
                calcMatchPercent(user)
            }

            // 화면에 표시
            matcherAdapter.submitList(recommendedUsers)
            Log.d(TAG, "recommendedUsers: $recommendedUsers")

        }.addOnFailureListener { }
    }

    private fun calcMatchPercent(user: User) {
        // 코골이
        if(myUserInfo.sleepHabit1 == user.sleepHabit1) {
            user.matchCount++
        }

        // 이갈이
        if(myUserInfo.sleepHabit2 == user.sleepHabit2) {
            user.matchCount++
        }

        // 올빼미 / 얼리버드
        if(myUserInfo.sleep == user.sleep) {
            user.matchCount++
        }

        // 아침샤워 / 밤샤워
        if(myUserInfo.shower == user.shower) {
            user.matchCount++
        }

        // 어울리기 / 혼자놀기
        if(myUserInfo.alone == user.alone) {
            user.matchCount++
        }

        // 외향적 / 내형적
        if(myUserInfo.clean == user.clean) {
            user.matchCount++
        }

        // 수시로 정리 / 몰아서 정리
        if(myUserInfo.cleanRoom == user.cleanRoom) {
            user.matchCount++
        }

        // 본가 러버 / 긱사 러버

        if(myUserInfo.home == user.home) {
            user.matchCount++
        }

        // 모이(긱사식) / 사냥(외식)
        if(myUserInfo.meal == user.meal) {
            user.matchCount++
        }
        if(myUserInfo.wakeup == user.wakeup) {
            user.matchCount++
        }
        // 학부
        /*
        if(myUserInfo.department == user.department) {
            user.matchCount++
        }
        */

        // 매칭률 = 매칭 카운트 / 총 비교한 필드 수 * 100
        user.matchPercent = (user.matchCount / 10f * 100f).toInt()

        // 로그
        Log.d(TAG, "user.matchCount: ${user.matchCount}, user.matchPercent: ${user.matchPercent}")
    }
}
