package com.why.skin

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.text.TextUtils
import com.why.skin.utils.SingletonHolder
import com.why.skin.utils.SkinPerference
import com.why.skin.utils.SkinResource
import java.lang.reflect.Method
import java.util.*

/**
 * @ClassName: SkinManager
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/25/21 3:27 PM
 */
class SkinManager constructor(application: Application) : Observable() {

    companion object : SingletonHolder<SkinManager, Application>(::SkinManager)
    val mContext: Context = application
    val applicationActivityLifecycle = ApplicationActivityLifecycle(this)
    init {
        SkinPerference.init(mContext)
        SkinResource.init(mContext)
        application.registerActivityLifecycleCallbacks(applicationActivityLifecycle)
        loadSkin(SkinPerference.getInstance().getSkinPath())
    }

    fun loadSkin(path: String){
        if(TextUtils.isEmpty(path)){
            SkinPerference.getInstance().resetSkinPath()
            SkinResource.getInstance().reset()
        }else{
            val mAppReasource = mContext.resources
            val mAppAssetManager= AssetManager::class.java.newInstance()
            val addAssetPath: Method =  mAppAssetManager::class.java.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(mAppAssetManager, path)

            //配置skin的reasource
            val mSkinReasource: Resources = Resources(mAppAssetManager, mAppReasource.displayMetrics, mAppReasource.configuration)

            //获取外部皮肤包名
            val pkManager: PackageManager = mContext.packageManager
            val pkgInfo = pkManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
            val pkgName = pkgInfo?.packageName
            pkgName?.let {
                SkinResource.getInstance().applySkin(mSkinReasource, pkgName)
                SkinPerference.getInstance().setSkinPath(path)
            }
        }

        setChanged()
        notifyObservers(null)
    }
}