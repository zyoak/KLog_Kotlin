package com.zy.klog_kotlin.core

import android.util.Log
import com.zy.klog_kotlin.KLog
import com.zy.klog_kotlin.KLogUtil
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * Created by fengjrfengjr on 2018/2/26.
 */
object XmlLog{

    fun printXml(tag : String , xml : String , headString : String){
        var xm : String
        if(xml != null){
            xm  = headString + "\n" + XmlLog.formatXML(xml)
        }else{
            xm = headString + KLog.NULL_TIPS
        }
        KLogUtil.printLine(tag , true)
        var lines : List<String> = xml.split(KLog.LINE_SEPARATOR)
        for(line in lines){
            if(!KLogUtil.isEmpty(line)){
                Log.d(tag , "║ " + line)
            }
        }
        KLogUtil.printLine(tag , false)
    }

    fun formatXML(inputXml : String):String{
        try {
            var xmlInput : Source = StreamSource(StringReader(inputXml))
            var xmlOutput : StreamResult = StreamResult(StringWriter())
            var transformer : Transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT , "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount" , "2")
            transformer.transform(xmlInput , xmlOutput)
            return xmlOutput.writer.toString().replaceFirst(">" , ">\n")
        }catch (e : Exception){
            e.printStackTrace()
            return inputXml
        }
    }


}