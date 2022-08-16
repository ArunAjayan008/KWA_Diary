package com.polymorfuz.kwadiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.base.BaseViewModel
import com.polymorfuz.kwadiary.beans.DivNodeModel

class DivisionContactViewmodel(app: Application) : BaseViewModel(app) {
    val event = MutableLiveData<DivisionContactEvent>()
    var nodeList = arrayListOf<DivNodeModel>()


    enum class DivisionContactEvent {
        SUCCESS, FAILED
    }
}