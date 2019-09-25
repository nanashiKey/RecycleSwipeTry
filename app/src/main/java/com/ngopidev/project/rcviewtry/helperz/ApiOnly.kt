package com.ngopidev.project.rcviewtry.helperz

import retrofit2.Call
import retrofit2.http.GET


/**
 *   created by Irfan Assidiq on 2019-09-25
 *   email : assidiq.irfan@gmail.com
 **/
interface ApiOnly {
    @GET("menu.json")
    fun getAllData() : Call<MutableList<ItemData>>
}