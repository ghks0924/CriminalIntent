package com.example.criminalintent

import androidx.lifecycle.ViewModel

class CrimeListViewModel: ViewModel() {

    //repository 사용으로 주석처리
//    val crimes = mutableListOf<Crime>()
//
//    init {
//        for(i in 0 until 100){
//            val crime = Crime()
//            crime.title = "Crime #$i"
//            crime.isSolved = i % 2 == 0
//            crimes += crime
//        }
//    }

    private val crimeRepository = CrimeRepository.get()
//    val crimes = crimeRepository.getCrimes()

    fun insertCrime (crime: Crime) {
        crimeRepository.insertCrime(crime)
    }
    val crimeListLiveData = crimeRepository.getCrimes()
}
