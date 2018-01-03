package org.vontech.rollout.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MapStyleOptions
import org.vontech.rollout.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ui.IconGenerator
import org.vontech.rollout.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions

/**
 * The main map-like activity of the Rollout application
 * @author Aaron Vontell
 * @owner Vontech Software, LLC
 */
class MainActivity : FragmentActivity(), OnMapReadyCallback {

    // The map object within the main layout
    //private var map : GoogleMap by lazy()
    private var map: GoogleMap? = null
    private var locationGranted : Boolean = false
    private var locationApi: FusedLocationProviderClient? = null


    // Some useful constants
    private val LOCATION_PERMISSION_CODE = 123
    private val MY_LOCATION_ZOOM = 14f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        decideWhereToGo()

        setContentView(R.layout.activity_main)

        // Setup the map (is there a Kotlin-like way to do this?)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.main_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Check permissions
        checkLocationPermissions()

    }

    private fun decideWhereToGo() {

        //Log.e("DECIDER [APP-ACCESSED]", prefs.appAccessed.toString())
        //Log.e("DECIDER [ACCESS-TOKEN]", prefs.accessToken)

        // First, if never opened, or logged out, automatically go to the login screen
        if (!prefs.appAccessed || !auth.isAccessTokenAvailable()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()

        // Otherwise, start a process to check if the
        } else {

            RolloutAPI.validateUser { valid ->
                //Log.e("DECIDER [ACCESS-VALID]", valid.toString())
                if (!valid) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
            }
        }
    }

    // Once the map is ready, begin operations
    override fun onMapReady(map: GoogleMap?) {

        // Configure the artistic styling
        val style = MapStyleOptions.loadRawResourceStyle(this, R.raw.mapstyle_night)
        //map!!.setMapStyle(style)

        // Configure functional styling

        // Save the map]
        this.map = map
        beginInitialMyLocation()

    }

    @SuppressLint("MissingPermission") // We already get it previously
    private fun beginInitialMyLocation() {

        if(map != null) {
            //map!!.isMyLocationEnabled = locationGranted
            if (locationGranted) {
                locationApi = LocationServices.getFusedLocationProviderClient(this)
                locationApi!!.lastLocation.addOnSuccessListener { location ->
                    goToLocation(location, MY_LOCATION_ZOOM, false)
                    createMarker(location)
                }
            }
        }

    }

    private fun goToLocation(location: Location, zoom: Float, smooth: Boolean) {

        if (map != null) {

            var update : CameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude), zoom)

            if (smooth) {
                map!!.animateCamera(update)
            } else {
                map!!.moveCamera(update)
            }

        }

    }

    private fun createMarker(location: Location) {

        var iconGenerator = IconGenerator(this)
        var iconView = LayoutInflater.from(this).inflate(R.layout.map_pin, null, false)
        // Set profile image here here with Picasso
        //      iconView.map_icon_image
        iconGenerator.setContentView(iconView)
        iconGenerator.setColor(Color.TRANSPARENT)
        iconGenerator.setBackground(null)
        var bitmap = iconGenerator.makeIcon()
        val markerOptions = MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                .position(locationToLatLng(location))
                .anchor(iconGenerator.getAnchorU(), iconGenerator.getAnchorV())

        map!!.addMarker(markerOptions)

    }

    private fun locationToLatLng(location: Location) : LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    private fun checkLocationPermissions() {

        var locationPermissionResult = ContextCompat
                .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (locationPermissionResult == PackageManager.PERMISSION_GRANTED) {
            locationGranted = true
            beginInitialMyLocation()
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    LOCATION_PERMISSION_CODE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                locationGranted =
                        !(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                beginInitialMyLocation()
            }
        }

    }

}
