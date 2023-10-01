package mb.app.travelapp.adapter


import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mb.app.travelapp.ImgActivity
import mb.app.travelapp.MainActivity
import mb.app.travelapp.databinding.ItemPhotoBinding
import mb.app.travelapp.db.PhotoDto
import java.io.File


class PhotoViewHolder(private val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photoDto: PhotoDto, mainActivity: MainActivity){
        binding.imgView.setImageBitmap(MediaStore.Images.Media.getBitmap(mainActivity.contentResolver, Uri.fromFile(File(photoDto.imgLoc.toString()))))
        Log.d("ViewHolder",binding.imgView.toString())
    }
}

class PhotoAdapter(private val initList: List<PhotoDto>, private val main: MainActivity) : RecyclerView.Adapter<PhotoViewHolder>(){
    var list: List<PhotoDto> = initList
    set(value)
    {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return PhotoViewHolder(binding).also { holder ->
            binding.root.setOnClickListener{ parent.context.startActivity(Intent(parent.context, ImgActivity::class.java).putExtra("idPhoto",initList[holder.layoutPosition].id)) }
        }
    }


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(list[position], main)
    }


}