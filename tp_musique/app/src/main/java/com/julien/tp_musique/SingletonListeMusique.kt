package com.julien.tp_musique

object SingletonListeMusique {
    var singletonListe = ArrayList<Musique>()

    fun ajoutListe (liste: ListeMusique){
        singletonListe.addAll(liste.music)
    }

}