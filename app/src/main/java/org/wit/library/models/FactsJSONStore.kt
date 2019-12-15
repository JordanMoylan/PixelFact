package org.wit.library.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.library.helpers.exists
import org.wit.library.helpers.read
import org.wit.library.helpers.write
import java.util.*

val JSON_FILE = "facts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<LibraryModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class FactsJSONStore : LibraryStore, AnkoLogger {

    val context: Context
    var facts = mutableListOf<LibraryModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<LibraryModel> {
        return facts
    }

    override fun create(librarys: LibraryModel) {
        librarys.id = generateRandomId()
        facts.add(librarys)
        serialize()
    }


    override fun update(librarys: LibraryModel) {
        val foundFacts: LibraryModel? = facts.find { p -> p.id == librarys.id }
        if (foundFacts != null) {
            foundFacts.title = librarys.title
            foundFacts.description = librarys.description
            foundFacts.category = librarys.category
            foundFacts.factvariable = librarys.factvariable
            foundFacts.image = librarys.image
            foundFacts.lat = librarys.lat
            foundFacts.lng = librarys.lng
            foundFacts.zoom = librarys.zoom
        }
        serialize()
    }

    override fun delete(librarys: LibraryModel) {
        facts.remove(librarys)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(facts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        facts = Gson().fromJson(jsonString, listType)
    }
}