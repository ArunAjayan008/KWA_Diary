package com.polymorfuz.kwadiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.base.BaseViewModel

class MainActivityViewModel(app: Application) : BaseViewModel(app) {
    val event = MutableLiveData<MainActivityEvents>()


    enum class MainActivityEvents { SUCCESS, FAILED }
}