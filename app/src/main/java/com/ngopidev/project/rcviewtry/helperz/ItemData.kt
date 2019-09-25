package com.ngopidev.project.rcviewtry.helperz

import com.google.gson.annotations.SerializedName


/**
 *   created by Irfan Assidiq on 2019-09-25
 *   email : assidiq.irfan@gmail.com
 **/
class ItemData {
    @SerializedName("id")
    var id : Int? = null

    @SerializedName("name")
    var name : String? = null

    @SerializedName("description")
    var description : String? = null

    @SerializedName("price")
    var price : Int? = null

    @SerializedName("thumbnail")
    var thumbnail : String? = null
}