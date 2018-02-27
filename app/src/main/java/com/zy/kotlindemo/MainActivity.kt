package com.zy.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zy.klog_kotlin.KLog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_i.setOnClickListener({
            KLog.i();
            KLog.i("KLog.i.msg")
            KLog.i("KLog.i" , "KLog.i.msg..")
        })

        btn_d.setOnClickListener({
            KLog.d()
            KLog.d("KLog.d.msg")
            KLog.d("KLog.d" , "KLog.d.msg...")
        })

        btn_e.setOnClickListener({
            KLog.e()
            KLog.e("KLog.e.msg")
            KLog.e("KLog.e" , "KLog.e.msg...")
        })

        btn_a.setOnClickListener({
            KLog.a()
            KLog.a("KLog.a.msg")
            KLog.a("KLog.a" , "KLog.a.msg...")
        })

        btn_json.setOnClickListener({
            KLog.json("KLog.json.msg")
            KLog.json("KLog.json" , "{\"aa\":\"KLog.json.msg...}\"")
        })

        btn_xml.setOnClickListener({
            KLog.xml("KLog.xml.msg")
            KLog.xml("KLog.xml" , "<a>KLog.xml.msg...</a>")
        })

    }


}
