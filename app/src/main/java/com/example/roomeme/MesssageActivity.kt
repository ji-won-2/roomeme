package com.example.roomeme

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MesssageActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var chatAdapter: ArrayAdapter<String>
    private var userId: String? = null // 사용자 식별자

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messsage)

        // 사용자 식별자 선택을 위한 다이얼로그 표시
        showUserSelectionDialog()

        // Firestore 초기화
        firestore = FirebaseFirestore.getInstance()
    }

    private fun showUserSelectionDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val input = EditText(this)

        dialogBuilder.setTitle("사용자 선택")
        dialogBuilder.setMessage("사용자 ID를 입력하세요:")
        dialogBuilder.setView(input)

        dialogBuilder.setPositiveButton("확인") { _, _ ->
            val selectedUserId = input.text.toString()

            if (selectedUserId.isNotBlank()) {
                // 사용자 선택 후 초기화 및 채팅 로직 실행
                initializeChat(selectedUserId)
            } else {
                // ID가 입력되지 않은 경우 다이얼로그 재표시
                showUserSelectionDialog()
            }
        }

        dialogBuilder.setCancelable(false)
        dialogBuilder.show()
    }

    private fun initializeChat(selectedUserId: String) {
        userId = selectedUserId // 선택한 사용자 ID를 userId 변수에 할당합니다.

        val chatListView = findViewById<ListView>(R.id.chatListView)
        val sendButton = findViewById<Button>(R.id.sendButton)
        val messageEditText = findViewById<EditText>(R.id.messageEditText)

        // 채팅 어댑터 초기화
        chatAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        chatListView.adapter = chatAdapter

        // 메시지 전송 버튼 클릭 시
        sendButton.setOnClickListener {
            val message = messageEditText.text.toString()

            if (message.isNotBlank() && userId != null) {
                sendMessage(message, userId!!)
                messageEditText.text.clear()
            }
        }

        // Firestore에서 채팅 메시지 받아오기
        firestore.collection("chat")
            .document(userId!!)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    // 오류 처리
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messages = snapshot.documents.mapNotNull { document ->
                        document.getString("message")
                    }

                    chatAdapter.clear()
                    chatAdapter.addAll(messages)
                }
            }
    }

    private fun sendMessage(message: String, userId: String) {
        val data = hashMapOf(
            "message" to message,
            "timestamp" to System.currentTimeMillis(),
            "userId" to userId
        )

        firestore.collection("chat")
            .document(userId)
            .collection("messages")
            .add(data)
            .addOnSuccessListener { documentReference ->
                // 전송 성공 처리
            }
            .addOnFailureListener { exception ->
                // 오류 처리
            }
    }
}