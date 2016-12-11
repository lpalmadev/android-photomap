package mx.lpalma.photomap.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mx.lpalma.photomap.R;
import mx.lpalma.photomap.db.PhotoData;
import mx.lpalma.photomap.dialog.PhotoLocationDialog;
import mx.lpalma.photomap.models.Photo;

public class PhotoLocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, PhotoLocationDialog.Callback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private LatLng location;
    private PhotoLocationDialog photoLocationDialog;
    private String filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_location);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        filePath = getIntent().getExtras().getString("file");
        photoLocationDialog = new PhotoLocationDialog();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accept:
                if (location != null) {
                    photoLocationDialog.show(getFragmentManager(), "Loc");
                    photoLocationDialog.setFile(filePath, location);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
    }

    private void setLocation() {
        if (mMap != null) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //mMap.setMyLocationEnabled(true);
            location = new LatLng(0, 0);
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        location = latLng;
        mMap.addMarker(new MarkerOptions().position(latLng).icon(
                BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
/*        Toast.makeText(getApplicationContext(), latLng.toString(),
                Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void SetLocation(String file, LatLng points) {
        Photo photo;
        try {
            photo = Photo.create(file, points, Photo.FROM_MANUAL);
            new PhotoData().insert(getApplicationContext(), photo);
        } catch (Exception e) {
            Log.e("NOT_LOCATION", e.getMessage());
            photo = new Photo();
            photo.setPath(filePath);
        }
        finish();
    }
}
