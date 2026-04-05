package com.example.travelcompanion.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.travelcompanion.data.EventEntity
import com.example.travelcompanion.databinding.ItemEventBinding
import com.example.travelcompanion.util.EventFormatter

class EventAdapter(
    private val onEdit: (EventEntity) -> Unit,
    private val onDelete: (EventEntity) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private val events = mutableListOf<EventEntity>()

    fun submitList(items: List<EventEntity>) {
        events.clear()
        events.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class EventViewHolder(
        private val binding: ItemEventBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: EventEntity) {
            binding.titleText.text = event.title
            binding.categoryText.text = event.category
            binding.locationText.text = event.location.ifBlank { "No location added" }
            binding.dateText.text = EventFormatter.formatDateTime(event.eventTimeMillis)

            binding.editButton.setOnClickListener { onEdit(event) }
            binding.deleteButton.setOnClickListener { onDelete(event) }
        }
    }
}
