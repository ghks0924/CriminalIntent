package com.example.criminalintent

import java.util.*

data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(), //현재일자로 초기화
    var isSolved: Boolean = false,
    )