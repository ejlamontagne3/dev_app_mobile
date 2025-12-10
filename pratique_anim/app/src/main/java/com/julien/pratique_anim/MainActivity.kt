package com.julien.pratique_anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var element : View
    var anim : ObjectAnimator? = null
    var anim2: ObjectAnimator? = null
    var set : AnimatorSet? = null
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        element = findViewById(R.id.view)
        button = findViewById(R.id.button)
        anim = ObjectAnimator.ofFloat(element, View.X, 200f, 900f).apply {
            duration = 3500
        }
        anim2 = ObjectAnimator.ofFloat(element, View.Y,  200f, 500f).apply {
            duration = 3500
        }

        anim?.setInterpolator(BounceInterpolator()) //comment ca va resgir entre le debut et la fin
        anim?.repeatCount = INFINITE
        anim?.repeatMode = REVERSE

        set = AnimatorSet()
        set?.playTogether(anim, anim2) //il y a playsequentially aussi


        button.setOnClickListener {
            //set?.start()
            anim?.start()
        }
    }



}