package com.app.myapplication.Adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.Holders.InternalItemsHolder
import com.app.myapplication.Models.ExternalItemsModel
import com.app.myapplication.Models.InternalItemsModel

class InternalItemsAdapter (
    var itemsData: ArrayList<InternalItemsModel>,
    private val clickLambda: (InternalItemsModel) -> Unit):
    RecyclerView.Adapter<InternalItemsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InternalItemsHolder =
        InternalItemsHolder.create(
            parent,
            clickLambda
        )

    override fun onBindViewHolder(internalItemsHolder: InternalItemsHolder, position: Int) =
        internalItemsHolder.bind((itemsData[position]))

    override fun getItemCount() = itemsData.size

    /*fun update(itemData: ArrayList<>?) {
        if (itemData == null) {
            return
        }
        itemData.clear()
        notifyDataSetChanged()
        itemData.addAll(itemData)
        notifyDataSetChanged()
    }*/
}
