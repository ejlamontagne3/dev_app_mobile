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
import java.io.BufferedWriter
import java.io.OutputStreamWriter

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



        champMemo = findViewById(R.id.champMemo)
        ajoutBtn = findViewById(R.id.ajoutBtn)

        ajoutBtn.setOnClickListener{

            val texteMemo = champMemo.text.toString()
            val fos = openFileOutput("fichier.txt", MODE_APPEND) //pour que les memos s'ajoutent 1 par ligne
            val osw = OutputStreamWriter(fos)//flux de traduction binaire en caractere
            val bw = BufferedWriter(osw) //notre eponge pour accelerer les operations

            bw.use{ //use cest pour fermer le flux de donnée qu'il y ait un probleme ou non
                    bw.write(texteMemo)
                    bw.newLine() //pour pouvoir mieux les séparer
            }

            finish() //fermer l'activité et revenir au menu



        }
    }





}