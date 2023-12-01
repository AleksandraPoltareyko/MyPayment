package com.example.mypay

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mypay.data.Payment
import java.util.*

class RecyclerPaymentHolder(itemView: View, adapter: RecyclerPaymentAdapter): RecyclerView.ViewHolder(itemView) {

    private val idPayment: TextView = itemView.findViewById(R.id.id)
    private val title: TextView = itemView.findViewById(R.id.title)
    private val amount: TextView = itemView.findViewById(R.id.amount)
    private val created: TextView = itemView.findViewById(R.id.created)


    fun bind(payment: Payment){
        idPayment.text = payment.id
        title.text = payment.title
        val amountStr = String.format(Locale.FRENCH, "%#,.2f", payment.amount);
        amount.text = amountStr
        created.text = payment.created
    }

}