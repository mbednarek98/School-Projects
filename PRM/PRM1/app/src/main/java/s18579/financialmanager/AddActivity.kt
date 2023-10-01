package s18579.financialmanager

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import s18579.financialmanager.databinding.ActivityAddBinding
import s18579.financialmanager.model.Operation
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Add/Edit a Operation"
        setContentView(binding.root)
        val id = getId()
        val calendar = setupCalendar(id)
        if(id != -1) setData(id)
        setupSave(calendar, id)
    }


    private fun setupSave(calendar: Calendar, id: Int){
        binding.saveButton.setOnClickListener{
            val operation = Operation(binding.name.text.toString(), binding.amount.text.toString().toDouble(), calendar.time,binding.category.text.toString())
            if(id == -1) Shared.operationList.add(operation)
            else Shared.operationList[id] = operation
            setResult(Activity.RESULT_OK)
            finish()
        }

    }

    private fun setupCalendar(id: Int):Calendar{
        val calendar = Calendar.getInstance()
        if(id != -1) calendar.time = Shared.operationList[id].date
        binding.date.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            binding.date.date = calendar.timeInMillis
        }
        return calendar
    }

    private fun setData(id:Int){
        println(Shared.operationList[id].date)
        print(SimpleDateFormat("dd/MM/yyyy").format(Shared.operationList[id].date.time))
        binding.name.setText(Shared.operationList[id].name)
        binding.amount.setText(Shared.operationList[id].amount.toString())
        binding.category.setText(Shared.operationList[id].category)
        binding.date.setDate(Shared.operationList[id].date.time)
    }

    private fun getId():Int{
        if(intent.extras?.get("id") != null) return intent.extras?.get("id") as Int;
        return -1
    }


}
