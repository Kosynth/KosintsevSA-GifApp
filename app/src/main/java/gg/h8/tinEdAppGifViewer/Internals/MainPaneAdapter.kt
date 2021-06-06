package gg.h8.tinEdAppGifViewer.Internals

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import gg.h8.tinEdAppGifViewer.MainActivity
import gg.h8.tinEdAppGifViewer.R


class MainPaneAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
lateinit var itemSet:ArrayList<MainPaneItem>

 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
  return MainPaneHolder(
   LayoutInflater.from(parent.context).inflate(R.layout.mainpane_layout,parent,false)
  )
 }

 override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
  when(holder){
   is MainPaneHolder ->{holder.bind(itemSet[position])}
  }
 }


 override fun getItemCount(): Int {
  return itemSet.size
 }

 class MainPaneHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView)
 {
  val media= itemView.findViewById<ImageView>(R.id.media)
  val mediaTitle= itemView.findViewById<TextView>(R.id.media_title)
  val mediaAuthor = itemView.findViewById<TextView>(R.id.media_author)
  val mediaDate = itemView.findViewById<TextView>(R.id.date)
  val mediaVotes = itemView.findViewById<TextView>(R.id.votes)

  fun bind(mainPaneItem: MainPaneItem){
   mediaTitle.text = mainPaneItem.description
   mediaAuthor.text = mainPaneItem.author
   mediaDate.text = mainPaneItem.date
   mediaVotes.text = mainPaneItem.votes
   val options = RequestOptions().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background)

   Glide.with(itemView.context).applyDefaultRequestOptions(options).load(mainPaneItem.gifURL).into(media)

  }
 }
}