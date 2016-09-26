package mx.lpalma.photomap.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;

import mx.lpalma.photomap.R;
import mx.lpalma.photomap.db.PhotoData;
import mx.lpalma.photomap.dialog.LocationDialog;
import mx.lpalma.photomap.helper.PhotoMarkerManager;
import mx.lpalma.photomap.models.Photo;
import mx.lpalma.photomap.ui.adapters.PhotoMapInfoAdapter;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationDialog.Callback {

    public static final int TAKE_PICTURE = 100;
    public static final int SELECT_PICTURE = 200;

    private Toolbar toolbar;
    private GoogleMap mMap;
    private FloatingActionButton btnAccion;
    private BottomSheetBehavior bottomSheetBehavior;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main);
        final View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        btnAccion = (FloatingActionButton) findViewById(R.id.fab);
        btnAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                getActionIntent(TAKE_PICTURE);
            }
        });
        //Snackbar snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_SHORT);
        //snackbar.show();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        if (mMap != null)
            updateMap();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        Photo file = null;
        String picturePath = null;
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Log.i("REQUEST", requestCode + "");
            String[] filePathColumn = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            String imageOrderBy = MediaStore.Images.Media._ID + " DESC";
            picturePath = getRealFilePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, filePathColumn, imageOrderBy);
            Log.i("DATA_PATH", picturePath + "");
            try {
                if (Photo.hasInfo(picturePath)) {
                    file = Photo.create(picturePath, Photo.FROM_CAMERA);
                    new PhotoData().insert(getApplicationContext(), file);
                    updateMap();
                } else {
                    showDialog(picturePath);
                    file = new Photo();
                    file.setPath(picturePath);
                }
            } catch (IOException e) {
                Log.e("ERROR_RESULT", e.getMessage());
            }
        } else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            Log.i("REQUEST", requestCode + "");
            Uri selectedImage = data.getData();
            Log.i("DATA_PATH", selectedImage.getPath() + "");
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            picturePath = getRealFilePath(selectedImage, filePathColumn, null);
            try {
                if (Photo.hasInfo(picturePath)) {
                    file = Photo.create(picturePath, Photo.FROM_GALLERY);
                    new PhotoData().insert(getApplicationContext(), file);
                    updateMap();
                } else {
                    showDialog(picturePath);
                }
            } catch (IOException e) {
                Log.e("ERROR_RESULT", e.getMessage());
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new PhotoMapInfoAdapter(
                getLayoutInflater()));
        updateMap();
    }

    public void updateMap() {
        mMap.clear();
        PhotoMarkerManager.addPhotos(new PhotoData().getAll(getApplicationContext()));
        PhotoMarkerManager.showMarkers(mMap);
    }

    private void getActionIntent(int requestCode) {
        Intent intent = null;
        if (requestCode == TAKE_PICTURE) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, TAKE_PICTURE);
            }
        } else if (requestCode == SELECT_PICTURE) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, SELECT_PICTURE);
            }
        }
    }

    private String getRealFilePath(Uri file, String[] projection, String order) {
        Cursor cursor = getContentResolver().query(file, projection, null,
                null, order);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    private void showDialog(String file) {
        LocationDialog dialog = new LocationDialog();
        dialog.setFile(file);
        dialog.show(this.getFragmentManager(), "Dialog");
    }

    @Override
    public void SetLocation(String file) {
        Intent intent = new Intent(getApplicationContext(),
                PhotoLocationActivity.class);
        intent.putExtra("file", file);
        startActivity(intent);
    }
}
