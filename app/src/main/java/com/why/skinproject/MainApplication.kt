package com.why.skinproject

import android.app.Application
import com.why.skin.SkinManager

/**
 * @ClassName: MainApplication
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 4/26/21 4:49 PM
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
    }
}