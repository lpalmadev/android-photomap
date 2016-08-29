package mx.lpalma.photomap.models;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

/**
 * Created by lpalma on 28/08/2016.
 */
public class PhotoMarker {

    public MarkerOptions marker;
    public Photo photo;

    public PhotoMarker(Photo photo) {
        this.photo = photo;
        marker = new MarkerOptions();
        marker.title(photo.getName());
        marker.snippet(photo.getDate());
        marker.describeContents();
        marker.draggable(photo.getType() == Photo.FROM_GALLERY_MANUAL ? true : false);
        marker.position(photo.getLatLng());
        if (new File(photo.getPath()).exists()) {
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        } else {
            marker.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        }
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void setMarker(MarkerOptions marker) {
        this.marker = marker;
    }

    public Photo getPhoto() {
        return photo;
    }
}
