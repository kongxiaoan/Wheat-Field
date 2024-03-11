package com.kpa.video.uikit.video

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kpa.video.common.log.VideoLogUtil
import com.kpa.video.playerkit.base.VideoLayerHost
import com.kpa.video.playerkit.base.VideoView
import com.kpa.video.uikit.model.VideoItem
import com.kpa.video.uikit.video.layer.ConvertLayer
import com.kpa.video.uikit.video.layer.SimpleTitleLayer

/**
 *
 * @author: kpa
 * @date: 2024/3/1
 * @description:
 */
class ShortVideoAdapter : RecyclerView.Adapter<ShortVideoAdapter.ViewHolder>() {
    private var mItems: MutableList<VideoItem> = mutableListOf()
    private var mContext: Context? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(videoItems: MutableList<VideoItem>) {
        mItems.clear()
        mItems.addAll(videoItems)
        notifyDataSetChanged()
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var videoView: VideoView? = null

        init {
            videoView = itemView as VideoView
            videoView?.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
        }

        fun bind(position: Int, videoItem: VideoItem) {
            var dataSource = videoView?.getDataSource()
            if(dataSource == null) {
                dataSource = VideoItem.toMediaSource(videoItem)
                dataSource?.let { videoView?.bindDataSource(it) }
            }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val videoView: VideoView = createVideoView(parent)
                videoView.layoutParams = RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT
                )
                return ViewHolder(videoView)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        return ViewHolder.create(parent)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        // 列表从屏幕滚出视野或者被移除时
        VideoLogUtil.d("onViewDetachedFromWindow")
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        VideoLogUtil.d("onViewRecycled")
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoItem = mItems[position]
        holder.bind(position, videoItem)
    }

    companion object {
        fun createVideoView(parent: ViewGroup): VideoView {
            val videoView = VideoView(parent.context)
            val layerHost = VideoLayerHost(parent.context)
            layerHost.addLayer(ConvertLayer())
            layerHost.addLayer(SimpleTitleLayer())
            layerHost.attachToVideoView(videoView)
            videoView.setBackgroundColor(parent.resources.getColor(R.color.holo_red_dark))
            return videoView
        }
    }
}