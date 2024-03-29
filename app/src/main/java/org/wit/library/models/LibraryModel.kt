package org.wit.library.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LibraryModel(var id: Long = 0,
                          var title: String = "",
                          var description: String = "",
                          var category: String = "",
                          var image: String = "",
                          var factvariable: String ="",
                          var lat : Double = 0.0,
                          var lng: Double = 0.0,
                          var zoom: Float = 0f) : Parcelable

@Parcelize
data class LocationModel(var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f) : Parcelable