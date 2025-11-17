package com.kodego.diangca.ebrahim.myslambook.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Member(
    var profilePic: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var nickName: String? = null,
    var clubName: String? = null,
    var birthDate: String? = null,
    var gender: String? = null,
    var status: String? = null,
    var email: String? = null,
    var contactNo: String? = null,
    var address: String? = null,
    var workoutMusics: ArrayList<Music> = arrayListOf(),
    var sportsContents: ArrayList<Content> = arrayListOf(),
    var events: ArrayList<Event> = arrayListOf(),
    var skillsWithRate: ArrayList<Skill> = arrayListOf(),
    var defineLove: String? = null,
    var defineFriendship: String? = null,
    var memorableExperience: String? = null,
    var describeMe: String? = null,
    var adviceForMe: String? = null,
    var rateMe: Int? = null
) : Parcelable
