package com.julien.annexe1

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var champMemo : EditText
lateinit var ajoutBtn : Button


class AjouterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ajouter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var ecc = Ecouteur()

        champMemo = findViewById(R.id.champMemo)
        ajoutBtn = findViewById(R.id.ajoutBtn)

        ajoutBtn.setOnClickListener(ecc)
    }

    inner class Ecouteur : OnClickListener{
        override fun onClick(source: View?) {


            if (source == ajoutBtn){

                var memo : String = champMemo.text.toString()
                memoList.add(memo)
                finish()
            }



        }


    }



}