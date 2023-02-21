package com.tomerpacific.jetpackcomposetabs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val tabIndex: MutableLiveData<Int> = MutableLiveData(0)
    val index: LiveData<Int> = tabIndex
    val tabs = listOf("Home", "About", "Settings")

    fun updateTabIndexBasedOnSwipe(isSwipeToTheLeft: Boolean) {
        tabIndex.value = when (isSwipeToTheLeft) {
            true -> Math.floorMod(tabIndex.value!!.plus(1), tabs.size)
            false -> Math.floorMod(tabIndex.value!!.minus(1), tabs.size)
        }
    }

    fun updateTabIndex(i: Int) {
        tabIndex.value = i
    }

}