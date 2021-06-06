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
    private val snapHelper: SnapHelper = PagerSnapHelper()
    val mainList=MainList()
    val mainPaneAdapter=MainPaneAdapter()
    var pos = 0

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
        val clickListener: View.OnClickListener = View.OnClickListener {
            if (it.id == R.id.nextButton){
                Log.d("ButtonPress", "Next Button is pressed")
                getNext();
            }
            else if (it.id == R.id.backButton){
                goBack()
                Log.d("ButtonPress", "Back Button is pressed")
                //goBack()
            }
        }
        recyclerView.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }
        })
        nextButton.setOnClickListener(clickListener)
        backButton.setOnClickListener(clickListener)
        var an=ObjectAnimator.ofFloat(backButton, "translationY", 1000f,)
        an.duration = 2000
        an.start();

    }

    override fun onResume() {
        super.onResume()


    }

    private fun getNext(quantity:Int=1){
        mainList.requestNew(quantity,mainPaneAdapter)
        val totalItemCount = recyclerView.adapter!!.itemCount
        if (totalItemCount <= 0) return
        val lastVisibleItemIndex: Int = linearLayoutManager.findLastVisibleItemPosition()
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


}