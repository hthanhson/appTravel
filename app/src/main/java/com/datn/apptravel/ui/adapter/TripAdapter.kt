package com.datn.apptravel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.datn.apptravel.databinding.ItemTripBinding
import com.datn.apptravel.util.Trip

class TripAdapter(
    private var trips: List<Trip>,
    private val onTripClicked: (Trip) -> Unit
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    override fun getItemCount(): Int = trips.size

    fun updateTrips(newTrips: List<Trip>) {
        trips = newTrips
        notifyDataSetChanged()
    }

    inner class TripViewHolder(private val binding: ItemTripBinding) : RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onTripClicked(trips[position])
                }
            }
        }
        
        fun bind(trip: Trip) {
            binding.apply {
                tvTripName.text = trip.title
                tvTripDate.text = "${trip.startDate} - ${trip.endDate}"
                
                // For the price, we'll just use a placeholder since Trip doesn't have a price field yet
                tvTripPrice.text = "75.000.000đ"
                
                // Set image if available
                // If trip has an image URL, load it with an image loader like Glide or Picasso
                // For now, we'll use the placeholder
            }
        }
    }
}