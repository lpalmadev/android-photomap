package mx.lpalma.photomap.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by lpalma on 27/08/2016.
 */
public class Photo {

    public static final int FROM_CAMERA = 1;
    public static final int FROM_GALLERY = 2;
    public static final int FROM_GALLERY_MANUAL = 3;

    int id;
    String path;
    double longitude;
    double latitude;
    String name;
    String date;
    int type;

    public Photo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public LatLng getLatLng(){
        return new LatLng(latitude, longitude);
    }

    public void setLatLng(LatLng point){
        this.latitude = point.latitude;
        this.longitude = point.longitude;
    }

    /*
	public static boolean hasInfo(String file) throws IOException {
		ExifInterface fileInfo = new ExifInterface(file);
		float[] lanLong = new float[2];
		if (fileInfo.getLatLong(lanLong)) {
			return true;
		}
		return false;
	}

	public static float[] getLanLong(String file) throws IOException {
		ExifInterface fileInfo = new ExifInterface(file);
		float[] lanLong = new float[2];
		fileInfo.getLatLong(lanLong);
		return lanLong;
	}

	public static void setInfo(String file, float[] lanLog) throws IOException {
		ExifInterface fileInfo = new ExifInterface(file);
		fileInfo.setAttribute(ExifInterface.TAG_GPS_LATITUDE, "" + lanLog[0]);
		fileInfo.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, "" + lanLog[1]);
		fileInfo.saveAttributes();
	}

	public static ImageFile create(String file, int type) throws IOException{
		ImageFile newfile = new ImageFile();
		ExifInterface fileInfo = new ExifInterface(file);
		File i = new File(file);
		newfile.setName(i.getName());
		newfile.setDate(fileInfo.getAttribute(ExifInterface.TAG_DATETIME));
		float[] a = getLanLong(file);
		newfile.setLatitude(a[0]);
		newfile.setLongitude(a[1]);
		newfile.setPath(file);
		newfile.setType(type);
		return newfile;
	}

	public static ImageFile create(String file,LatLng point, int type) throws IOException{
		ImageFile newfile = new ImageFile();
		ExifInterface fileInfo = new ExifInterface(file);
		File i = new File(file);
		newfile.setName(i.getName());
		newfile.setDate(fileInfo.getAttribute(ExifInterface.TAG_DATETIME));
		newfile.setLatitude(point.latitude);
		newfile.setLongitude(point.longitude);
		newfile.setPath(file);
		newfile.setType(type);
		return newfile;
	}
     */
}
