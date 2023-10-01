package s18579.financialmanager.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import s18579.financialmanager.Shared
import s18579.financialmanager.model.Operation
import java.util.*


class Chart (context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 10f
        textSize = 20f
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return

        with(canvas){
            val calPom = Calendar.getInstance()
            calPom.add(Calendar.MONTH, -1)
            calPom.set(Calendar.DAY_OF_MONTH, 1)
            val calPom2 = Calendar.getInstance()
            calPom2.add(Calendar.MONTH, -1)
            calPom2.set(Calendar.DAY_OF_MONTH, 6)
            val calPom3 = Calendar.getInstance()
            calPom3.add(Calendar.MONTH, -1)
            calPom3.set(Calendar.DAY_OF_MONTH, 29)
            val opList = mutableListOf<Operation>(
                Operation("Jacex", 420.00, calPom.time, "Work"),
                Operation("Jacex", -420.00, calPom2.time, "Work"),
                Operation("Jacex", 42.00, calPom3.time, "Work"),
                Operation("Jacex", 4.00, Calendar.getInstance().time, "Work"),
                Operation("Jacex", 0.00, Calendar.getInstance().time, "Work"),
                Operation("Jacex", 420.00, Calendar.getInstance().time, "Work"),
                Operation("Jacex", 420.00, Calendar.getInstance().time, "Work"),
                Operation("Jacex", 420.00, Calendar.getInstance().time, "Work"),
                Operation("Jacex", 420.00, Calendar.getInstance().time, "Work"),
                Operation("Jacex", 420.00, Calendar.getInstance().time, "Work")
            )
            drawLine(100f, 100f, 100f, 965f, paint) // y-axis
            drawLine(100f,  height.toFloat()/2, width.toFloat()-50f, height.toFloat()/2, paint) // x-axis
            for(i in getFirstDayOfLastMonth().get(Calendar.DAY_OF_MONTH)..getLastDayOfLastMonth().get(Calendar.DAY_OF_MONTH)){    // days
                drawLine(100f+ 30f*i, height.toFloat()/2-10f, 100f+ 30f*i, height.toFloat()/2+10f, paint)
                drawText(i.toString(),100f + 30f*i, height.toFloat()/2+25f,paint)
            }
            var listLastMonth = mutableListOf<Operation>()
            for (operation in opList) if (operation.date.time >= getFirstDayOfLastMonth().timeInMillis && operation.date.time <= getLastDayOfLastMonth().timeInMillis) listLastMonth.add(operation)
            listLastMonth.sortBy { it.date }
            var sum: Double = 0.0
            var max: Double = 0.0
            var min: Double = 0.0
            for(i in getFirstDayOfLastMonth().get(Calendar.DAY_OF_MONTH)..getLastDayOfLastMonth().get(Calendar.DAY_OF_MONTH)){
                for (op in listLastMonth){
                    if(i == op.date.date){
                        sum += op.amount
                        if(max < sum) max = sum
                        if(min > sum) min = sum
                        drawText("$max $min",100f + 30f*i, height.toFloat()/2-20f,paint)
                    }
                }
            }

        }
    }

    private fun getFirstDayOfLastMonth(): Calendar {
        val calFirst = Calendar.getInstance()
        calFirst.add(Calendar.MONTH,-1)
        calFirst.set(Calendar.DAY_OF_MONTH, 1)
        calFirst.set(Calendar.HOUR, 1)
        calFirst.set(Calendar.MINUTE, 1)
        calFirst.set(Calendar.MILLISECOND, 1)
        return calFirst
    }

    private fun getLastDayOfLastMonth(): Calendar {
        val calLast = Calendar.getInstance()
        calLast.set(Calendar.DATE, 1)
        calLast.add(Calendar.DATE, -2)
        calLast.set(Calendar.HOUR, 23)
        calLast.set(Calendar.MINUTE, 59)
        calLast.set(Calendar.MILLISECOND, 59)
        return calLast
    }

}