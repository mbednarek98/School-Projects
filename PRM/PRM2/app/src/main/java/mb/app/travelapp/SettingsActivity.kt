package mb.app.travelapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mb.app.travelapp.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {
    private val pref by lazy { getSharedPreferences("SaveData",Context.MODE_PRIVATE) }
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadPref()
        binding.saveWidget.setOnClickListener {
            onSave()
            finish()
        }
    }

    private fun onSave(){
        val editor = pref.edit()
        editor.putInt("SIZE",binding.sizeText.text.toString().toInt())
        editor.putString("COLOR",binding.colorText.text.toString())
        editor.putInt("DISTANCE",binding.distanceText.text.toString().toInt())
        editor.apply()
    }

    private fun loadPref(){
        binding.sizeText.setText(pref.getInt("SIZE",50).toString())
        binding.colorText.setText(pref.getString("COLOR","Color.RED"))
        binding.distanceText.setText(pref.getInt("DISTANCE",25).toString())

    }


}