package com.app.myapplication.Holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.myapplication.Models.ExternalItemsModel
import com.app.myapplication.Models.InternalItemsModel
import com.app.myapplication.R
import kotlinx.android.extensions.LayoutContainer

class InternalItemsHolder(
    override val containerView: View,
    private val clickLambda: (InternalItemsModel) -> Unit
    ) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(internalItemsModel: InternalItemsModel) {
        containerView.findViewById<TextView>(R.id.dataTV).text = internalItemsModel.data
        containerView.findViewById<TextView>(R.id.internalItemContent).text = internalItemsModel.itemContent
        itemView.setOnClickListener { clickLambda(internalItemsModel) }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (InternalItemsModel) -> Unit) =
            InternalItemsHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.fragment_iteminternal, parent, false
                ),
                clickLambda
            )

    }
}
