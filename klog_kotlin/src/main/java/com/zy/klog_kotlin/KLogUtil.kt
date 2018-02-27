package com.zy.klog_kotlin

import android.text.TextUtils
import android.util.Log

/**
 * Created by fengjrfengjr on 2018/2/26.
 */
object KLogUtil{

    fun isEmpty(line : String):Boolean{
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t");
    }

    fun printLine(tag : String , isTop : Boolean){
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

}