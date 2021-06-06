package gg.h8.tinEdAppGifViewer.Internals

import android.util.Log
import androidx.viewpager.widget.PagerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MainList: ArrayList<MainPaneItem>() {
    var list = ArrayList<MainPaneItem>()
        fun requestNew(quantity:Int=1, m: MainPaneAdapter): ArrayList<MainPaneItem> {
            list.clear()
            val retrofit= Retrofit.Builder().baseUrl("https://developerslife.ru").addConverterFactory(
                GsonConverterFactory.create()).build()
            for (i in 1..quantity) {
                val api=retrofit.create(ApiSvc::class.java)
                api.getNext().enqueue(object: Callback<MainPaneItem> {
                    override fun onResponse(call: Call<MainPaneItem>, response: Response<MainPaneItem>) {
                        response.body()?.let { m.itemSet.add(it)
                        m.notifyItemInserted(m.itemSet.size)}
                        Log.d("List", "${list.size}")
                        Log.d("getter", "onResponse: ${response.body()?.author}")
                    }

                    override fun onFailure(call: Call<MainPaneItem>, t: Throwable) {
                        Log.d("getter", "onFailure")
                    }
                })
                Log.d("List", "${list.size}")

            }
            Log.d("List", "${list.size}")
            return list
        }
    fun requestNewLatestPage(quantity:Int=1, m: MainPaneAdapter): ArrayList<MainPaneItem> {
        list.clear()
        val retrofit= Retrofit.Builder().baseUrl("https://developerslife.ru").addConverterFactory(
            GsonConverterFactory.create()).build()
        for (i in 1..quantity) {
            val api=retrofit.create(ApiSvc::class.java)
            api.getLatestPage().enqueue(object: Callback<PageResult<MainPaneItem>> {
                override fun onResponse(call: Call<PageResult<MainPaneItem>>, response: Response<PageResult<MainPaneItem>>) {
                    response.body()?.let { m.itemSet.addAll(it.result)
                        m.notifyDataSetChanged() }
                    Log.d("List", "${list.size}")
                    Log.d("getter", "onResponse:}")
                }

                override fun onFailure(call: Call<PageResult<MainPaneItem>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })

        }
        Log.d("List", "${list.size}")
        return list
    }

}