package com.polymorfuz.kwadiary.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.base.BaseViewModel
import com.polymorfuz.kwadiary.beans.DivNodeModel

class SearchViewmodel (app: Application) : BaseViewModel(app) {
    val event = MutableLiveData<SearchEvent>()
    var nodeList = arrayListOf<DivNodeModel>()


    enum class SearchEvent {
        SUCCESS, FAILED
    }
}