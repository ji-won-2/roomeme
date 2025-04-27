package com.example.roomeme.model

// 파이어베이스
// RealTime Database - Users 의 단일 객체 모델
data class User(
    var alone: String = "",
    var cigarette: String = "",
    var clean: String = "",
    var cleanRoom: String = "",
    var department: String = "",
    var domitory: String = "",
    var gender: String = "",
    var home: String = "",
    var meal: String = "",
    var name: String = "",
    var schoolnumber: String = "",
    var shower: String = "",
    var wakeup: String = "",
    var sleep: String = "",
    var sleepHabit1: String = "",
    var sleepHabit2: String = "",
    var userEmail: String = "",
    // 매칭률 계산하기 위한 값
    var matchCount: Int = 0,
    var matchPercent: Int = 0,
    var isRequirementSatisfied: Boolean = false,
)
