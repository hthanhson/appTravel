package com.datn.apptravel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.datn.apptravel.databinding.ItemScheduleDayBinding
import com.datn.apptravel.model.ScheduleDay
import java.text.SimpleDateFormat
import java.util.Locale

class ScheduleDayAdapter(
    private var scheduleDays: List<ScheduleDay>
) : RecyclerView.Adapter<ScheduleDayAdapter.ScheduleDayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleDayViewHolder {
        val binding = ItemScheduleDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScheduleDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleDayViewHolder, position: Int) {
        holder.bind(scheduleDays[position])
    }

    override fun getItemCount(): Int = scheduleDays.size

    fun updateScheduleDays(newScheduleDays: List<ScheduleDay>) {
        scheduleDays = newScheduleDays
        notifyDataSetChanged()
    }

    inner class ScheduleDayViewHolder(private val binding: ItemScheduleDayBinding) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(scheduleDay: ScheduleDay) {
            binding.apply {
                tvDayNumber.text = "Day ${scheduleDay.dayNumber}"
                tvDayTitle.text = scheduleDay.title
                tvDate.text = formatDate(scheduleDay.date)
                
                // Setup the activities RecyclerView if needed
                if (scheduleDay.activities.isNotEmpty()) {
                    val activitiesAdapter = ScheduleActivityAdapter(scheduleDay.activities)
                    rvScheduleActivities.adapter = activitiesAdapter
                    rvScheduleActivities.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(binding.root.context)
                }
            }
        }
        
        private fun formatDate(dateString: String): String {
            // Format the date as needed, e.g. convert "2023-06-10" to "June 10, 2023"
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                return date?.let { outputFormat.format(it) } ?: dateString
            } catch (e: Exception) {
                return dateString
            }
        }
    }
}