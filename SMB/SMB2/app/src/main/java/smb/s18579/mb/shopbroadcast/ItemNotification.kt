package smb.s18579.mb.shopbroadcast



import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ItemNotification (appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        try{
            val productId = inputData.getLong("id", 0)
            val channelId = inputData.getString("channelID") ?: ""
            val productName = inputData.getString("name")
            val productQuant = inputData.getInt("quant", 0)
            val productPrice = inputData.getDouble("price",0.0)

            val addProductIntent = Intent().also {
                it.component = ComponentName("smb.s18579.mb.shoplist", "smb.s18579.mb.shoplist.ProductListActivity")
            }
            with (addProductIntent){
                putExtra("ITEM_ID", productId)
                putExtra("ITEM_NAME", productName)
                putExtra("ITEM_QUANT", productQuant)
                putExtra("ITEM_PRICE", productPrice)
            }

            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                1,
                addProductIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )

            val notification = NotificationCompat.Builder(
                applicationContext,
                channelId
            ).setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Added product $productName")
                .setContentText("With $productQuant Quantity and $productPrice Price")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(applicationContext).notify(0, notification)
        }
        catch (e: java.lang.Exception){
            return Result.failure()
        }

        return Result.success()
    }
}