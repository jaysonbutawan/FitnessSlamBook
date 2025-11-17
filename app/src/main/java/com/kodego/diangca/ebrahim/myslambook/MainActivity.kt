package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityMainBinding
import com.kodego.diangca.ebrahim.myslambook.model.Member
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        member = intent.getParcelableExtra("Member") ?: Member()

        Log.d("MAIN_ACTIVITY_LOG", "===== MEMBER DATA RECEIVED =====")
        Log.d("MAIN_ACTIVITY_LOG", "Name: ${member.firstName} ${member.lastName}")
        Log.d("MAIN_ACTIVITY_LOG", "Email: ${member.email}")
        Log.d("MAIN_ACTIVITY_LOG", "Music count: ${member.workoutMusics?.size ?: 0}")
        member.workoutMusics?.forEachIndexed { i, m ->
            Log.d("MAIN_ACTIVITY_LOG", "Music[$i]: ${m.songName}")
        }

        Log.d("MAIN_ACTIVITY_LOG", "Content count: ${member.sportsContents?.size ?: 0}")
        member.sportsContents?.forEachIndexed { i, c ->
            Log.d("MAIN_ACTIVITY_LOG", "Content[$i]: ${c.contentName}")
        }

        Log.d("MAIN_ACTIVITY_LOG", "Event count: ${member.events?.size ?: 0}")
        member.events?.forEachIndexed { i, e ->
            Log.d("MAIN_ACTIVITY_LOG", "Event[$i]: ${e.event}")
        }

        Log.d("MAIN_ACTIVITY_LOG", "Skill count: ${member.skillsWithRate?.size ?: 0}")
        member.skillsWithRate?.forEachIndexed { i, s ->
            Log.d("MAIN_ACTIVITY_LOG", "Skill[$i]: ${s.skill}, Rate=${s.rate}")
        }

        Log.d("MAIN_ACTIVITY_LOG", "================================")
        displayMemberDetails()
    }

    private fun displayMemberDetails() {
        with(binding) {

            val profileUriString = intent.getStringExtra("profilePicUri")
            val profileUri = profileUriString?.toUri()
            if (profileUri != null) {
                try {
                   profilePicture.setImageURI(profileUri)
                } catch (e: Exception) {
                   profilePicture.setImageResource(R.drawable.profile_icon)
                    Log.e("MAIN_ACTIVITY", "Failed to load profile URI", e)
                }
            } else {
                profilePicture.setImageResource(R.drawable.profile_icon)
            }

            backButton.setOnClickListener {
              back()
            }



            detailName.text = "${member.firstName ?: ""} ${member.lastName ?: ""}"
            detailBirthdate.text = member.birthDate ?: ""
            detailContact.text = member.contactNo ?: ""
            detailAddress.text = member.address ?: ""
            detailGender.text = member.gender ?: ""
            detailStatus.text = member.status ?: ""
            detailEmail.text = member.email ?: ""

            detailClubName.text = member.clubName ?: ""
            detailDefineLove.text = member.defineLove ?: ""
            detailDefineFriendship.text = member.defineFriendship ?: ""
            detailMemorableExperience.text = member.memorableExperience ?: ""
            ratingBar.rating = (member.rateMe ?: 0).toFloat()

            chipWorkoutMusic.removeAllViews()
            member.workoutMusics?.forEach { music ->
                val chip = Chip(this@MainActivity)
                chip.text = music.songName
                chip.isCheckable = false
                chip.isClickable = false
                chip.setChipBackgroundColorResource(R.color.light_orange)
                chip.setTextColor(getColor(R.color.primary))
                chipWorkoutMusic.addView(chip)
            }

            chipSportsContent.removeAllViews()
            member.sportsContents?.forEach { content ->
                val chip = Chip(this@MainActivity)
                chip.text = content.contentName
                chip.isCheckable = false
                chip.isClickable = false
                chip.setChipBackgroundColorResource(R.color.light_orange)
                chip.setTextColor(getColor(R.color.primary))
                chipSportsContent.addView(chip)
            }

            chipEvents.removeAllViews()
            member.events?.forEach { event ->
                val chip = Chip(this@MainActivity)
                chip.text = event.event
                chip.isCheckable = false
                chip.isClickable = false
                chip.setChipBackgroundColorResource(R.color.light_orange)
                chip.setTextColor(getColor(R.color.primary))
                chipEvents.addView(chip)
            }

            chipSkills.removeAllViews()
            member.skillsWithRate?.forEach { skill ->
                val chip = Chip(this@MainActivity)
                chip.text = "${skill.skill} (${skill.rate}/5)"
                chip.isCheckable = false
                chip.isClickable = false
                chip.setChipBackgroundColorResource(R.color.light_orange)
                chip.setTextColor(getColor(R.color.primary))
                chipSkills.addView(chip)
            }
        }
    }
    fun back(){
        val intent = Intent(this, ViewPlanActivity::class.java)
        intent.putExtra("Member", member)
        startActivity(intent)
        finish()

    }
}
