package gg.h8.tinEdAppGifViewer

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import gg.h8.tinEdAppGifViewer.Internals.MainPaneAdapter
import gg.h8.tinEdAppGifViewer.Internals.MainList
import gg.h8.tinEdAppGifViewer.Internals.MainPaneItem


class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var hotButton: Button
    private lateinit var latestButton: Button
    private lateinit var bestButton: Button
    private val snapHelper: SnapHelper = PagerSnapHelper()
    val mainList=MainList()
    val mainPaneAdapter=MainPaneAdapter()
    var pos = 0
    var page="random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initMainPane()
        mainPaneAdapter.itemSet= ArrayList<MainPaneItem>()
        recyclerView=findViewById<RecyclerView>(R.id.main_pane_recycler)
        snapHelper.attachToRecyclerView(recyclerView)
        mainList.requestNew(5,mainPaneAdapter)

        nextButton = findViewById(R.id.nextButton)
        backButton= findViewById(R.id.backButton)
        hotButton= findViewById(R.id.hotButton)
        latestButton= findViewById(R.id.latestButton)
        bestButton= findViewById(R.id.bestButton)
        val clickListener: View.OnClickListener = View.OnClickListener {
            if (it.id == R.id.nextButton){
                Log.d("ButtonPress", "Next Button is pressed")
                getNext();
            }
            else if (it.id == R.id.backButton){
                goBack()
                Log.d("ButtonPress", "Back Button is pressed")
            }
            if (it.id == R.id.hotButton){
                Log.d("ButtonPress", "Hot Button is pressed")
                page="hot";
                cls()

            }
            if (it.id == R.id.latestButton){
                Log.d("ButtonPress", "latest latest is pressed")
                page="latest"
                cls()
                mainList.requestNewLatestPage(1,mainPaneAdapter)
            }
            if (it.id == R.id.bestButton){
                Log.d("ButtonPress", "Best Button is pressed")
                page="best"
                cls()
            }
        }
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }
        })
        nextButton.setOnClickListener(clickListener)
        backButton.setOnClickListener(clickListener)
        hotButton.setOnClickListener(clickListener)
        bestButton.setOnClickListener(clickListener)
        latestButton.setOnClickListener(clickListener)
        var an=ObjectAnimator.ofFloat(backButton, "translationY", 1000f,)
        an.duration = 2000
        an.start();

    }

    private fun getNext(quantity:Int=1){
        val lastVisibleItemIndex: Int = linearLayoutManager.findLastVisibleItemPosition()
        if (lastVisibleItemIndex-5 < recyclerView.adapter!!.itemCount){
            mainList.requestNew(quantity,mainPaneAdapter)
        }
        val totalItemCount = recyclerView.adapter!!.itemCount
        if (totalItemCount <= 0) return
        if (lastVisibleItemIndex >= totalItemCount) return
        linearLayoutManager.smoothScrollToPosition(recyclerView, null, lastVisibleItemIndex)
        if (lastVisibleItemIndex>0){
            backButton.isClickable = true
            var an=ObjectAnimator.ofFloat(backButton, "translationY", 0f)
            an.duration = 2000
            an.start()
        }
    }
    private fun goBack(){
        val firstVisibleItemIndex: Int = linearLayoutManager.findFirstVisibleItemPosition()
        if (firstVisibleItemIndex==0 ){
            backButton.isClickable = false
            var an=ObjectAnimator.ofFloat(backButton, "translationY", 1000f,)
            an.duration = 2000
            an.start()
        }else{}
        if (firstVisibleItemIndex < 0) return
        linearLayoutManager.smoothScrollToPosition(recyclerView, null, firstVisibleItemIndex)
    }
    private fun initMainPane(){
        findViewById<RecyclerView>(R.id.main_pane_recycler).apply {
            layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL, false)
            linearLayoutManager= layoutManager as LinearLayoutManager
            adapter=mainPaneAdapter
        }

        }
    private fun cls(){
        var ic=recyclerView.adapter!!.itemCount
        mainPaneAdapter.itemSet.clear()
        mainPaneAdapter.notifyItemRangeRemoved(0,ic)
    }


}