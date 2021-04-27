package com.why.skin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils

/**
 * @ClassName: SkinReasours
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/24/21 6:12 PM
 */
class SkinResource constructor(val context: Context){

    companion object : SingletonHolder<SkinResource, Context>(::SkinResource)

    var isDeafultRes = false

    val mAppReasources: Resources = context.resources
    var mSkinResources: Resources? = null

    var mSkinPkgName: String = ""

    fun reset(){
        mSkinResources = null
        mSkinPkgName = ""
        isDeafultRes = true
    }

    fun applySkin(resources: Resources, pkName: String){
        mSkinResources = resources
        mSkinPkgName = pkName

        isDeafultRes = TextUtils.isEmpty(mSkinPkgName) || mSkinResources == null
    }

    fun getSkinResId(resId: Int): Int{
        if(isDeafultRes){
            return resId
        }
        try {
            val skinResName = mAppReasources.getResourceEntryName(resId)
            val skinResType = mAppReasources.getResourceTypeName(resId)
            val skinResId = mSkinResources!!.getIdentifier(skinResName, skinResType, mSkinPkgName)
            return skinResId
        }catch (e: Exception){

        }

        return 0
    }

    fun getColor(resId: Int): Int{
        if(isDeafultRes){
            return mAppReasources!!.getColor(resId)
        }
        val skinId = getSkinResId(resId)
        if(0 == skinId){
            return mAppReasources!!.getColor(resId)
        }
        return mSkinResources!!.getColor(skinId)
    }

    fun getDrawable(resId: Int): Drawable{
        if(isDeafultRes){
            return mAppReasources!!.getDrawable(resId)
        }
        val skinResId = getSkinResId(resId)
        if(skinResId == 0){
            return mAppReasources!!.getDrawable(resId)
        }
        return mSkinResources!!.getDrawable(skinResId)
    }

    fun getBackground(resId: Int): Any{

        val resName = mAppReasources!!.getResourceTypeName(resId)

        return if("color" == resName)  getColor(resId) else getDrawable(resId)
    }

}