package com.julien.annexe_8_animation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.View.TRANSLATION_Y
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var popUp : LinearLayout
    var anim : ObjectAnimator? = null
    var estSorti = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        popUp = findViewById(R.id.popUp)
        anim = ObjectAnimator.ofFloat(popUp, TRANSLATION_Y, 0f)

        popUp.setOnClickListener {


            if (estSorti){
                estSorti = false
                anim?.reverse()
            }
            else{
                estSorti = true
                anim?.start()
            }

        }





    }
}