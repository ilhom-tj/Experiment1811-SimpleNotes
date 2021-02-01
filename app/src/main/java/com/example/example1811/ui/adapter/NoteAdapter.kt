package com.example.example1811.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.example1811.R
import com.example.example1811.databinding.ListItemBinding
import com.example.example1811.db.Note

class NoteAdapter (private val clickListener:(Note)->Unit)
    : RecyclerView.Adapter<MyViewHolder>()
{
    private val subscribersList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding : ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListener)
    }

    fun setList(subscribers: List<Note>) {
        subscribersList.clear()
        subscribersList.addAll(subscribers)

    }

}

class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Note,clickListener:(Note)->Unit){
        binding.title?.text = subscriber.title
        binding.content?.text = subscriber.content

    }
}