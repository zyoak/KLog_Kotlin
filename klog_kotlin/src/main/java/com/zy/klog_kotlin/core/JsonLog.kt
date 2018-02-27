package com.zy.klog_kotlin.core

import android.util.Log
import com.zy.klog_kotlin.KLog
import com.zy.klog_kotlin.KLogUtil
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by fengjrfengjr on 2018/2/26.
 */
object JsonLog{

    fun printJson(tag : String , msg : String , headString : String){
        var message : String
        try {
            if(msg.startsWith("{")){
                var jsonObject : JSONObject = JSONObject(msg)
                message = jsonObject.toString(KLog.JSON_INDENT)
            }else if(msg.startsWith("[")){
                var jsonArray : JSONArray = JSONArray(msg)
                message = jsonArray.toString(KLog.JSON_INDENT)
            }else{
                message = msg
            }
        }catch (e : Exception){
            message = msg
        }
        KLogUtil.printLine(tag , true)
        message = headString + KLog.LINE_SEPARATOR + message
        var lines : List<String> = message.split(KLog.LINE_SEPARATOR)
        for(line in lines){
            Log.d(tag , "║ $line")
        }
        KLogUtil.printLine(tag , false)
    }

}