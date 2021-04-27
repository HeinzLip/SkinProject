package com.why.skin

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.why.skin.utils.SingletonHolder
import com.why.skin.utils.SkinResource
import com.why.skin.utils.SkinThemeUtil
import java.lang.reflect.Constructor
import java.util.*

/**
 * @ClassName: SkinlayoutInflaterFactory
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/25/21 3:48 PM
 */
class SkinlayoutInflaterFactory (val activity: Activity) : LayoutInflater.Factory2, Observer {

    companion object : SingletonHolder<SkinlayoutInflaterFactory, Activity>(::SkinlayoutInflaterFactory){
        val mConstructorSignature = arrayOf(
            Context::class.java, AttributeSet::class.java
        )

        val mSDKPkgName = arrayOf(
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
        )

        val sConstructorMap = HashMap<String, Constructor<out View?>>()
    }
    val mSkinAttribute: SkinAttribute = SkinAttribute()

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        var view = createSDKView(context, name, attrs)
        if(view == null ){
            view = createView(context, name, attrs)
        }

        if(view != null){
            mSkinAttribute.look(view, attrs)
        }
        return view
    }

    private fun createSDKView(context: Context, name: String, attrs: AttributeSet): View?{
        if(-1 != name.indexOf(".")){
            return null
        }

        for(i in mSDKPkgName.indices){
            val constructor = findConstructor(context, mSDKPkgName[i] + name)
            val view = constructor?.newInstance(context, attrs)
            if(view != null){
                return view
            }
        }

        return null
    }

    private fun createView(context: Context, name: String, attrs: AttributeSet): View?{
        val constructor = findConstructor(context, name)
        try {
            return constructor?.newInstance(context, attrs)
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
    private fun findConstructor(context: Context, name: String): Constructor<out View?>?{
        var constructor: Constructor<out View?>? = sConstructorMap[name]
        if(constructor == null){
            try {
                val clazz = context.classLoader.loadClass(name).asSubclass(
                    View::class.java
                )
                constructor = clazz.getConstructor(*mConstructorSignature)
                sConstructorMap[name] = constructor
            }catch (e: Exception){

            }
        }
        return constructor;
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    override fun update(o: Observable?, arg: Any?) {
        SkinThemeUtil.updateStatusbarColor(activity)
        mSkinAttribute.applySkin()
    }
}