package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kodego.diangca.ebrahim.myslambook.databinding.FragmentFormPageThreeBinding
import com.kodego.diangca.ebrahim.myslambook.model.Member
import java.io.File

class FormPageThreeFragment : Fragment() {

    private lateinit var member: Member
    private lateinit var binding: FragmentFormPageThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            member = arguments?.getParcelable<Member>("Member") ?: Member()
        } else {
            Log.e("FormPageThree", "ERROR: Arguments Bundle is null.")
            member = Member()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFormPageThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
        restoreFields()
    }

    private fun initComponents() {
        with(binding) {
            btnBack.setOnClickListener { btnBackOnClickListener() }
            btnSave.setOnClickListener { btnSaveOnClickListener() }

            defineLove.setText(member.defineLove)
            defineFriendship.setText(member.defineFriendship)
            memorableExperience.setText(member.memorableExperience)
            ratingBar.rating = (member.rateMe ?: 0).toFloat()
            cloudIconPlaceholder.setOnClickListener {
                openImagePicker()
            }

        }
    }
    private fun restoreFields() {
        binding.defineLove.setText(member.defineLove)
        binding.defineFriendship.setText(member.defineFriendship)
        binding.memorableExperience.setText(member.memorableExperience)
        binding.ratingBar.rating = (member.rateMe ?: 0).toFloat()

        member.profilePic?.let { path ->
            binding.cloudIconPlaceholder.setImageURI(Uri.parse(path))
        }
    }

    private fun openImagePicker() {
        pickImageLauncher.launch("image/*")
    }
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { pickedUri ->
            val fileName = "profile_${member.firstName}_${member.lastName}.jpg"
            val file = File(requireContext().filesDir, fileName)
            try {
                requireContext().contentResolver.openInputStream(pickedUri)?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                member.profilePic = file.absolutePath
                val uri = Uri.parse(member.profilePic)
                binding.cloudIconPlaceholder.setImageURI(uri)

            } catch (e: Exception) {
                Log.e("FormPageThree", "Failed to save picked image", e)
                Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }




    private fun btnSaveOnClickListener() {
        with(binding) {
            member.defineLove = defineLove.text.toString().trim()
            member.defineFriendship = defineFriendship.text.toString().trim()
            member.memorableExperience = memorableExperience.text.toString().trim()
            member.rateMe = ratingBar.rating.toInt()

            Log.d("PAGE_THREE_SAVE", "Saving Member data before sending to MainActivity...")
            logMemberDetails(member)

            Toast.makeText(requireContext(), "Saved Successfully!", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), ViewPlanActivity::class.java)
            intent.putExtra("Member", member)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun btnBackOnClickListener() {
        member.defineLove = binding.defineLove.text.toString().trim()
        member.defineFriendship = binding.defineFriendship.text.toString().trim()
        member.memorableExperience = binding.memorableExperience.text.toString().trim()
        member.rateMe = binding.ratingBar.rating.toInt()
        val bundle = Bundle().apply {
            putParcelable("Member", member)
        }
        Log.d("PAGE_THREE_BACK", "⬅ Returning to Page Two with member data.")
        findNavController().navigate(R.id.action_formPageThreeFragment_to_formPageTwoFragment, bundle)
    }

    /**
     * Helper function to log everything for debugging.
     */
    private fun logMemberDetails(member: Member) {
        Log.d("PAGE_THREE_LOG", "----- MEMBER DATA -----")
        Log.d("PAGE_THREE_LOG", "Name: ${member.firstName} ${member.lastName}")
        Log.d("PAGE_THREE_LOG", "Email: ${member.email}")
        Log.d("PAGE_THREE_LOG", "Contact: ${member.contactNo}")
        Log.d("PAGE_THREE_LOG", "Club Name: ${member.clubName}")

        Log.d("PAGE_THREE_LOG", "Music Count: ${member.workoutMusics?.size ?: 0}")
        member.workoutMusics?.forEachIndexed { i, m ->
            Log.d("PAGE_THREE_LOG", "Music[$i]: ${m.songName}")
        }

        Log.d("PAGE_THREE_LOG", "Content Count: ${member.sportsContents?.size ?: 0}")
        member.sportsContents?.forEachIndexed { i, c ->
            Log.d("PAGE_THREE_LOG", "Content[$i]: ${c.contentName}")
        }

        Log.d("PAGE_THREE_LOG", "Event Count: ${member.events?.size ?: 0}")
        member.events?.forEachIndexed { i, e ->
            Log.d("PAGE_THREE_LOG", "Event[$i]: ${e.event}")
        }

        Log.d("PAGE_THREE_LOG", "Skill Count: ${member.skillsWithRate?.size ?: 0}")
        member.skillsWithRate?.forEachIndexed { i, s ->
            Log.d("PAGE_THREE_LOG", "Skill[$i]: ${s.skill}, Rate=${s.rate}")
        }

        Log.d("PAGE_THREE_LOG", "Love: ${member.defineLove}")
        Log.d("PAGE_THREE_LOG", "Friendship: ${member.defineFriendship}")
        Log.d("PAGE_THREE_LOG", "Experience: ${member.memorableExperience}")
        Log.d("PAGE_THREE_LOG", "Rate Me: ${member.rateMe}")
        Log.d("PAGE_THREE_LOG", "-----------------------")
    }
}