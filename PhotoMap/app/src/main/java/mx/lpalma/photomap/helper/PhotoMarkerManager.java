package mx.lpalma.photomap.helper;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.lpalma.photomap.models.Photo;
import mx.lpalma.photomap.models.PhotoMarker;

/**
 * Created by lpalma on 28/08/2016.
 */
public class PhotoMarkerManager {

    public static List<PhotoMarker> markerList = new ArrayList<PhotoMarker>();
    public static HashMap<Marker, Photo> markers;
    public static List<Marker> listMarker;
    public static List<Photo> fail;

    public static void addPhotos(List<Photo> photos) {
        markerList.clear();
        fail = new ArrayList<Photo>();
        for (Photo photo : photos) {
            if (!existsFile(photo.getPath())) {
                fail.add(photo);
            }
            addPhotoMarker(new PhotoMarker(photo));
        }
    }

    public static void addPhotoMarker(PhotoMarker marker) {
        markerList.add(marker);
    }

    public static void showMarkers(GoogleMap map) {
        markers = new HashMap<Marker, Photo>();
        listMarker = new ArrayList<Marker>();
        Marker mMarker = null;
        for (PhotoMarker photoMarker : markerList) {
            mMarker = map.addMarker(photoMarker.getMarker());
            listMarker.add(mMarker);
            markers.put(mMarker, photoMarker.getPhoto());
        }
    }

    private static boolean existsFile(String path) {
        File f = new File(path);
        return f.exists();
    }

    /*
    public static void showFailFile(FragmentManager manager) {
        if (fail.size() == 1) {
            FileDialog dialog = new FileDialog();
            dialog.type = FileDialog.ERROR_FILE;
            dialog.setMarker(fail.get(0));
            dialog.show(manager, "FILE");
        } else if (fail.size() > 1) {
            FileDialog dialog = new FileDialog();
            dialog.type = FileDialog.ERROR_FILES;
            dialog.setListMarker(fail);
            dialog.show(manager, "FILES");
        }
    }
    */

    public  static List<Marker> findDistance(Marker marker) {
        LatLng a = marker.getPosition();
        List<Marker> range = new ArrayList<Marker>();
        for (Marker mMarker : listMarker) {
            if(getdistance(a, mMarker.getPosition()) < 100){
                Log.i("Distancia", "" + getdistance(a, mMarker.getPosition()));
                range.add(mMarker);
            }
        }
        return range;
    }

    public static float getdistance(LatLng a, LatLng b) {
        double eathRadius = 3958.75;
        double latdif = Math.toRadians(b.latitude - a.latitude);
        double lngdif = Math.toRadians(b.longitude - a.longitude);
        double x = Math.sin(latdif / 2) * Math.sin(latdif / 2)
                + Math.cos(Math.toRadians(a.latitude))
                * Math.cos(Math.toRadians(b.latitude)) * Math.sin(lngdif / 2)
                * Math.sin(lngdif / 2);
        double c = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
        double distance = eathRadius * c;
        int meterConversion = 1609;
        return new Float(distance * meterConversion).floatValue();
    }

}
