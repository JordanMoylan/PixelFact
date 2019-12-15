package org.wit.library.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_library.*
import kotlinx.android.synthetic.main.card_library.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.jetbrains.anko.intentFor
import org.wit.library.R
import org.wit.library.main.MainApp
import org.wit.library.models.LibraryModel
import org.wit.library.helpers.readImage
import org.wit.library.helpers.readImageFromPath
import org.wit.library.helpers.showImagePicker

class LibraryActivity : AppCompatActivity(), AnkoLogger {

  var libraryInput = LibraryModel()
  var edit = false
  lateinit var app: MainApp
  var library = LibraryModel()
  val IMAGE_REQUEST = 1


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_library)

//creating variables for spinners

    var categoryOf = arrayOf<String>(
      "Educational",
      "Fun",
      "Artistic",
      "Music",
      "Video Games",
      "Cheat Codes",
      "History",
      "Work Related",
      "Self Study",
      "Misc"
    )

    var factOption = arrayOf<String>("Important", "Unimportant")

    var bookCategory = findViewById(R.id.bookCategoryInput) as Spinner
    var factFiction = findViewById(R.id.factFictionInput) as Spinner

    //allows us to bind data to the spinners
    var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryOf)
    bookCategory.adapter = adapter

    var adapterTwo = ArrayAdapter(this, android.R.layout.simple_list_item_1, factOption)
    factFiction.adapter = adapterTwo

    //will listen to a chosen input by the user
    bookCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        Toast.makeText(this@LibraryActivity, categoryOf[i], Toast.LENGTH_SHORT).show()
      }

      override fun onNothingSelected(adapterView: AdapterView<*>) {
      }
    }

    factFiction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        Toast.makeText(this@LibraryActivity, factOption[i], Toast.LENGTH_SHORT).show()
      }

      override fun onNothingSelected(adapterView: AdapterView<*>) {
      }
    }

    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("library Activity started..")

    app = application as MainApp


    //setting up the inputs to
    if (intent.hasExtra("library_edit")) {
      edit = true
      libraryInput = intent.extras?.getParcelable<LibraryModel>("library_edit")!!
      bookTitleInput.setText(libraryInput.title)
      bookDescriptionInput.setText(libraryInput.description)
      bookCategoryInput.setSelection(adapter.getPosition(libraryInput.category))
      factFictionInput.setSelection(adapterTwo.getPosition(libraryInput.factvariable))
      factImage.setImageBitmap(readImageFromPath(this, libraryInput.image))
      if (libraryInput.image != null) {
        chooseImage.setText(R.string.change_fact_image)
      }
      btnAdd.setText(R.string.save_book)
    }

    btnAdd.setOnClickListener() {
      libraryInput.title = bookTitleInput.text.toString()
      libraryInput.description = bookDescriptionInput.text.toString()
      libraryInput.category = bookCategoryInput.selectedItem as String
      libraryInput.factvariable = factFictionInput.selectedItem as String
      if (libraryInput.title.isEmpty()) {
        toast(R.string.enter_fact_title)
      } else {
        if (edit) {
          app.facts.update(libraryInput.copy())
        } else {
          app.facts.create(libraryInput.copy())
        }
      }
      info("add Button Pressed: $bookTitleInput")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    deleteButton.setOnClickListener() {
      libraryInput.title = bookTitleInput.text.toString()
      libraryInput.description = bookDescriptionInput.text.toString()
      libraryInput.category = bookCategoryInput.selectedItem as String
      libraryInput.factvariable = factFictionInput.selectedItem as String
      if (libraryInput.title.isEmpty()) {
        toast(R.string.enter_fact_title)
      } else {
        if (edit) {
          app.facts.delete(libraryInput.copy())
        }
      }
      info("add Button Pressed: $bookTitleInput")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_library, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          libraryInput.image = data.getData().toString()
          factImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(R.string.change_fact_image)
        }
      }
    }
  }
}




