package com.example.roomeme

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.roomeme.databinding.ListItemMatcherBinding
import com.example.roomeme.model.User

class MatcherAdapter: ListAdapter<User, MatcherAdapter.MyViewHolder>(USER_LIST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(ListItemMatcherBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.run item@ {
            with(holder.binding as ListItemMatcherBinding) {

                with(tvRank) {
                    text = (position + 1).toString()
                }

                with(tvName) {
                    text = this@item.name
                }

                with(tvDepartment) {
                    text = this@item.department
                }

                with(tvMatchPercent) {
                    text = this@item.matchPercent.toString() + "%"
                }
            }
        }
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        lateinit var binding: ViewBinding

        constructor(binding: ViewBinding): this(binding.root) {
            this.binding = binding
        }
    }

    companion object {
        private val USER_LIST_COMPARATOR = object: DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }
}


