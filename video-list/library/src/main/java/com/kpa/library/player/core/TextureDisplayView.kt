package com.kpa.library.player.core

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.view.View
import com.kpa.library.player.controller.PlayerController

/**
 *
 * @author: kpa
 * @date: 2024/3/21
 * @description:
 */
class TextureDisplayView constructor(val context: Context) {
    private var reuseSurface = false
    private var surfaceListener: SurfaceListener? = null
    private var ttSurface: TextureSurface? = null


    interface SurfaceListener {
        fun onSurfaceAvailable(surface: Surface?, width: Int, height: Int)
        fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int)
        fun onSurfaceUpdated(surface: Surface?)
        fun onSurfaceDestroy(surface: Surface?)
    }

    fun getSurface(): Surface? {
        return ttSurface
    }

    fun setReuseSurface(reuseSurface: Boolean) {
        this.reuseSurface = reuseSurface
        if (!reuseSurface && !textureView.isAttachedToWindow && ttSurface != null) {
            if (surfaceListener != null) {
                surfaceListener!!.onSurfaceDestroy(ttSurface)
            }
            ttSurface!!.releaseDeep()
            ttSurface = null
        }
    }

    fun getDisplayView(): View {
        return textureView
    }

    fun isReuseSurface(): Boolean {
        return reuseSurface
    }

    fun setSurfaceListener(surfaceListener: SurfaceListener?) {
        this.surfaceListener = surfaceListener
    }


    private val textureView: TextureView by lazy {
        TextureView(context).apply {
            surfaceTextureListener = object : TextureView.SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    Log.d(PlayerController.TAG, "onSurfaceTextureAvailable ")
                    if (reuseSurface && ttSurface != null && ttSurface!!.isValid) {
                        ttSurface!!.getSurfaceTexture()?.let { textureView.setSurfaceTexture(it) }
                    } else {
                        ttSurface?.releaseDeep()
                        ttSurface = TextureSurface(surface)
                        Log.d(PlayerController.TAG, "onSurfaceTextureAvailable $ttSurface")
                    }
                    surfaceListener?.onSurfaceAvailable(ttSurface, width, height)
                }

                override fun onSurfaceTextureSizeChanged(
                    surface: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    surfaceListener?.onSurfaceSizeChanged(ttSurface, width, height)
                }

                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                    if (!reuseSurface) {
                        if (surfaceListener != null) {
                            surfaceListener!!.onSurfaceDestroy(ttSurface)
                        }
                        ttSurface?.releaseDeep()
                        ttSurface = null
                    }
                    return !reuseSurface
                }

                override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                    surfaceListener?.onSurfaceUpdated(ttSurface)
                }

            }
        }
    }

    init {

    }

    class TextureSurface(surface: SurfaceTexture) : Surface(surface) {
        private var surfaceTexture: SurfaceTexture? = null

        fun getSurfaceTexture(): SurfaceTexture? {
            return surfaceTexture
        }

        override fun release() {
            if (surfaceTexture != null) {
                super.release()
            }
        }

        fun releaseDeep() {
            super.release()
            surfaceTexture?.release()
        }


        override fun isValid(): Boolean {
            return super.isValid()
        }
    }


}