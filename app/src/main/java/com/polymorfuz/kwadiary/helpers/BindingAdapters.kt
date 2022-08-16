package com.polymorfuz.kwadiary.helpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.polymorfuz.kwadiary.beans.Enums
import com.polymorfuz.kwadiary.utils.Utils


@BindingAdapter("setTextWithVisibility")
fun setTextWithVisibility(view: TextView, text: String?) {
    if (!text.isNullOrBlank()) {
        view.visibility = View.VISIBLE
        view.text = text
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setDefaultVisibility")
fun setDefaultVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.DEFAULT || state == Enums.PageState.SUCCESS)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setLoadingVisibility")
fun setLoadingVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.LOADING)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setErrorVisibility")
fun setErrorVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.ERROR)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setEmptyVisibility")
fun setEmptyVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.EMPTY)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("setOverlayVisibility")
fun setOverlayVisibility(view: View, state: Enums.PageState) {
    if (state == Enums.PageState.OVERLAY)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.GONE
}

@BindingAdapter("app:srcVector")
fun setSrcVector(view: ImageView, @DrawableRes drawable: Int) {
    view.setImageResource(drawable)
}

@BindingAdapter("setInt")
fun setInt(view: TextView, data: Int) {
    view.text = data.toString()
}

@BindingAdapter("setImageItemVisibility")
fun setTextItemVisibility(view: TextView, data: Boolean) {
    if (data) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setAmountPattern")
fun setAmountPattern(view: TextView, data: String) {
    view.text = "$data/-"
}


@BindingAdapter("setStringData")
fun setStringData(view: TextView, data: String) {
    view.text = "$data"
}

@BindingAdapter("setFavStatus")
fun setFavStatus(view: ImageView, data: String?) {
    if (data != null) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setAppliedStatus")
fun setAppliedStatus(view: ImageView, data: String?) {
    if (data != null) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("setCamelCase")
fun setCamelCase(view: TextView, data: String?) {
    if (data != null) {
        view.text = Utils.capitalizeWords(data)
    }
}
