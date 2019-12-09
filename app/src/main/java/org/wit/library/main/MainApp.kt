package org.wit.library.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.library.models.FactsJSONStore
import org.wit.library.models.LibraryStore

class MainApp : Application(), AnkoLogger {

  lateinit var facts: LibraryStore


  override fun onCreate() {
    facts = FactsJSONStore(applicationContext)
    super.onCreate()
    info("library started")
  }
}