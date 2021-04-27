package com.why.skin

import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.why.skin.utils.SkinResource
import com.why.skin.utils.SkinThemeUtil

/**
 * @ClassName: SkinAttribute
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/24/21 5:27 PM
 */
class SkinAttribute {


    companion object{
        val attributeSetlist = mutableListOf<String>().apply {
            add("background")
            add("src")
            add("textColor")
        }

    }
    val skinViews = mutableListOf<SkinView>()

    fun look(view: View, attributeSet: AttributeSet){

        val skinPairs = mutableListOf<SkinPair>()
        for(i in 0 until attributeSet.attributeCount){
            val attributeName = attributeSet.getAttributeName(i)
            if(attributeSetlist.contains(attributeName)){
                val attributID = attributeSet.getAttributeValue(i);
                if(attributID.startsWith("#")){
                    continue
                }

                val skinPair: SkinPair = if(attributID.startsWith("?") ){
                    val attributIDInt = attributID.substring(1).toInt()
                    val skinThemeID = SkinThemeUtil.getResID(view.context, intArrayOf(attributIDInt))[0]
                    SkinPair(skinThemeID, attributeName)
                }else{
                    val attributIDInt = attributID.substring(1).toInt();
                    SkinPair(attributIDInt, attributeName)
                }
                skinPairs.add(skinPair)

            }
        }

        if(skinPairs.isNotEmpty() || view is SkinViewSupport){
            val skinView = SkinView(view, skinPairs)
            skinView.applySkin()
            skinViews.add(skinView)
        }


    }

    fun applySkin(){
        for(skinView in skinViews){
            skinView.applySkin()
        }
    }

}

class SkinView constructor(val view: View, val skinPairs: MutableList<SkinPair>){

    fun applySkin(){
        viewSupportSkin()
        for (skinPair in  skinPairs){
            skinPair.resName.let {
                when{
                    it.contains("background")->{
                        val res = SkinResource.getInstance().getBackground(skinPair.resId)
                        if(res is Drawable){
                            view.background = res
                        }else{
                            view.setBackgroundColor(res as Int)
                        }
                    }
                    it.contains("textColor")->{
                        val textColor = SkinResource.getInstance().getColor(skinPair.resId)
                        when(view){
                            is TextView ->{
                                view.setTextColor(textColor)
                            }
                            is Button ->{
                                view.setTextColor(textColor)
                            }

                        }
                    }
                    it.contains("src")->{
                        val src = SkinResource.getInstance().getDrawable(skinPair.resId)
                        when(view){
                            is ImageView ->{
                                view.setImageDrawable(src)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun viewSupportSkin(){
        if(view is SkinViewSupport){
            view.applySkin()
        }
    }


}

data class SkinPair(val resId: Int, val resName: String)