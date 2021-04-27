package com.why.skin.utils

import android.content.Context

/**
 * @ClassName: SkinPerference
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/25/21 3:18 PM
 */
class SkinPerference private constructor(context: Context){

    companion object : SingletonHolder<SkinPerference, Context>(::SkinPerference){
        private val SKIN_SHARED = "skin_shared"
        private val KEY_SKIN_PATH = "key_skin_path"
    }

    private val mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE)

    fun setSkinPath(path: String){
        mPref.edit().putString(KEY_SKIN_PATH, path).apply()
    }

    fun resetSkinPath(){
        mPref.edit().remove(KEY_SKIN_PATH).apply()
    }

    fun getSkinPath(): String{
        return mPref.getString(KEY_SKIN_PATH, "")?:""
    }
}