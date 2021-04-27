package com.why.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import com.why.skin.utils.SkinThemeUtil
import java.lang.Exception
import java.util.*

/**
 * @ClassName: ApplicationActivityLifecycle
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/25/21 5:08 PM
 */
class ApplicationActivityLifecycle constructor(val observable: Observable): Application.ActivityLifecycleCallbacks {
    val factoryMap = mutableMapOf<Activity, SkinlayoutInflaterFactory>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        SkinThemeUtil.updateStatusbarColor(activity)

        val inflater = activity.layoutInflater
        try {
            val mSkinLayoutInflaterFactory = SkinlayoutInflaterFactory(activity)

//            在Q之前这段代码可以执行，但是Q之后这个属性为非SDK属性，就无法获取到
//            val mFactorySet = LayoutInflater::class.java.getDeclaredField("mFactorySet")
//            mFactorySet.isAccessible = true;
//            mFactorySet.setBoolean(inflater, false)
//            LayoutInflaterCompat.setFactory2(inflater, mSkinLayoutInflaterFactory)

//            转换思路通过查看LayoutInflaterCompat方法，知道在21之后已经无法通过系统方法设置，只能直接反射factory属性然后赋值
            val factory2Field = LayoutInflater::class.java.getDeclaredField("mFactory2")
            factory2Field.isAccessible = true
            factory2Field.set(inflater, mSkinLayoutInflaterFactory)


            factoryMap[activity] = mSkinLayoutInflaterFactory
            observable.addObserver(mSkinLayoutInflaterFactory)
        }catch (e: Exception){
            e.printStackTrace()
        }


    }

    override fun onActivityStarted(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityResumed(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityPaused(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivityStopped(activity: Activity) {
//        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
//        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(activity: Activity) {
        val factory = factoryMap.remove(activity)
        observable.deleteObserver(factory)
    }
}