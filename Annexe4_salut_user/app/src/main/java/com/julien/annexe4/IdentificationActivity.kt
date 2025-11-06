package com.julien.annexe4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class IdentificationActivity : AppCompatActivity() {

    lateinit var champPrenom : EditText
    lateinit var champNom : EditText
    lateinit var boutonConfirmer : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_identification)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        boutonConfirmer = findViewById(R.id.confirmerBtn)
        champPrenom = findViewById(R.id.champPrenom)
        champNom = findViewById(R.id.champNom)


        boutonConfirmer.setOnClickListener{
            val intentRetour = Intent()
            intentRetour.putExtra("utilisateur", Utilisateur(champPrenom.text.toString(), champNom.text.toString()))

            setResult(RESULT_OK, intentRetour)
            finish()
        }
    }
}