package com.polymorfuz.kwadiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.base.BaseViewModel

class SecConViewmodel (app: Application) : BaseViewModel(app) {
    val event = MutableLiveData<SecConEvents>()


    enum class SecConEvents { SUCCESS, FAILED }
}