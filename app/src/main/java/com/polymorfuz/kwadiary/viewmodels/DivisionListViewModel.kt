package com.polymorfuz.kwadiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.base.BaseViewModel

class DivisionListViewModel(app: Application) : BaseViewModel(app) {
    val event = MutableLiveData<DivisionListEvent>()

    enum class DivisionListEvent {
        SUCCESS, FAILED
    }
}