package com.android.popup.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.popup.DialogParams
import com.android.popup.WindowHelper
import com.android.popup.interfaces.IWindow
import com.android.popup.interfaces.OnWindowDismissListener
import com.android.popup.interfaces.OnWindowShowListener

/**
 *
 * @author: kpa
 * @date: 2024/3/18
 * @description:
 */
abstract class BaseDialogActivity : AppCompatActivity(), IWindow {
    private var dialogParams: DialogParams? = null
    abstract fun layoutId(): Int
    abstract fun buildTargetClass(): BaseDialogActivity
    abstract fun render(dialogParams: DialogParams?)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.dialogParams = intent.getSerializableExtra("dialogParams") as? DialogParams
        enableEdgeToEdge()
        setContentView(layoutId())
        render(dialogParams)
    }

    override fun onResume() {
        WindowHelper.isActivityShow = true
        super.onResume()
    }

    override fun onPause() {
        WindowHelper.isActivityShow = false
        super.onPause()
    }

    override fun show(activity: Activity, dialogParams: DialogParams?) {
        activity.startActivity(Intent(activity, buildTargetClass().javaClass).apply {
            putExtra("dialogParams", dialogParams)
        })
    }

    override fun dismiss() {
        finish()
    }

    override fun isShowing(): Boolean {
        return WindowHelper.isActivityShow
    }


    override fun onDestroy() {
        super.onDestroy()
        WindowHelper.activityDismissListener?.onDismiss()
    }

    override fun setOnWindowDismissListener(listener: OnWindowDismissListener) {
        WindowHelper.activityDismissListener = listener
    }

    override fun setOnWindowShowListener(listener: OnWindowShowListener) {

    }
}