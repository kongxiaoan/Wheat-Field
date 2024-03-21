package com.kpa.library.page

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kpa.library.base.VideoViewFactory
import com.kpa.library.core.VideoView
import com.kpa.library.page.model.VideoPageItem
import com.kpa.library.player.controller.PlayerController
import com.kpa.library.player.core.MediaSource

/**
 *
 * @author: kpa
 * @date: 2024/3/20
 * @description:
 */
class VideoPageAdapter : RecyclerView.Adapter<VideoPageAdapter.ViewHolder>() {

    private val mItems = mutableListOf<VideoPageItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(videoItems: List<VideoPageItem>) {
        mItems.clear()
        mItems.addAll(videoItems)
        notifyDataSetChanged()
    }

    fun prependItems(videoItems: List<VideoPageItem>) {
        if (videoItems.isNotEmpty()) {
            mItems.addAll(0, videoItems)
            notifyItemRangeInserted(0, videoItems.size)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun appendItems(videoItems: List<VideoPageItem>) {
        if (videoItems.isNotEmpty()) {
            val count = mItems.size
            mItems.addAll(videoItems)
            if (count > 0) {
                notifyItemRangeInserted(count, mItems.size)
            } else {
                notifyDataSetChanged()
            }
        }
    }

    fun deleteItem(position: Int) {
        if (position >= 0 && position < mItems.size) {
            mItems.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun replaceItem(position: Int, videoItem: VideoPageItem) {
        if (0 <= position && position < mItems.size) {
            mItems[position] = videoItem
            notifyItemChanged(position)
        }
    }

    fun getItem(position: Int): VideoPageItem {
        return mItems[position]
    }

    fun getItems(): List<VideoPageItem> {
        return mItems
    }


    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        holder.videoView?.stopPlayback()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.videoView?.stopPlayback()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var videoView: VideoView? = null

        init {
            videoView = itemView as VideoView
            videoView?.setLayoutParams(
                RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT
                )
            )
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                return ViewHolder(VideoViewFactory.DEFAULT.createVideoView(parent.context).apply {
                    layoutParams = RecyclerView.LayoutParams(
                        RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.MATCH_PARENT
                    )
                })
            }
        }

        fun bind(videoPageItem: VideoPageItem) {
            var mediaSource: MediaSource? = videoView?.getSource()
            if (mediaSource == null) {
                // 此处应该做数据转换
                mediaSource = MediaSource()
                Log.d(PlayerController.TAG, "mediaSource = $mediaSource")
                videoView?.bindSource(mediaSource)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoPageItem = mItems[position]
        holder.bind(videoPageItem)
    }

}