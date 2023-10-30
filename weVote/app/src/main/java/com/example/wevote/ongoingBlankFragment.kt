package com.example.wevote

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Criteria
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.util.Locale


class ongoingBlankFragment : Fragment() {

    private lateinit var listView: ListView

    private lateinit var currentlocation: TextView
    private lateinit var geocoder: Geocoder
    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    private lateinit var usernameTextView: TextView

    private lateinit var tv: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ongoing_blank, container, false)
        tv = view.findViewById(R.id.textView)
        val font = Typeface.createFromAsset(requireActivity().assets, "voiceinmyhead.otf")
        tv.typeface = font



        currentlocation = view.findViewById(R.id.currentlocation)
        currentlocation.ellipsize = TextUtils.TruncateAt.MARQUEE
        currentlocation.marqueeRepeatLimit = -1 // -1 for infinite marquee
        currentlocation.isSelected = true

        geocoder = Geocoder(requireContext(), Locale.getDefault())

        requestLocationPermission()
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Check if the location provider is enabled
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Get the last known location
            val location = getLastKnownLocation()

            // Display the location in the TextView
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val address = getAddressFromLocation(latitude, longitude)
                currentlocation.text ="$address, Lat: $latitude, Lon: $longitude"
            }
        }


        listView = view.findViewById(R.id.listView)


        return view
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun getLastKnownLocation(): Location? {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Get the best available provider
        val provider = locationManager.getBestProvider(
            Criteria(), true
        )

        if (provider != null && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return locationManager.getLastKnownLocation(provider)
        }

        return null
    }
    private fun getAddressFromLocation(latitude: Double, longitude: Double): String {
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null) {
                if (addresses.isNotEmpty()) {
                    val address = addresses?.get(0)
                    if (address != null) {
                        return address.getAddressLine(0)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "Address not found"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming you have a method to retrieve the data from your database
        val dataList = getDataFromDatabase()

        val adapter = CustomListAdapter(requireContext(), dataList)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            // Get the data associated with the clicked item if needed.
            val clickedItem = dataList[position] // Assuming dataList contains your data

            // Retrieve the campaign ID from the clicked item
            val campaignId = clickedItem.id
            Log.d("CampaignDetailsActivity", "campaignId: $campaignId")
            Log.d("CampaignDetailsActivity", "clickedItem: $clickedItem")

            val intent = Intent(requireActivity(), CampaignDetailsActivity::class.java)
            intent.putExtra("campaignId", campaignId)
            startActivity(intent)
        }
    }

    // Add a method to retrieve data from the database
    private fun getDataFromDatabase(): List<DataModel> {
        // Replace this with your database retrieval logic
        val db = DatabaseHandler(requireContext())
        return db.getAllData()
    }
}