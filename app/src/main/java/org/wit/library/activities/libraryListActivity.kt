package org.wit.library.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_library_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.library.R
import org.wit.library.main.MainApp
import org.wit.library.models.LibraryModel

class LibraryListActivity : AppCompatActivity(), LibraryListener {

  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_library_list)
    app = application as MainApp
    toolbar.title = title
    setSupportActionBar(toolbar)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = LibraryAdapter(app.facts.findAll(), this)
    loadFacts()
  }

  private fun loadFacts() {
    showFacts(app.facts.findAll())
  }

  fun showFacts (facts: List<LibraryModel>) {
    recyclerView.adapter = LibraryAdapter(facts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_add -> startActivityForResult<LibraryActivity>(0)
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onlibraryClick(library: LibraryModel) {
    startActivityForResult(intentFor<LibraryActivity>().putExtra("library_edit", library), AppCompatActivity.RESULT_OK)
  }
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }
}