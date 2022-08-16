package com.polymorfuz.kwadiary.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.polymorfuz.kwadiary.beans.Enums
import com.polymorfuz.kwadiary.helpers.default

open class BaseViewModel(app: Application) : AndroidViewModel(app) {
    var state = MutableLiveData<Enums.PageState>().default(Enums.PageState.LOADING)
    var errorType = MutableLiveData<Enums.ErrorType>().default(Enums.ErrorType.NONE)

}