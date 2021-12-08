package com.example.assignment2_19012021006


import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.assignment2_19012021006.databinding.ActivityMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermmison()
        LoadPockemon()
    }

    var ACCESSLOCATION = 123
    fun checkPermmison(){

        if (Build.VERSION.SDK_INT>=23){
            if (ActivityCompat
                    .checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESSLOCATION)
                return
            }
        }

        GetUserLocation()
    }

    fun GetUserLocation(){
        Toast.makeText(this,"User Location access on",Toast.LENGTH_LONG).show()

        var myLocation = MylocationListener()

        var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)

        var mythread=myThread()
        mythread.start()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){

            ACCESSLOCATION->{
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    GetUserLocation()
                }else
                {
                    Toast.makeText(this,"We cannot access to your location",Toast.LENGTH_LONG).show()
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

    }

    var location:Location?=null

    // Get user location
    inner class MylocationListener:LocationListener{

        constructor(){
            location= Location("Start")
            location!!.longitude=0.0
            location!!.latitude=0.0
        }

        override fun onLocationChanged(p0: Location) {
            location=p0
        }
    }

    inner class myThread:Thread{
        constructor():super(){

        }

        override fun run() {
            while (true){
                try {
                    runOnUiThread {

                        mMap!!.clear()

                        //show me
                        val myLoc = LatLng(location!!.latitude, location!!.longitude)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(myLoc)
                                .title("Me")
                                .snippet(" here is my Location")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.trainer))
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLoc, 14f))

//                        if(mMap != null){
//                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                        }
                        //show pokemons

                        for (i in 0 until listPokemons.size){
                            var newPokemon=listPokemons[i]

                            if (newPokemon.IsCatch==false){
                                val pokemonLoc = LatLng(newPokemon.location!!.latitude, newPokemon.location!!.longitude)
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(pokemonLoc)
                                        .title(newPokemon.name!!)
                                        .snippet(newPokemon.des!! +", power:"+newPokemon!!.power)
                                        .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!)))

                                if (location!!.distanceTo(newPokemon.location)<2){
                                    newPokemon.IsCatch = true
                                    listPokemons[i]=newPokemon
                                    playerPower+=newPokemon.power!!
                                    Toast.makeText(applicationContext,"You catch new pokemon your new power is "+playerPower,Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                    }
                    Thread.sleep(10000)
                }catch (ex:Exception){}
            }

            super.run()
        }
    }

    var playerPower=0.0
    var listPokemons = ArrayList<Pokemon>()

    fun LoadPockemon(){
        listPokemons.add(Pokemon(R.drawable.gangar, "Gangar","Ghost",90.5,23.5951381891,72.3732733726))
        listPokemons.add(Pokemon(R.drawable.evee, "Evee","normal",60.5,23.6180247849,72.3663854599))
        listPokemons.add(Pokemon(R.drawable.bulbsur, "Bulbsur","grass",55.0,23.5926212063,72.3564291))
        listPokemons.add(Pokemon(R.drawable.charizard, "Charizard","Fire + Fly",160.5,23.6275503216, 72.3574099305))
        listPokemons.add(Pokemon(R.drawable.arceus, "Arceus","All",300.0,23.6268323066, 72.3357439041))
        listPokemons.add(Pokemon(R.drawable.darkrai, "Darkrai","dark",160.5,23.5713822411, 72.3885726929))
        listPokemons.add(Pokemon(R.drawable.dialga, "Dialga","Steel + Drg",260.0,23.5712249027, 72.3709774017))
        listPokemons.add(Pokemon(R.drawable.giratina, "Giratina","Dark + Drg",300.0,23.5663473161, 72.3490905761))
        listPokemons.add(Pokemon(R.drawable.mew, "Mew","Normal",300.0,23.5710675641, 72.3263454437))
        listPokemons.add(Pokemon(R.drawable.mewtwo, "Mewtwo","Psychic ",300.0,23.5897895429, 72.3200798034))
        listPokemons.add(Pokemon(R.drawable.palkia, "Palkia","Drg",260.0,23.6503423427, 72.3553562164))
        listPokemons.add(Pokemon(R.drawable.rayquaza, "Rayquaza","Drg",300.0,23.6350101893, 72.3832941055))
    }
}