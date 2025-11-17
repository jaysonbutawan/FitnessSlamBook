package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.diangca.ebrahim.myslambook.adapter.ItemMemberAdapter
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityViewPlanBinding
import com.kodego.diangca.ebrahim.myslambook.model.Member

class ViewPlanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPlanBinding

    private val memberList = mutableListOf<Member>()
    private lateinit var memberAdapter: ItemMemberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newMember = intent.getParcelableExtra<Member>("Member")

        if (newMember != null) {
            memberList.add(newMember)
        } else {
            Log.e("VIEW_PLAN_ACTIVITY", "No new Member data. Displaying existing directory.")
        }
        setupRecyclerView()
}

    private fun setupRecyclerView() {
        memberAdapter = ItemMemberAdapter(memberList) { selectedMember ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Member", selectedMember)
            selectedMember.profilePic?.let { uriString ->
                intent.putExtra("profilePicUri", uriString)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(intent)

        }

        binding.memberRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ViewPlanActivity)
            adapter = memberAdapter
        }
    }

}
