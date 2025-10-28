package com.julien.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var ajouterBtn : Button
lateinit var afficherBtn : Button
lateinit var intent : Intent
lateinit var intent2 : Intent
lateinit var memoList : ArrayList<String>


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        intent = Intent(this, AjouterActivity::class.java)
        intent2 = Intent(this, AfficherActivity::class.java)

        memoList = ArrayList()
        var ec = Ecouteur()

        ajouterBtn = findViewById(R.id.ajouterBtn)
        afficherBtn = findViewById(R.id.afficherBtn)
        ajouterBtn.setOnClickListener(ec)
        afficherBtn.setOnClickListener(ec)


    }

    inner class Ecouteur : OnClickListener{
        override fun onClick(source: View?) {

            if (source == ajouterBtn){

                startActivity(intent)

            }

            if (source == afficherBtn){

                startActivity(intent2)

            }


        }



    }




}