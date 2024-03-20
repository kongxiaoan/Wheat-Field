package com.kpa.popupsimple.simple

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.popup.DialogParams
import com.android.popup.PopupManager
import com.android.popup.WindowHelper
import com.android.popup.base.BaseDialogActivity
import com.android.popup.interfaces.IWindow
import com.android.popup.interfaces.OnWindowDismissListener
import com.android.popup.interfaces.OnWindowShowListener
import com.kpa.popupsimple.R

class SimpleDialogActivity : BaseDialogActivity(), IWindow {
    override fun layoutId(): Int {
        return R.layout.activity_simple_dialog
    }

    override fun buildTargetClass(): BaseDialogActivity {
        return this
    }

    override fun render(dialogParams: DialogParams?) {
        Log.d(PopupManager.TAG, "dialogParams = ${dialogParams.toString()}")
        findViewById<TextView>(R.id.test).text = dialogParams?.content ?: "是空的 enen"
    }
}