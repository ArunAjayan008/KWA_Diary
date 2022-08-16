package com.polymorfuz.kwadiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.base.BaseViewModel

class SubDivContactViewmodel (app: Application) : BaseViewModel(app) {
    val event = MutableLiveData<SubDivContactEvents>()


    enum class SubDivContactEvents { SUCCESS, FAILED }
}