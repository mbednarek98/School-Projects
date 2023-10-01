package s18579.financialmanager

import s18579.financialmanager.model.Operation
import java.util.*

object Shared {
    val operationList = mutableListOf<Operation>(
        Operation("PJATK",123.00, Calendar.getInstance().time, "Food"),
        Operation("MBank",-12.00, Calendar.getInstance().time, "Work"),
        Operation("McDonald",42.00, Calendar.getInstance().time, "Payment"),
        Operation("KFC",4.00, Calendar.getInstance().time, "Work"),
            Operation("BurgerKing",10.00, Calendar.getInstance().time, "Work"),
            Operation("fff",64.00, Calendar.getInstance().time, "Work"),
            Operation("ggg",12.00, Calendar.getInstance().time, "Work"),
            Operation("hhh",177.00, Calendar.getInstance().time, "Work"),
            Operation("iii",199.00,Calendar.getInstance().time, "Work"),
            Operation("jjj",200.00, Calendar.getInstance().time, "Work")
    )
}