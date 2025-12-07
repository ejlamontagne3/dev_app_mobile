package com.julien.tp_musique

import java.io.Serializable

data class EtatChanson (var titre: String, var tempsLectureActuel: Int) : Serializable {
}