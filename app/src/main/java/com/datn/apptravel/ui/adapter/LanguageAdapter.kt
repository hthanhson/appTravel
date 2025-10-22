package com.datn.apptravel.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.datn.apptravel.data.model.Language
import com.datn.apptravel.databinding.ItemLanguageBinding
import com.datn.apptravel.ui.base.BaseAdapter

/**
 * Adapter for language selection RecyclerView
 */
class LanguageAdapter(
    private val languages: List<Language>,
    private val onLanguageSelected: (Language) -> Unit
) : BaseAdapter<Language, LanguageAdapter.LanguageViewHolder, ItemLanguageBinding>(LanguageDiffCallback()) {
    
    init {
        submitList(languages)
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding = ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }
    
    inner class LanguageViewHolder(
        binding: ItemLanguageBinding
    ) : BaseViewHolder<Language, ItemLanguageBinding>(binding) {
        
        override fun bind(item: Language) {
            binding.apply {
                tvLanguageName.text = item.name
                ivFlag.setImageResource(item.flagResource)
                
                // Update selection state
                radioButton.isChecked = item.isSelected
                
                // Set click listener for the entire item
                root.setOnClickListener {
                    onLanguageSelected(item)
                }
                
                // Set click listener for the radio button
                radioButton.setOnClickListener {
                    onLanguageSelected(item)
                }
            }
        }
    }
    
    /**
     * DiffUtil callback for Language items
     */
    private class LanguageDiffCallback : DiffUtil.ItemCallback<Language>() {
        override fun areItemsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem.name == newItem.name
        }
        
        override fun areContentsTheSame(oldItem: Language, newItem: Language): Boolean {
            return oldItem == newItem
        }
    }
}