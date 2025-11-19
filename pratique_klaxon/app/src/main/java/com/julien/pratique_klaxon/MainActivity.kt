package com.julien.pratique_klaxon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.julien.pratique_klaxon.Record

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

        // Crée la queue de requete
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.jsonbin.io/v3/b/691c8be1d0ea881f40f0243b"

// notre requete demande une string au server
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {response ->
                val toutesLesVilles : Record = Klaxon().parse<Record>(response) ?: Record()
                Toast.makeText(this, toutesLesVilles.listeVille.size.toString(), Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener { Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()  })

// Add the request to the RequestQueue.
        queue.add(stringRequest)





    }







}