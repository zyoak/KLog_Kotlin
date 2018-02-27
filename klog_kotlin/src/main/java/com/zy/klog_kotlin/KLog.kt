package com.zy.klog_kotlin

import android.text.TextUtils
import com.zy.klog_kotlin.core.BaseLog
import com.zy.klog_kotlin.core.FileLog
import com.zy.klog_kotlin.core.JsonLog
import com.zy.klog_kotlin.core.XmlLog
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
/**
 * Created by zengyong on 2018/2/26.
 */
object KLog{

    val NULL : String = "null"
    val PARAM : String = "Param"
    var SUFFIX : String = ".java"
    val TAG_DEFAULT : String = "KLog"
    val NULL_TIPS : String = "Log with null object"
    var DEFAULT_MESSAGE : String = "execute"
    val LINE_SEPARATOR : String = System.getProperty("line.separator")
    val JSON_INDENT : Int = 4
    val STACK_TRACE_INDEX_4 : Int = 4
    val STACK_TRACE_INDEX_5 : Int = 5

    val V : Int = 0x1
    val D : Int = 0x2
    val I : Int = 0x3
    val W : Int = 0x4
    val E : Int = 0x5
    val A : Int = 0x6
    val JSON : Int = 0x7
    val XML : Int = 0x8

    private var mGlobalTag : String = ""
    private var mIsGlobalTagEmpty : Boolean = true
    private var IS_SHOW_LOG : Boolean = true;

    fun init(isShowLog : Boolean){
        IS_SHOW_LOG = isShowLog
    }

    fun init(isShowLog: Boolean , tag : String){
        init(isShowLog)
        mGlobalTag = tag
        mIsGlobalTagEmpty = TextUtils.isEmpty(mGlobalTag)
    }

    fun v(){
        printLog(V , null , DEFAULT_MESSAGE)
    }

    fun v(msg : Any){
        printLog(V , null , msg)
    }

    fun v(tag : String , objects : Any){
        printLog(V , tag , objects)
    }

    fun d(){
        printLog(D , null , DEFAULT_MESSAGE)
    }

    fun d(msg : Any){
        printLog(D , null , msg)
    }

    fun d(tag : String , objects : Any){
        printLog(D , tag , objects)
    }

    fun i(){
        printLog(I , null , DEFAULT_MESSAGE)
    }

    fun i(msg : Any){
        printLog(I , null , msg)
    }

    fun i(tag : String , objects : Any){
        printLog(I , tag , objects)
    }

    fun w(){
        printLog(W , null , DEFAULT_MESSAGE)
    }

    fun w(msg : Any){
        printLog(W , null , msg)
    }

    fun w(tag : String ,  objects : Any){
        printLog(W , tag , objects)

    }

    fun e(){
        printLog(E , null , DEFAULT_MESSAGE)
    }

    fun e(msg : Any){
        printLog(E , null , msg)
    }

    fun e(tag : String , objects : Any){
        printLog(E , tag , objects)
    }

    fun a(){
        printLog(A , null , DEFAULT_MESSAGE)
    }

    fun a(msg : Any){
        printLog(A , null , msg)
    }

    fun a(tag : String , objects : Any){
        printLog(A , tag , objects)
    }


    fun json(jsonFormat: String){
        printLog(JSON , null , jsonFormat)
    }

    fun json(tag : String , jsonFormat : String){
        printLog(JSON , tag , jsonFormat)
    }

    fun xml(xml : String){
        printLog(XML , null , xml)
    }

    fun xml(tag : String , xml : String){
        printLog(XML , tag , xml)
    }

    fun file(targetDirectory: File , msg: Any){
        printFile(null , targetDirectory , null , msg)
    }

    fun file(tag : String , targetDirectory: File , msg : Any){
        printFile(tag , targetDirectory , null , msg)
    }

    fun file(tag : String , targetDirectory: File , fileName: String , msg : Any){
        printFile(tag , targetDirectory , fileName , msg)
    }

    fun debug(){
        printDebug(null , DEFAULT_MESSAGE)
    }

    fun debug(msg : Any){
        printDebug(null , msg)
    }

    fun debug(tag : String , vararg objects : Any){
        printDebug(tag , objects)
    }

    fun trace(){
        printStackTrace()
    }

    private fun printStackTrace(){
        if(!IS_SHOW_LOG){
            return
        }
        var tr : Throwable = Throwable()
        var sw : StringWriter = StringWriter()
        var pw : PrintWriter = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        var message : String = sw.toString()

        var traceString : List<String> = message.split("\\n\\t")
        var sb : StringBuilder = StringBuilder()
        sb.append("\n")
        for(trace in traceString){
            if(trace.contains("at com.socks.library.KLog")){
                continue
            }
            sb.append(trace).append("\n")
        }
        var contents : List<String> = wrapperContent(STACK_TRACE_INDEX_4 , null , sb.toString())
        var tag : String = contents[0]
        var msg : String = contents[1]
        var headString : String = contents[2]
        BaseLog.printDefault(D , tag , headString + msg)
    }

    private fun  printLog(type : Int , tagStr: String? , objects : Any){
        if(!IS_SHOW_LOG){
            return
        }
        var contents : List<String> = wrapperContent(STACK_TRACE_INDEX_5 , tagStr , objects)
        var tag : String = contents[0]
        var msg : String = contents[1]
        var headString : String = contents[2]
        when(type){
            V,D,I,W,E,A -> BaseLog.printDefault(type , tag , headString + msg)
            JSON -> JsonLog.printJson(tag , msg , headString)
            XML -> XmlLog.printXml(tag , msg , headString)
        }
    }

    private fun printDebug(tagStr: String? , objects: Any){
        var contents : List<String> = wrapperContent(STACK_TRACE_INDEX_5 , tagStr , objects)
        var tag = contents[0]
        var msg = contents[1]
        var headString = contents[2]
        BaseLog.printDefault(D , tag , headString + msg)
    }

    private fun printFile(tagStr : String?, targetDirectory : File, fileName : String? , objectMsg : Any){
        if(!IS_SHOW_LOG){
            return
        }
        var contents : List<String> = wrapperContent(STACK_TRACE_INDEX_5 , tagStr , objectMsg)
        var tag : String = contents[0]
        var msg : String = contents[1]
        var headString : String = contents[2]
        FileLog.printFile(tag , targetDirectory , fileName , headString , msg)
    }

    private fun wrapperContent(stackTraceIndex : Int , tagStr : String? , objects : Any) : List<String>{
        var stackTrace : Array<StackTraceElement> = Thread.currentThread().stackTrace
        var targetElement : StackTraceElement = stackTrace[stackTraceIndex]
        var className : String = targetElement.className
        var classNameInfo : List<String> = className.split("\\.")
        if(classNameInfo.size > 0){
            className = classNameInfo[classNameInfo.size - 1] + SUFFIX
        }
        if(className.contains("$")){
            className = className.split("\\$")[0] + SUFFIX
        }
        var methodName = targetElement.methodName
        var lineNumber : Int = targetElement.lineNumber
        if(lineNumber < 0){
            lineNumber = 0
        }
        var tag : String = if(tagStr == null) className else tagStr
        if(mIsGlobalTagEmpty && TextUtils.isEmpty(tag)){
            tag = TAG_DEFAULT
        }else if(!mIsGlobalTagEmpty){
            tag = mGlobalTag
        }
        var msg : String = if(objects == null) NULL_TIPS else objects.toString()
        var headString : String = "[($className : $lineNumber)#$methodName] "
        return listOf(tag , msg , headString)
    }



}