package com.julien.tp_musique

interface ObservateurChangement {
    fun changement(musiqueRecu: ListeMusique?, estBoutonPlayAppuye : Boolean){
        //on transfere ici les chanson dans le listview

    }

}