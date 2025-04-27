package com.example.roomeme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class PandaActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var postEditText: EditText
    private lateinit var postButton: Button
    private lateinit var postsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panda)

        // FirebaseAuth 및 FirebaseFirestore 인스턴스 초기화
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        postEditText = findViewById(R.id.postEditText)
        postButton = findViewById(R.id.postButton)
        postsTextView = findViewById(R.id.postsTextView)

        // 게시 버튼 클릭 이벤트 처리
        postButton.setOnClickListener {
            val postText = postEditText.text.toString()
            if (postText.isNotEmpty()) {
                addPost(postText)
                postEditText.text.clear()
            }
        }

        // 게시글 변경 내용 실시간으로 감지하여 화면에 업데이트
        firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING) // timestamp 필드를 내림차순으로 정렬
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    // 에러 처리
                    return@addSnapshotListener
                }

                val stringBuilder = StringBuilder()
                for (document in snapshot!!) {
                    val post = document.getString("post")
                    stringBuilder.append("$post\n\n")
                }

                postsTextView.text = stringBuilder.toString()
            }
    }

    private fun addPost(postText: String) {
        val post = hashMapOf(
            "post" to postText,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("posts")
            .add(post)
            .addOnSuccessListener {
                // 게시글 추가 성공
            }
            .addOnFailureListener {
                // 게시글 추가 실패
            }
    }
}