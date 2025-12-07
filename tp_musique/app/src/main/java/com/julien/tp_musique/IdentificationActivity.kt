package com.julien.tp_musique

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IdentificationActivity : AppCompatActivity() {

    lateinit var champUserName : EditText
    lateinit var confirmerBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_identification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        champUserName = findViewById(R.id.champUserName)
        confirmerBtn = findViewById(R.id.confirmerBtn)

        confirmerBtn.setOnClickListener {

            val intentRetour = Intent()
            intentRetour.putExtra("utilisateur", Utilisateur(champUserName.text.toString()))

            setResult(RESULT_OK, intentRetour)
            finish()
        }

    }
}