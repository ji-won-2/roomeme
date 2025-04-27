package com.example.roomeme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private val TAG = "JoinActivity"
    private lateinit var auth: FirebaseAuth
    private lateinit var nameing: String
    private lateinit var department: String
    private lateinit var schoolnumber: String
    private lateinit var gender: String

    private lateinit var domitory: String
    private lateinit var cigarette: String
    private lateinit var alone: String
    private lateinit var sleepHabit1: String
    private lateinit var sleepHabit2: String
    private lateinit var wakeup: String
    private lateinit var sleep: String
    private lateinit var clean: String
    private lateinit var cleanRoom: String
    private lateinit var home: String
    private lateinit var shower: String
    private lateinit var meal: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        auth = Firebase.auth

        val email = findViewById<EditText>(R.id.btn_email)
        val pwd = findViewById<EditText>(R.id.btn_pw)
        val nameEditText = findViewById<EditText>(R.id.btn_name)
        val departmentEditText = findViewById<EditText>(R.id.btn_ma)
        val schoolNumberEditText = findViewById<EditText>(R.id.btn_num)
        val genderRadioGroup = findViewById<RadioGroup>(R.id.radio_group)

        val domitoryRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Domitory)
        val cigaretteRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Cigarette)
        val aloneRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Alone)
        val sleepHabitRadioGroup1 = findViewById<RadioGroup>(R.id.Radiogroup_Sleephabit1)
        val sleepHabitRadioGroup2 = findViewById<RadioGroup>(R.id.Radiogroup_Sleephabit2)
        val wakeupRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_WakeUP)
        val sleepRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Sleep)
        val cleanRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Clean)
        val cleanRoomRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Cleanroom)
        val homeRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Home)
        val showerRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Shower)
        val mealRadioGroup = findViewById<RadioGroup>(R.id.Radiogroup_Meal)


        val btn_join = findViewById<Button>(R.id.btn_join)
        btn_join.setOnClickListener {
            val userEmail = email.text.toString()
            val userPwd = pwd.text.toString()

            // 이메일 도메인 검증
            val isValidDomain = validateEmailDomain(userEmail)
            if (!isValidDomain) {
                Toast.makeText(
                    this@JoinActivity,
                    "올바른 이메일 주소를 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            nameing = nameEditText.text.toString()
            department = departmentEditText.text.toString()
            schoolnumber = schoolNumberEditText.text.toString()

            val selectedRadioButtonId = genderRadioGroup.checkedRadioButtonId

            val selectedRadioButtonId1 = domitoryRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId2 = cigaretteRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId3 = aloneRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId4 = sleepHabitRadioGroup1.checkedRadioButtonId
            val selectedRadioButtonId5 = sleepHabitRadioGroup2.checkedRadioButtonId
            val selectedRadioButtonId6 = wakeupRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId7 = sleepRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId8 = cleanRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId9 = cleanRoomRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId10 = homeRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId11 = showerRadioGroup.checkedRadioButtonId
            val selectedRadioButtonId12 = mealRadioGroup.checkedRadioButtonId


            gender = findViewById<RadioButton>(selectedRadioButtonId).text.toString()

            domitory = findViewById<RadioButton>(selectedRadioButtonId1).text.toString()
            cigarette = findViewById<RadioButton>(selectedRadioButtonId2).text.toString()
            alone = findViewById<RadioButton>(selectedRadioButtonId3).text.toString()
            sleepHabit1 = findViewById<RadioButton>(selectedRadioButtonId4).text.toString()
            sleepHabit2 = findViewById<RadioButton>(selectedRadioButtonId5).text.toString()
            wakeup = findViewById<RadioButton>(selectedRadioButtonId6).text.toString()
            sleep = findViewById<RadioButton>(selectedRadioButtonId7).text.toString()
            clean = findViewById<RadioButton>(selectedRadioButtonId8).text.toString()
            cleanRoom = findViewById<RadioButton>(selectedRadioButtonId9).text.toString()
            home = findViewById<RadioButton>(selectedRadioButtonId10).text.toString()
            shower = findViewById<RadioButton>(selectedRadioButtonId11).text.toString()
            meal = findViewById<RadioButton>(selectedRadioButtonId12).text.toString()


            auth.createUserWithEmailAndPassword(userEmail, userPwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 회원가입 성공
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        // 이메일 확인 메일 보내기
                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { verificationTask ->
                                if (verificationTask.isSuccessful) {
                                    // 이메일 확인 메일 전송 성공
                                    Toast.makeText(
                                        baseContext,
                                        "이메일 확인 메일이 전송되었습니다. 이메일을 확인해주세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // 사용자 정보를 Realtime Database에 저장
                                    val database = Firebase.database
                                    val usersRef = database.getReference("users")

                                    val userKey = usersRef.push().key
                                    val userMap = hashMapOf<String, Any>(
                                        "name" to nameing,
                                        "department" to department,
                                        "schoolnumber" to schoolnumber,
                                        "gender" to gender,
                                        "domitory" to domitory,
                                        "cigarette" to cigarette,
                                        "alone" to alone,
                                        "sleepHabit1" to sleepHabit1,
                                        "sleepHabit2" to sleepHabit2,
                                        "wakeup" to wakeup,
                                        "sleep" to sleep,
                                        "clean" to clean,
                                        "cleanRoom" to cleanRoom,
                                        "home" to home,
                                        "shower" to shower,
                                        "meal" to meal,
                                        "userEmail" to userEmail
                                    )

                                    if (userKey != null) {
                                        usersRef.child(userKey).setValue(userMap)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "User information saved in the database.")
                                            }
                                            .addOnFailureListener { exception ->
                                                Log.e(TAG, "Failed to save user information: $exception")
                                            }
                                    }


                                    // 회원가입에 성공했을 때 메인화면으로 이동하는 코드
                                    val intent = Intent(this@JoinActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // 이메일 확인 메일 전송 실패
                                    Log.w(TAG, "sendEmailVerification:failure", verificationTask.exception)
                                    Toast.makeText(
                                        baseContext,
                                        "이메일 확인 메일 전송에 실패했습니다. 오류: ${verificationTask.exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        // 회원가입 실패
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "회원가입에 실패했습니다. 오류: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    // 이메일 도메인 검증 함수
    private fun validateEmailDomain(email: String): Boolean {
        val domain = email.substringAfterLast("@")
        return domain.equals("kangwon.ac.kr", ignoreCase = true)
    }
}
