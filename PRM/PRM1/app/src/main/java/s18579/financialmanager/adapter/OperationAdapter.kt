package s18579.financialmanager.adapter

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import s18579.financialmanager.AddActivity
import s18579.financialmanager.MainActivity
import s18579.financialmanager.R
import s18579.financialmanager.Shared
import s18579.financialmanager.databinding.ItemOperationBinding;
import s18579.financialmanager.model.Operation
import java.text.SimpleDateFormat
import java.util.*

class OperationViewHolder(private val binding: ItemOperationBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(operation: Operation){
        with(binding){
            category.text = operation.category
            date.text = getStringDate(operation.date.time)
            name.text = operation.name
            amount.text = operation.amount.toString()
            if(operation.amount >= 0){
                binding.amount.setTextColor(Color.parseColor("#20BB27"))
                image.setImageDrawable(loadDrawables()[0])
            }
            else{
                binding.amount.setTextColor(Color.parseColor("#BB2020"))
                image.setImageDrawable(loadDrawables()[1])
            }
        }
    }
    private fun loadDrawables(): List<Drawable>
    {
        val draw = listOf(
            R.drawable.ic_up,
            R.drawable.ic_down
        )
        return draw.map {binding.root.resources.getDrawable(it, binding.root.resources.newTheme()) }
    }

    private fun getStringDate(date: Long): String {
        return SimpleDateFormat("dd.MM.yy").format(Date(date))
    }
}

class OperationAdapter(initList: List<Operation>, private val main: MainActivity) : RecyclerView.Adapter<OperationViewHolder>(){
    var list: List<Operation> = initList
        set(value)
        {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val binding = ItemOperationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OperationViewHolder(binding).also { holder ->
            binding.root.setOnLongClickListener{ removeOperation(holder.layoutPosition,parent) }
            binding.root.setOnClickListener{ parent.context.startActivity(Intent(parent.context, AddActivity::class.java).putExtra("id",holder.layoutPosition)) }
        }
    }

    private fun removeOperation(id: Int, parent: ViewGroup): Boolean
    {
        val builder = AlertDialog.Builder(parent.context)
        builder.setTitle("Warning")
                .setMessage("Do you want to delete this operation?")
                .setCancelable(true)
                .setPositiveButton("YES") { _, _ ->
                    Shared.operationList.removeAt(id)
                    list = Shared.operationList
                    main.updateBalance()
                    notifyItemChanged(id)
                }
                .setNegativeButton("NO") { dialog, _ -> dialog.dismiss() }
        val alert = builder.create()
        alert.setOnShowListener{alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)}
        alert.show()
        return true;
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        holder.bind(list[position])
    }

}
