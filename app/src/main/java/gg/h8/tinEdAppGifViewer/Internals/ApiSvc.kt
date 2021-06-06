package gg.h8.tinEdAppGifViewer.Internals

import retrofit2.http.GET
import retrofit2.Call

interface ApiSvc {
    @GET("/random?json=true")
    fun getNext():Call<MainPaneItem>
}