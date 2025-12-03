package com.julien.annexe_8

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.io.path.Path

class MainActivity : AppCompatActivity() {

    lateinit var element : View
    var anim : ObjectAnimator? = null
    var set : AnimatorSet? = null

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
        //anim = ObjectAnimator.ofFloat(element, View.X, 200f, 900f)
        val path = android.graphics.Path()
        path.moveTo(200f, 200f)
        path.lineTo(600f, 500f)
        path.lineTo(50f, 800f)
        //path.arcTo()
        anim = android.animation.ObjectAnimator.ofFloat(element, View.X, View.Y, path)
        anim?.duration = 2000
        anim?.startDelay = 1000
        //anim?.setInterpolator(BounceInterpolator())
        //anim?.repeatCount = INFINITE
        //anim?.repeatMode = REVERSE
        //set?.playTogether(anim, anim2)  //pour jouer en meme temps

        val anim2 = ObjectAnimator.ofFloat(element, View.ALPHA, 0.4f)
        set = AnimatorSet()
        set?.playSequentially(anim, anim2) //joue un apres lautre




    }


    //raccourci pour clic de bouton
    fun gestion (source : View)
    {
        set?.start() // pour arreter : end()
    }

}