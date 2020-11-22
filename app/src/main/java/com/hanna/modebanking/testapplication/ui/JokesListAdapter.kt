package com.hanna.modebanking.testapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hanna.modebanking.testapplication.OpenForTesting
import com.hanna.modebanking.testapplication.R
import com.hanna.modebanking.testapplication.extensions.throttledClickListener
import com.hanna.modebanking.testapplication.model.Joke

@OpenForTesting
//Prototypes - N/A
//Tests - N/A
class JokesListAdapter(val jokeClicked: (id: Int) -> Unit) :
    ListAdapter<Joke, BindableViewHolder<Joke>>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Joke>() {
            override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
                return oldItem.joke == newItem.joke
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<Joke> {
        return JokeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.single_joke_layout, parent, false),
            jokeClicked
        )
    }

    override fun onBindViewHolder(holder: BindableViewHolder<Joke>, position: Int) {
        holder.bindView(currentList[position])

    }
}

class JokeViewHolder(itemView: View, val jokeClicked: (id: Int) -> Unit) :
    BindableViewHolder<Joke>(itemView) {
    val jokeTextView: TextView = itemView.findViewById(R.id.joke_tv)
    override fun bindView(data: Joke) {
        jokeTextView.text = data.joke
        itemView.throttledClickListener { jokeClicked(data.id) }
    }
}

abstract class BindableViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindView(data: T)
    val context: Context get() = itemView.context
}