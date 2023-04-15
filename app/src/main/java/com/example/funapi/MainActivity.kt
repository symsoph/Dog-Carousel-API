package com.example.funapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler

import okhttp3.Headers
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var petList: MutableList<String>
     lateinit var idList: MutableList<String>
    private lateinit var rvPets: RecyclerView
    private lateinit var rvPetsID: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //might need to specify val title
        rvPets = findViewById(R.id.pet_list)
        rvPetsID = findViewById(R.id.imageId_list)
        petList = mutableListOf()
        idList = mutableListOf()
        getDogImageURL()

        //Log.d("imageURL", " image URL set")

    }

    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {


            //random between two apis
//            var choice = Random.nextInt(2)

            }
        }

     fun getDogImageURL() {
        val client = AsyncHttpClient()
        //var picArray = arrayOf<String>("shibes", "birds", "cats")
        //var animal = Random.nextInt(2)
        //var clientURL = "https://shibe.online/api/" + picArray[animal] + "?count=2&urls=true&httpsUrls=true" --> want https://dog.ceo/api/breeds/image/random/20
        client["https://shibe.online/api/shibes?count=20&urls=false&httpsUrls=true", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful$json")
                val imageArray = json.jsonArray
                for (i in 0 until 20) {
                    petList.add("https://cdn.shibe.online/shibes/" + imageArray.getString(i) + ".jpg")
                    idList.add(imageArray.getString(i))
               }
                val adapter = PetAdapter(petList, idList)
                rvPets.adapter = adapter
                rvPets.layoutManager = LinearLayoutManager(this@MainActivity)
                rvPets.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))

                //for photo ID
//                val adapterId = PetAdapter(idList)
//                rvPetsID.adapterId = adapter
//                rvPetsID.layoutManager = LinearLayoutManager(this@MainActivity)
//                rvPetsID.addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayoutManager.VERTICAL))
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]
    }
}