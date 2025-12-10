package com.julien.annexe8_anim

import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var palette: LinearLayout
    var ma_variable = false
    var o : ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        palette = findViewById(R.id.palette)


        palette.setOnClickListener {
            if (!ma_variable){
                o = ObjectAnimator.ofFloat(palette, "translationY", 0f).apply{
                    duration = 300
                    start()
                }
                ma_variable = true
            }else{

                o?.reverse()
                ma_variable = false


            }

        }


    }






}