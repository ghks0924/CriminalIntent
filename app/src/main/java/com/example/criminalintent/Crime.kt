package com.example.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Crime(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = id.toString().substring(0,5),
    var date: Date = Date(), //현재일자로 초기화
    var isSolved: Boolean = false,
    )