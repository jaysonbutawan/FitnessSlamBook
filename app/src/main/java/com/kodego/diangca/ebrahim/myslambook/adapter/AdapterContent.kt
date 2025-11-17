package com.kodego.diangca.ebrahim.myslambook.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.RecycleViewSingleItemBinding
import com.kodego.diangca.ebrahim.myslambook.model.Content

class AdapterContent(var context: Context, var content: ArrayList<Content>) :
RecyclerView.Adapter<AdapterContent.MovieViewHolder>(){


    inner class MovieViewHolder(
        private val context: Context,
        private val itemBinding: RecycleViewSingleItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        var content = Content()

        fun bindStudent(content: Content) {
            this.content = content
            itemBinding.itemName.text = "${content.contentName}"

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(itemBinding, adapterPosition)
            }
            itemBinding.updateBtn.setOnClickListener {
                btnUpdateOnClickListener(itemBinding, adapterPosition)
            }
        }

        override fun onClick(view: View?) {
            if (view!=null)
                Snackbar.make(
                    itemBinding.root,
                    "${itemBinding.itemName}",
                    Snackbar.LENGTH_SHORT
                ).show()
        }

    }

    private fun btnRemoveOnClickListener(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {

        removeSong(itemBinding, positionAdapter)
    }

    private fun btnUpdateOnClickListener(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        updateSong(itemBinding, positionAdapter)
    }

    private fun removeSong(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        var alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete?")
        alertDialogBuilder.setMessage("Are you sure you want to delete ${content[positionAdapter].contentName}")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, position: Int) {
                content.removeAt(positionAdapter)
                notifyItemRemoved(positionAdapter);
                notifyItemRangeChanged(positionAdapter, itemCount);
                Snackbar.make(
                    itemBinding.root,
                    "Sports content has been successfully removed.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }).show()
    }
    private fun updateSong(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        val currentItem = content[positionAdapter]

        // Create a dialog with an EditText for the update
        val editText = EditText(context)
        editText.setText(currentItem.contentName)
        editText.hint = "Update song name"

        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Update Song")
        alertDialogBuilder.setView(editText)
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Save") { _, _ ->
            val updatedName = editText.text.toString().trim()
            if (updatedName.isNotEmpty()) {
                content[positionAdapter].contentName = updatedName
                notifyItemChanged(positionAdapter)
                Snackbar.make(
                    itemBinding.root,
                    "Song name updated successfully.",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    itemBinding.root,
                    "Song name cannot be empty.",

                    
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        alertDialogBuilder.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            RecycleViewSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(context, itemBinding)
    }

    override fun getItemCount(): Int {
        return content.size
        print("SIZE: ${content.size}")
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindStudent(content[position])
    }
}