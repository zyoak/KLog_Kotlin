package com.zy.klog_kotlin.core

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Created by zengyong on 2018/2/26.
 */
object FileLog{

    private val FILE_PREFIX : String = "KLog_"
    private val FILE_FORMAT : String = ".log"

    fun printFile(tag : String , targetDirectory : File , fileName : String? , headString : String , msg : String){
        var fileName : String = if(fileName == null) getFileName() else fileName
        if(save(targetDirectory , fileName , msg)){
            Log.d(tag ,"$headString save log success ! location is >>> ${targetDirectory.absolutePath} / $fileName")
        }else{
            Log.e(tag , "$headString save log fails !")
        }

    }

    private fun save(dic : File , filename : String , msg : String):Boolean{
        var file : File = File(dic , filename)
        try {
            var outputStream : OutputStream = FileOutputStream(file)
            var outputStreamWriter : OutputStreamWriter = OutputStreamWriter(outputStream , "UTF-8")
            outputStreamWriter.write(msg)
            outputStreamWriter.flush()
            outputStreamWriter.close()
            return true
        }catch (e : Exception){
            e.printStackTrace()
            return false
        }
    }

    private fun getFileName():String{
        return FILE_PREFIX + System.currentTimeMillis().toString() + FILE_FORMAT
    }

}