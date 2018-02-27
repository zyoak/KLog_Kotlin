package com.zy.klog_kotlin.core

import android.util.Log
import com.zy.klog_kotlin.KLog

/**
 * Created by zengyong on 2018/2/26.
 */
object BaseLog{

    val MAX_LENGTH : Int = 4000

    fun printDefault(type : Int , tag : String , msg : String){
        var index : Int = 0
        var length : Int = msg.length
        var countOfSub : Int = length / MAX_LENGTH
        if(countOfSub > 0){
            for(i in 0 until countOfSub){
                var sub = msg.substring(index , index + MAX_LENGTH)
                printSub(type , tag , sub)
                index += MAX_LENGTH
            }
            printSub(type , tag , msg.substring(index , length))
        }else{
            printSub(type , tag , msg)
        }
    }

    fun printSub(type : Int , tag : String , sub : String){
        when(type){
            KLog.V -> Log.v(tag , sub)
            KLog.D -> Log.d(tag , sub)
            KLog.I -> Log.i(tag , sub)
            KLog.W -> Log.w(tag , sub)
            KLog.E -> Log.e(tag , sub)
            KLog.A -> Log.wtf(tag , sub)
        }
    }

}