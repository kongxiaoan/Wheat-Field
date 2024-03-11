package com.kpa.video.playerkit.base

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import android.view.View
import androidx.annotation.IntDef

abstract class DisplayView {
    @MustBeDocumented
    @Retention(AnnotationRetention.SOURCE)
    @IntDef(DISPLAY_VIEW_TYPE_NONE, DISPLAY_VIEW_TYPE_TEXTURE_VIEW, DISPLAY_VIEW_TYPE_SURFACE_VIEW)
    annotation class DisplayViewType

    abstract fun getViewType(): Int

    abstract fun getDisplayView(): View?

    abstract fun getSurface(): Surface?

    abstract fun setReuseSurface(reuseSurface: Boolean)

    abstract fun isReuseSurface(): Boolean

    abstract fun setSurfaceListener(surfaceListener: SurfaceListener)


    /**
     * 接口用于监听与 Surface 相关的事件。
     */
    interface SurfaceListener {
        /**
         * 当 Surface 可用时调用。
         *
         * @param surface 可用的 Surface 对象
         * @param width   Surface 的宽度
         * @param height  Surface 的高度
         */
        fun onSurfaceAvailable(surface: Surface?, width: Int, height: Int)

        /**
         * 当 Surface 大小发生变化时调用。
         *
         * @param surface 变化的 Surface 对象
         * @param width   新的 Surface 宽度
         * @param height  新的 Surface 高度
         */
        fun onSurfaceSizeChanged(surface: Surface?, width: Int, height: Int)

        /**
         * 当 Surface 更新时调用。
         *
         * @param surface 更新的 Surface 对象
         */
        fun onSurfaceUpdated(surface: Surface?)

        /**
         * 当 Surface 被销毁时调用。
         *
         * @param surface 被销毁的 Surface 对象
         */
        fun onSurfaceDestroy(surface: Surface?)
    }


    internal class SurfaceDisplayView(context: Context?) : DisplayView() {
        var surfaceView: SurfaceView
        override fun getViewType(): Int {
            return DISPLAY_VIEW_TYPE_SURFACE_VIEW
        }

        override fun getDisplayView(): View {
            return surfaceView
        }

        override fun getSurface(): Surface? {
            return surfaceView.holder.surface
        }

        override fun setReuseSurface(reuseSurface: Boolean) {}
        override fun isReuseSurface(): Boolean {
            return false
        }

        private var surfaceListener: SurfaceListener? = null

        init {
            surfaceView = SurfaceView(context)
            surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    if (surfaceListener == null) return
                    surfaceListener!!.onSurfaceAvailable(
                        holder.surface,
                        surfaceView.width,
                        surfaceView.height
                    )
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                    if (surfaceListener == null) return
                    surfaceListener!!.onSurfaceSizeChanged(holder.surface, width, height)
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                    if (surfaceListener == null) return
                    surfaceListener!!.onSurfaceDestroy(holder.surface)
                }
            })
        }

        override fun setSurfaceListener(surfaceListener: SurfaceListener) {
            this.surfaceListener = surfaceListener
        }
    }

    internal class TextureDisplayView(context: Context?) : DisplayView() {
        private val textureView: TextureView
        private var ttSurface: TextureSurface? = null
        private var surfaceListener: SurfaceListener? = null
        private var reuseSurface = false

        init {
            textureView = TextureView(context!!)
            textureView.surfaceTextureListener = object : SurfaceTextureListener {
                override fun onSurfaceTextureAvailable(
                    surfaceTexture: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    if (reuseSurface && ttSurface != null && ttSurface!!.isValid) {
                        textureView.setSurfaceTexture(ttSurface!!.surfaceTexture!!)
                    } else {
                        if (ttSurface != null) {
                            ttSurface!!.releaseDeep()
                        }
                        ttSurface = TextureSurface(surfaceTexture)
                    }
                    if (surfaceListener != null) {
                        surfaceListener!!.onSurfaceAvailable(ttSurface, width, height)
                    }
                }

                override fun onSurfaceTextureSizeChanged(
                    surfaceTexture: SurfaceTexture,
                    width: Int,
                    height: Int
                ) {
                    if (surfaceListener != null) {
                        surfaceListener!!.onSurfaceSizeChanged(ttSurface, width, height)
                    }
                }

                override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
                    if (!reuseSurface) {
                        if (surfaceListener != null) {
                            surfaceListener!!.onSurfaceDestroy(ttSurface)
                        }
                        ttSurface!!.releaseDeep()
                        ttSurface = null
                    }
                    return !reuseSurface
                }

                override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {
                    if (surfaceListener != null) {
                        surfaceListener!!.onSurfaceUpdated(ttSurface)
                    }
                }
            }
        }

        override fun getSurface(): Surface? {
            return ttSurface
        }

        override fun setReuseSurface(reuseSurface: Boolean) {
            this.reuseSurface = reuseSurface
            if (!reuseSurface && !textureView.isAttachedToWindow && ttSurface != null) {
                if (surfaceListener != null) {
                    surfaceListener!!.onSurfaceDestroy(ttSurface)
                }
                ttSurface!!.releaseDeep()
                ttSurface = null
            }
        }

        override fun isReuseSurface(): Boolean {
            return reuseSurface
        }

        override fun getViewType(): Int {
            return DISPLAY_VIEW_TYPE_TEXTURE_VIEW
        }

        override fun getDisplayView(): View {
            return textureView
        }

        override fun setSurfaceListener(surfaceListener: SurfaceListener) {
            this.surfaceListener = surfaceListener
        }

        internal class TextureSurface @SuppressLint("Recycle") constructor(var surfaceTexture: SurfaceTexture?) :
            Surface(
                surfaceTexture
            ) {
            override fun release() {
                if (surfaceTexture != null) {
                    super.release()
                    surfaceTexture = null
                }
            }

            fun releaseDeep() {
                if (surfaceTexture != null) {
                    super.release()
                    surfaceTexture!!.release()
                    surfaceTexture = null
                }
            }

            override fun isValid(): Boolean {
                return super.isValid() && surfaceTexture != null
            }
        }
    }

    companion object {
        const val DISPLAY_VIEW_TYPE_NONE = -1
        const val DISPLAY_VIEW_TYPE_TEXTURE_VIEW = 0
        const val DISPLAY_VIEW_TYPE_SURFACE_VIEW = 1
        fun create(context: Context?, @DisplayViewType displayType: Int): DisplayView {
            return if (displayType == DISPLAY_VIEW_TYPE_SURFACE_VIEW) {
                SurfaceDisplayView(context)
            } else {
                TextureDisplayView(context)
            }
        }
    }
}
