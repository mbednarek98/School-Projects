package s18579.financialmanager.model


import java.util.*


data class Operation (
        val name: String,
        val amount: Double,
        val date: Date,
        val category: String
): Comparable<Operation>{
    override fun compareTo(other: Operation): Int {
        return date.compareTo(other.date);
    }

}