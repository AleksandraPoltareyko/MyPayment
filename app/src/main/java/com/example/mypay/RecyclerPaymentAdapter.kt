package com.example.mypay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mypay.data.Payment

class RecyclerPaymentAdapter(private val payments: MutableList<Payment>): RecyclerView.Adapter<RecyclerPaymentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerPaymentHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(viewType, parent, false)
        return RecyclerPaymentHolder(view, this)
    }

    override fun getItemViewType(position: Int)=  R.layout.item

    override fun onBindViewHolder(holder: RecyclerPaymentHolder, position: Int) = holder.bind(payments[position])

    override fun getItemCount(): Int = payments.size
}