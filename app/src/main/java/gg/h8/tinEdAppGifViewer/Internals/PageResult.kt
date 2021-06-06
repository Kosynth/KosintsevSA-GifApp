package gg.h8.tinEdAppGifViewer.Internals

import com.google.gson.annotations.SerializedName

data class PageResult<T>(
    var result: ArrayList<MainPaneItem>
) {

}