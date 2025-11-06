package com.julien.annexe4

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

lateinit var connaitreBtn : Button
lateinit var texteSalutation : TextView
lateinit var lanceur: ActivityResultLauncher<Intent>;

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

        lanceur = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            CallBackIdentification()
        )

        texteSalutation = findViewById(R.id.textSalutation)

        connaitreBtn = findViewById(R.id.connaitreBtn)
        connaitreBtn.setOnClickListener{ source ->
            if (source == connaitreBtn){

                val intent = Intent(this, IdentificationActivity::class.java)
                lanceur.launch(intent)

            }
        }


    }

    inner class CallBackIdentification : ActivityResultCallback<ActivityResult> {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onActivityResult(result: ActivityResult) {

            //On revient ici de notre boomrang
            if (result.resultCode == RESULT_OK){
                val intent = result.data
                val u = intent!!.getSerializableExtra("utilisateur", Utilisateur::class.java)
                texteSalutation.append((" ${u!!.prenom} ${u!!.nom}"))

            }


        }

    }





}