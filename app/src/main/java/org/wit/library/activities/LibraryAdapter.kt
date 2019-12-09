package org.wit.library.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_library.view.*
import org.wit.library.R
import org.wit.library.models.LibraryModel
import org.wit.library.helpers.readImageFromPath


interface LibraryListener {
  fun onlibraryClick(library: LibraryModel)
}

class LibraryAdapter constructor(private var books: List<LibraryModel>,
                                   private val listener: LibraryListener) : RecyclerView.Adapter<LibraryAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_library, parent, false))
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val library = books[holder.adapterPosition]
    holder.bind(library, listener)
  }

  override fun getItemCount(): Int = books.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(library: LibraryModel, listener: LibraryListener) {
      itemView.libraryTitle.text = library.title
      itemView.description.text = library.description
      itemView.Category.text = library.category
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, library.image))
      itemView.setOnClickListener { listener.onlibraryClick(library)
        itemView.factFictionOutput.text = library.factvariable
        itemView.setOnClickListener { listener.onlibraryClick(library)
      }
    }
  }
 }
}