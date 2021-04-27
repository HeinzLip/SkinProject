package com.why.skin.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import com.why.skin.SkinAttribute

/**
 * @ClassName: SkinThemeUtil
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/25/21 2:15 PM
 */
class SkinThemeUtil {

    companion object{

        private val APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS = intArrayOf(
            androidx.appcompat.R.attr.colorPrimaryDark
        )

        private val STATUSBAR_COLOR_ATTRS = intArrayOf(
            android.R.attr.statusBarColor,
            android.R.attr.navigationBarColor
        )

        fun getResID(context: Context, attrs: IntArray): IntArray {
            val resIDs = IntArray(attrs.size);
            val a = context.obtainStyledAttributes(attrs)
            for (i in attrs.indices){
                resIDs[i] = a.getResourceId(i, 0)
            }
            a.recycle()
            return resIDs
        }

        fun updateStatusbarColor(activity: Activity){
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                return
            }

            val statusIDs = getResID(activity, STATUSBAR_COLOR_ATTRS)
            val statusBarColorID = statusIDs[0]
            val navigationBarColorID = statusIDs[1]
            if(statusBarColorID != 0){
                val statusBarColor = SkinResource.getInstance().getColor(statusBarColorID)
                activity.window.statusBarColor = statusBarColor
            }else{
                val primaryColor = getResID(activity, APPCOMPAT_COLOR_PRIMARY_DARK_ATTRS)[0]
                if(primaryColor != 0){
                    val realPriMaryColor = SkinResource.getInstance().getColor(primaryColor)
                    activity.window.statusBarColor = realPriMaryColor
                }
            }

            if(navigationBarColorID != 0){
                val navigationColor = SkinResource.getInstance().getColor(navigationBarColorID)
                activity.window.navigationBarColor = navigationColor
            }


        }
    }


}