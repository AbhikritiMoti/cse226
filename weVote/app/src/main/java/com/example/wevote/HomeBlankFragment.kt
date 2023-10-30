package com.example.wevote

import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import android.Manifest
import android.location.Geocoder
import android.text.TextUtils
import androidx.cardview.widget.CardView
import java.io.IOException
import java.util.Locale



class HomeBlankFragment : Fragment() {

    private lateinit var currentlocation: TextView
    private lateinit var geocoder: Geocoder
    private val LOCATION_PERMISSION_REQUEST_CODE = 123

    private lateinit var usernameTextView: TextView
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_blank, container, false)

        val voteCampaign = view.findViewById<View>(R.id.voteCampaign)
        voteCampaign.setOnClickListener {

            val voteFragment = ongoingBlankFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, voteFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val createCampaign = view.findViewById<View>(R.id.createCampaign)
        createCampaign.setOnClickListener {

            val createFragment = createBlankFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, createFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val closeCampaign = view.findViewById<View>(R.id.closeCampaign)
        closeCampaign.setOnClickListener {

            val closeFragment = closeBlankFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, closeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val faq = view.findViewById<View>(R.id.faqCampaign)
        faq.setOnClickListener {

            val faq = faqBlankFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, faq)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val profile = view.findViewById<View>(R.id.cv0)
        profile.setOnClickListener {

            val profile = profileBlankFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainer, profile)
            transaction.addToBackStack(null)
            transaction.commit()
        }


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
                currentlocation.text = "$address"
                //Lat: $latitude, Lon: $longitude, Address:
            }
        }
//        // Initialize the UserViewModel
//        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//
//        // Observe the readAllData LiveData to get the user data
//        userViewModel.readAllData.observe(viewLifecycleOwner, { users ->
//            if (users.isNotEmpty()) {
//                // Assuming you want the first user's username
//                val username = users[0].username
//                usernameTextView.text = "$username!"
//            }
//        })

        // Retrieve the username from the fragment arguments
        usernameTextView = view.findViewById(R.id.usernametext)
        val username = arguments?.getString("username")

        if (!username.isNullOrEmpty()) {
            usernameTextView.text = "$username"
        }


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
}