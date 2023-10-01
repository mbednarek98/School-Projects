package s18579.financialmanager

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import s18579.financialmanager.adapter.OperationAdapter
import s18579.financialmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*
private const val REQUEST_ADD_OPERATION = 1
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val operationAdapter by lazy { OperationAdapter(Shared.operationList, this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Financial Manager"
        setContentView(binding.root)
        binding.addButton.setOnClickListener {openAddActivity()}
        binding.summaryButton.setOnClickListener{openChartViewActivity()}
        setupOperationList()
        updateBalance()
    }

    private fun setupOperationList(){
        binding.listOfOperations.apply {
            adapter = operationAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun updateBalance()
    {
        var balanceMonth: Double = 0.0
        var balance: Double = 0.0
        for(operation in Shared.operationList){
            balance += operation.amount
            val date  = getStringDate(operation.date.time).split(".").toTypedArray();
            if(date[2].toInt() == getCurrentYear() && date[1].toInt() == getCurrentMonth()) balanceMonth +=  operation.amount
        }
        binding.balance.text = balance.toString()
        binding.balanceMonth.text = balanceMonth.toString()
        if(balance >= 0)  binding.balance.setTextColor(Color.parseColor("#20BB27"))
        else binding.balance.setTextColor(Color.parseColor("#BB2020"))
        if(balanceMonth >= 0)  binding.balanceMonth.setTextColor(Color.parseColor("#20BB27"))
        else binding.balanceMonth.setTextColor(Color.parseColor("#BB2020"))

    }

    override fun onResume() {
        super.onResume()
        operationAdapter.list = Shared.operationList
        updateBalance()
    }

    fun openAddActivity(){
        startActivity(Intent(this, AddActivity::class.java))
    }

    fun openChartViewActivity(){
        startActivity(Intent(this, ChartViewActivity::class.java))
    }

    private fun getCurrentMonth(): Int {
        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.MONTH)+1
    }

    private fun getCurrentYear(): Int {
        val date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        return cal.get(Calendar.YEAR)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
            operationAdapter.list = Shared.operationList
            updateBalance()
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun getStringDate(date: Long): String {
        return SimpleDateFormat("dd.MM.yyyy").format(Date(date))
    }
}