package com.aal.bb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    companion object {
        var state = desasState()
        var vards : String =""
        var rezims: playmode = playmode.Single
        var gaja: Boolean = false;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cnvsview = cnvsview(this)
        cnvsview.contentDescription = "hello wordl:::::"
        setContentView(cnvsview)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}