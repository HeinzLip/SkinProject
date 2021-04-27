package com.why.skinproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.why.skin.SkinManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListener()
    }

    fun initListener(){
        change_skin.setOnClickListener {
            //此处的地址是皮肤包所在地址
            SkinManager.getInstance().loadSkin("/data/data/com.why.skinproject/skin/app-debug.apk")
        }
        restore_skin.setOnClickListener {
            SkinManager.getInstance().loadSkin("")
        }
    }
}