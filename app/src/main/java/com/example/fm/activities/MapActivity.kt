package com.example.fm.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fm.R
import com.example.fm.other.Constants
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.GeoDataClient
import com.google.android.gms.location.places.PlaceDetectionClient
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class MapActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val constants = Constants()
    private val TAG: String = "MapActivity"
    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private val LAT_LNG_BOUNDS = LatLngBounds(LatLng(-40.0, -168.0), LatLng(71.0, 136.0))

    private var locationPermissionGranted = false
    private lateinit var map: GoogleMap
    private lateinit var geoDataClient: GeoDataClient
    private lateinit var placeDetectionClient: PlaceDetectionClient
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mGps: ImageView
    private lateinit var btMenuLeft: ImageButton
    private lateinit var btSearch: ImageButton
    private lateinit var searchTextView: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        mGps = findViewById(R.id.ic_gps);
        btSearch = findViewById(R.id.bt_search)
        searchTextView = findViewById(R.id.search_textview)

        getLocationPermission()

    }

    private fun getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permission")
        val permissions = arrayOf(FINE_LOCATION, COARSE_LOCATION)

        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                FINE_LOCATION
            ) === PackageManager.PERMISSION_GRANTED
        ) {
            if (ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    COARSE_LOCATION
                ) === PackageManager.PERMISSION_GRANTED
            ) {
                locationPermissionGranted = true

                initMap()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    permissions,
                    constants.LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                constants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun initMap() {
        Log.d(TAG, "initMap: initializing map")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(OnMapReadyCallback { googleMap ->
            Toast.makeText(this@MapActivity, "Map is ready", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onMapReady: map is ready")
            map = googleMap

            if (locationPermissionGranted) {
                getDeviceLocation()

                if (ActivityCompat.checkSelfPermission(
                        this@MapActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this@MapActivity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) !== PackageManager.PERMISSION_GRANTED
                ) {
                    return@OnMapReadyCallback
                }

                map.setMyLocationEnabled(true)

                init()
            }

            //            map.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { marker ->
            //                for (i in events.indices) {
            //                    if (events.get(i).getTitle().equals(marker.title)) {
            //                        setEventInfo(events.get(i))
            //                        break
            //                    }
            //                }
            //                showBottomSheet(infoEventBehavior) //////
            //                infoEvent.setVisibility(View.VISIBLE)
            //                true
            //            })

            //            infoEventBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            //                fun onStateChanged(@NonNull view: View, i: Int) {
            //                    if (i == infoEventBehavior.STATE_HIDDEN)
            //                        openBottomFloatingButton.show()
            //                    else
            //                        openBottomFloatingButton.hide()
            //                }
            //
            //                fun onSlide(@NonNull view: View, v: Float) {
            //
            //                }
            //            })

            //            addNewEvent()
        })
    }

    private fun init() {

        placeDetectionClient = Places.getPlaceDetectionClient(this, null);
        geoDataClient = Places.getGeoDataClient(this, null)

//        mPlaceAutocompleteAdapter = PlaceAutocompleteAdapter(this, mGeoDataClient, LAT_LNG_BOUNDS, null)


//        editTextSearch.setOnItemClickListener(mAutocompleteClickListener)
//        editTextSearch.setAdapter(mPlaceAutocompleteAdapter)
//        editTextSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH
//                || actionId == EditorInfo.IME_ACTION_DONE
//                || event.action == KeyEvent.ACTION_DOWN
//                || event.action == KeyEvent.KEYCODE_ENTER
//            ) {
//
//                //execute method
//                geoLocate()
//            }
//            false
//        })

        mGps.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "onClick: clicked gps icon")
            getDeviceLocation()
        })

//        hideSoftKeyBoard()

    }

    private fun getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location")

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        try {
            if (locationPermissionGranted) {
                val location = fusedLocationProviderClient.lastLocation

                location.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "onComplete: found location!")

                        val currentLocation = task.result
                        if (currentLocation != null) {
                            moveCamera(
                                LatLng(
                                    currentLocation.latitude,
                                    currentLocation.longitude
                                ),
                                constants.DEFAULT_ZOOM, "My location"
                            )
                        }
                    } else {
                        Log.d(TAG, "onComplete: found location!")
                        Toast.makeText(this@MapActivity, "unable to get current", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.d(TAG, "getDeviceLocation: SecurityException: " + e.message)
        }

    }

    private fun moveCamera(latLng: LatLng, zoom: Float, title: String) {
        Log.d(TAG, "move the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude)

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))

//        if (title != "My location") {
//            addOptionsAndMarker(latLng, title)
//        }

//        setPlaceInfo(mPlace)

//        hideSoftKeyBoard()

    }


}

