package com.tomerpacific.locationpermissions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _shouldShowPermissionRationale: MutableLiveData<Boolean> = MutableLiveData(false)
    val shouldShowPermissionRationale: LiveData<Boolean> = _shouldShowPermissionRationale

    fun setShouldShowPermissionRationale(state: Boolean) {
        _shouldShowPermissionRationale.value = state
    }

}