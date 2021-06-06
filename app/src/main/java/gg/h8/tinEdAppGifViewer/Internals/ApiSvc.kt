package gg.h8.tinEdAppGifViewer.Internals

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiSvc {

    @GET("/random?json=true")
    fun getNext():Call<MainPaneItem>

    @GET("/latest/0?json=true")
    fun getLatestPage():Call<PageResult<MainPaneItem>>

    @GET("/hot/0?json=true")
    fun getHotPage():Call<PageResult<MainPaneItem>>

    @GET("/top/0?json=true")
    fun getTopPage():Call<PageResult<MainPaneItem>>


}