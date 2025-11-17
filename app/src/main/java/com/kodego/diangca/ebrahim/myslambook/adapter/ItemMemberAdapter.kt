package com.kodego.diangca.ebrahim.myslambook.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kodego.diangca.ebrahim.myslambook.R
import com.kodego.diangca.ebrahim.myslambook.databinding.ItemMemberCardBinding
import com.kodego.diangca.ebrahim.myslambook.model.Member

class ItemMemberAdapter(
    private val members: MutableList<Member>,
    private val onMemberClick: (Member) -> Unit
) : RecyclerView.Adapter<ItemMemberAdapter.MemberViewHolder>() {

    inner class MemberViewHolder(val binding: ItemMemberCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: Member) {
            val fullName = "${member.firstName ?: ""} ${member.lastName ?: ""}".trim()
            binding.memberName.text = fullName.ifEmpty { "Unnamed Member" }
            binding.memberTitle.text = member.clubName ?: "Gym Buddy"

            if (!member.profilePic.isNullOrEmpty()) {
                val uri = Uri.parse(member.profilePic)
                binding.profileImage.setImageURI(uri)
            } else {
                binding.profileImage.setImageResource(R.drawable.profile_icon)
            }

            binding.root.setOnClickListener {
                onMemberClick(member)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemMemberCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int = members.size
}
