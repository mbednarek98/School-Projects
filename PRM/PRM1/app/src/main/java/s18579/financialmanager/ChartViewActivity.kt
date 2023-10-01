package s18579.financialmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ChartViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.title = "Monthly Chart"
        setContentView(R.layout.activity_chart_view)
    }
}