package edu.bedaev.universeofrickandmorty.network.model

import android.os.Parcelable
import edu.bedaev.universeofrickandmorty.domain.model.Person
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class Origin(
    val name: String? = null,
    val url: String? = null
): Parcelable{

    companion object{
        fun fakeOrigin(): Origin{
            return Origin(
                name = generateName(),
                url = generateUrl()
            )
        }

        private fun generateName(): String{
            return "${Person.generateName((5..10).random())} " +
                    "(${Person.generateName((10..15).random())} " +
                    "${Person.generateName((5..10).random())})"
        }

        fun generateUrl(pathLength: Int = 4): String{
            val prefix = if (Random.nextBoolean()) "http://" else "https://"
            var path = prefix
            val chars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
            for (i in 0 until pathLength){
                val pathPart = (1 .. (10..30).random())
                    .map {
                        chars.random()
                    }.joinToString(separator = "")
                path += "$pathPart/"
            }
            return path
        }

    }
}