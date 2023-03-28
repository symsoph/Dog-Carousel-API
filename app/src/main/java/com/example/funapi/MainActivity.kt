package com.example.funapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.w3c.dom.Text
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var imageURL = ""
    var periodDate = ""
    var objectTitle = ""
    var departmentName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.buttonToPress)
        var imageView = findViewById<ImageView>(R.id.image)

        getArtImageURL()
        //Log.d("imageURL", " image URL set")
        getNextImage(button, imageView)
    }

    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            var periodText = findViewById<TextView>(R.id.period)
            var titleText = findViewById<TextView>(R.id.title)
            var departnameFinal = findViewById<TextView>(R.id.department)
            getArtImageURL()
            //random between two apis
//            var choice = Random.nextInt(2)
//
            if (imageURL.isNotEmpty()) {
                Glide.with(this)
                    .load(imageURL)
                    .fitCenter()
                    .into(imageView)
                periodText.text = "Time period: " + periodDate
                titleText.text = "Title: " + objectTitle
                departnameFinal.text = "MET Department: " + departmentName
            }
            else {
                periodText.text = "Time period: " + periodDate
                titleText.text = "Title: " + objectTitle
                departnameFinal.text = "MET Department: " + departmentName
            }
            }
        }
    private fun getArtImageURL() {
        val client = AsyncHttpClient()
        var objectId = Random.nextInt(from = 37, until = 47899)
        var clientURL = "https://collectionapi.metmuseum.org/public/collection/v1/objects/" + objectId
        client[clientURL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                //var resultsJSON = json.jsonObject.getJSONObject(0)
                //imageURL = resultsJSON.getString("url")
                imageURL = json.jsonObject.getString("primaryImageSmall")
                periodDate = json.jsonObject.getString("period")
                objectTitle = json.jsonObject.getString("title")
                departmentName = json.jsonObject.getString("department")
                if (imageURL.isNullOrBlank()) {
                    getArtImageURL()
                }
                if (periodDate.isBlank()) {
                    periodDate = "Unknown"
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Art Error", errorResponse)
            }
        }]
    }

    private fun getDogImageURL() {
        val client = AsyncHttpClient()
        var picArray = arrayOf<String>("shibes", "birds", "cats")
        var animal = Random.nextInt(2)
        var clientURL = "https://shibe.online/api/" + picArray[animal] + "?count=2&urls=true&httpsUrls=true"
        client[clientURL, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful$json")
                var resultsJSON = json.jsonArray.getJSONObject(0)
                //print(resultsJSON.toString())
                //imageURL = resultsJSON as String
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