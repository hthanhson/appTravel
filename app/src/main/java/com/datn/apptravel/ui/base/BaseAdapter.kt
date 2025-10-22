package com.datn.apptravel.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * Base adapter class that provides common functionality for all RecyclerView adapters
 */
abstract class BaseAdapter<T, VH : BaseAdapter.BaseViewHolder<T, VB>, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, VH>(diffCallback) {
    
    /**
     * Abstract function to create ViewHolder
     */
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH
    
    /**
     * Bind data to ViewHolder
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
    
    /**
     * Base ViewHolder class
     */
    abstract class BaseViewHolder<T, VB : ViewBinding>(
        protected val binding: VB
    ) : RecyclerView.ViewHolder(binding.root) {
        
        /**
         * Abstract function to bind data to ViewHolder
         */
        abstract fun bind(item: T)
    }
}