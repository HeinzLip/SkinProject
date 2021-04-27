package com.why.skin.utils

import org.json.JSONObject

/**
 * @ClassName: SingletonHolder
 * @Description: java类作用描述
 * @Author: wanghaiyang91
 * @Date: 3/25/21 2:54 PM
 */
open class SingletonHolder<out A: Any, in T> constructor(private val creator: (T)->A) {

    private var instance: A? = null

    fun getInstance(): A{
        return instance!!
    }

    fun init(t: T): A{
        return (instance?: synchronized(this){
            instance?:creator(t).apply {
                instance = this
            }
        })
    }
}