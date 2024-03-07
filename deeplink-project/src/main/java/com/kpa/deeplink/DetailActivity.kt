package com.kpa.deeplink

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handleDeepLink(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // 在 singleTop 模式下处理新的 Intent
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        if (intent?.action == Intent.ACTION_VIEW) {
            // 获取 Deep Link 中的数据
            val data = intent.data
            // 处理数据，例如根据不同的 URI 启动不同的内容
            // 这里仅作为示例，你可以根据实际需求进行逻辑处理
            if (data != null) {
                Log.d("DeepLink", "Deep Link URI: $data")
                // 在这里处理具体的逻辑，例如根据 URI 启动不同的内容
            }
        }
    }
}