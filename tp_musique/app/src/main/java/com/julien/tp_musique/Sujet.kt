package com.julien.tp_musique

interface Sujet {
    fun ajouterObservateur(o:ObservateurChangement)
    fun enleverObservateur(o:ObservateurChangement)
    fun avertirObservateurs()

}