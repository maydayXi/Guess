package com.wei.guess

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess
import androidx.appcompat.app.AppCompatActivity as AppCompatActivity1

class MainActivity : AppCompatActivity1() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        processViews()
    }

    // <summary> initial view components </summary>
    private fun processViews() {
        btnPlay.setOnClickListener(listener)
        btnRecord.setOnClickListener(listener)
        btnQuit.setOnClickListener(listener)
    }

    // <summary> Button Click Listener </summary>
    private val listener = View.OnClickListener {
        val intent = Intent()

        when(it.id) {
            R.id.btnPlay -> {
                intent.component = ComponentName(this, MaterialGameActivity::class.java)
            }
            R.id.btnRecord -> {
                intent.component = ComponentName(this, RecordActivity::class.java)
                val bundle = Bundle()
                bundle.putInt("ActivityEntry", ActivityCode.MAIN_ACTIVITY.ordinal)
                intent.putExtras(bundle)
            }
            else -> {
                exitProcess(-1)
            }
        }
        startActivity(intent)
    }
}