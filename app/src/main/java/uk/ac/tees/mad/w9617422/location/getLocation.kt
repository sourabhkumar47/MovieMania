package uk.ac.tees.mad.w9617422.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await
import java.util.Locale

class LocationUtils {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    suspend fun getCurrentLocation(activity: Activity): String? {

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity)

        if (ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Log.d("LocationUtils", "Location permissions not granted.")

            // Request the necessary permissions
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), LOCATION_PERMISSION_REQUEST_CODE
            )

            // If permission is denied, show a rationale or direct them to settings
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.ACCESS_COARSE_LOCATION
                )
            ) {
                // Show a rationale to the user. This could be a dialog explaining why the app needs the permission.
                // After the rationale, try requesting the permission again.
            } else {
                // If the user has denied the permission and checked "Don't ask again", direct them to the app settings
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", activity.packageName, null)
                intent.data = uri
                activity.startActivity(intent)
            }

            return null
        }

        //Gives latitude and longitude
        val location = fusedLocationClient.lastLocation.await()
        Log.d("LocationUtils", "Fetched Location: $location")

        return location?.let {
            val address = getAddressFromLocation(it.latitude, it.longitude, activity)
            //Gives location: Sonipat, India
            Log.d("LocationUtils", "Fetched address: $address")
            address
        } ?: run {
            Log.d("LocationUtils", "Location is null.")
            null
        }
    }


    private fun getAddressFromLocation(
        latitude: Double, longitude: Double, context: Context
    ): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val address = addresses?.get(0)

        //Gives complete address :Fetched Address: Address[addressLines=[0:"X2VR+24J, Sector 12,
        // Sonipat, Haryana 131001, India"],feature=X2VR+24J,admin=Haryana,sub-admin=Rohtak Division
        // ,locality=Sonipat,thoroughfare=null,postalCode=131001,countryCode=IN,countryName=India,
        // hasLatitude=true,latitude=28.9925605,hasLongitude=true,longitude=77.0400975,phone=null,
        // url=null,extras=null]
        Log.d("LocationUtils", "Fetched Address: $address")

        return if (address != null) {
            "${address.locality}, ${address.countryName}"
        } else {
            "Unknown location"
        }
    }
}