package com.kodego.diangca.ebrahim.myslambook.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Music(
    var songName: String = ""
) : Parcelable