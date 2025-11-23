package com.julien.tp_musique

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon

class Modele (context: Context) : Sujet  {

    private var maMusique : ListeMusique? = null
    private var appuyerBoutonPlay : Boolean = false
    private var obs: ObservateurChangement? = null // c'est un observateur, il pourrait en avoir plusieurs

    // constructeur unique
    init {
        // Crée la queue de requete
        val queue = Volley.newRequestQueue(context)
        val url = "https://api.jsonbin.io/v3/b/680a6a1d8561e97a5006b822?meta=false"

        // notre requete demande une string au server
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {response ->
                maMusique = Klaxon().parse<ListeMusique>(response) ?: ListeMusique()
                setMaMusique(maMusique!!)

            },
            Response.ErrorListener {  })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun jouerChanson(context : Context, chanson : Musique){
        // Crée la queue de requete
        val queue = Volley.newRequestQueue(context)
        val url = chanson.source

        // notre requete demande une string au server
        val stringRequest = StringRequest(Request.Method.GET, url,
            {
                    response ->
                    setAppuyerBoutonPlay()
            },
            { })

// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun getMaMusique(): ListeMusique? {
        return maMusique
    }
    fun setMaMusique(maMusique: ListeMusique) {
        this.maMusique = maMusique
        //changement de l'état du sujet, avertir les observateurs
        avertirObservateurs()
    }

    fun getAppuyerBoutonPlay(): Boolean {
        return appuyerBoutonPlay
    }

    fun setAppuyerBoutonPlay() {
        if (appuyerBoutonPlay){
            this.appuyerBoutonPlay = false
        }else{
            this.appuyerBoutonPlay = true
        }
        //changement de l'état du sujet, avertir les observateurs
        avertirObservateurs()
    }

    // méthodes de l'interface Sujet

    override fun ajouterObservateur(obs: ObservateurChangement) {
        this.obs = obs
    }

    override fun enleverObservateur(obs: ObservateurChangement) {

        this.obs = null // il y en a un seul
    }

    override fun avertirObservateurs() {
        obs!!.changement(maMusique, appuyerBoutonPlay) // important
    }
}