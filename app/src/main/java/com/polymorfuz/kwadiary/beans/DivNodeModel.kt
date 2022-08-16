package com.polymorfuz.kwadiary.beans

data class DivNodeModel(
    var contact: List<ContactModel>?=null,
    var district: String?=null,
    var division: String?=null,
    var section: String?=null,
    var subdivision: String?=null,
    var type: String?=null
)
