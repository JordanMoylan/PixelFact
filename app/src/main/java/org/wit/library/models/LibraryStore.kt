package org.wit.library.models

interface LibraryStore {
  fun findAll(): List<LibraryModel>
  fun create(libraryVar: LibraryModel)
  fun update(libraryVar: LibraryModel)
  fun delete(libraryVar: LibraryModel)
}