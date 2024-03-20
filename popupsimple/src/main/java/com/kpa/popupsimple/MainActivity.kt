package com.kpa.popupsimple

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.popup.DialogParams
import com.android.popup.PopupManager
import com.android.popup.WindowType
import com.android.popup.WindowWrapper
import com.kpa.popupsimple.simple.SimpleDialogActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val dialogActivity = SimpleDialogActivity()
        PopupManager.addWindow(
            WindowWrapper.Builder()
                .setPriority(10)
                .setWindowType(WindowType.ACTIVITY)
                .setWindow(dialogActivity)
                .setDialogParams(
                    DialogParams.Builder()
                        .setContent("我是测试内容")
                        .build()
                )
                .setWindowName(dialogActivity.javaClass.name)
                .setCanShow(true)
                .build()
        )
        PopupManager.addWindow(
            WindowWrapper.Builder()
                .setPriority(12)
                .setWindowType(WindowType.ACTIVITY)
                .setWindow(dialogActivity)
                .setWindowName(dialogActivity.javaClass.name)
                .setCanShow(true)
                .build()
        )

        PopupManager.addWindow(
            WindowWrapper.Builder()
                .setPriority(3)
                .setWindowType(WindowType.ACTIVITY)
                .setWindow(dialogActivity)
                .setWindowName(dialogActivity.javaClass.name)
                .setCanShow(true)
                .build()
        )

        PopupManager.addWindow(
            WindowWrapper.Builder()
                .setPriority(1)
                .setWindowType(WindowType.ACTIVITY)
                .setWindow(dialogActivity)
                .setWindowName(dialogActivity.javaClass.name)
                .setCanShow(true)
                .build()
        )

        findViewById<Button>(R.id.clickId).setOnClickListener {
            PopupManager.autoShowNext(true).continueShow(this)
        }
    }
}