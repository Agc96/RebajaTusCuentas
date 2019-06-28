package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "RTC_INMOVABLE")
public class Inmovable implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "INMOVABLE_ID")
    private int inmovableId;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "MAX_PRICE")
    private double price;

    @ColumnInfo(name = "DEPARTMENT")
    private String department;

    @ColumnInfo(name = "PROVINCE")
    private String province;

    @ColumnInfo(name = "DISTRICT")
    private String district;

    @ColumnInfo(name = "LOCATION")
    private String location;

    @ColumnInfo(name = "REFERENCE")
    private String reference;

    @ColumnInfo(name = "LATITUDE")
    private Double latitude;

    @ColumnInfo(name = "LONGITUDE")
    private Double longitude;

    public Inmovable() {
        // Java inicializa todos los valores en 0 o NULL, dependiendo del tipo de dato.
    }

    public Inmovable(int inmovableId, String name, Double price, String department, String province,
                     String district, String location, String reference, Double latitude, Double longitude) {
        this.inmovableId = inmovableId;
        this.name = name;
        this.price = price;
        this.department = department;
        this.province = province;
        this.district = district;
        this.location = location;
        this.reference = reference;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getInmovableId() {
        return inmovableId;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public String getDepartment() {
        return department;
    }
    public String getProvince() {
        return province;
    }
    public String getDistrict() {
        return district;
    }
    public String getLocation() {
        return location;
    }
    public String getReference() {
        return reference;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

    public void setInmovableId(Integer inmovableId) {
        this.inmovableId = inmovableId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public void setDistrict(String district) {
        this.district = district;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Ignore
    private Inmovable(Parcel parcel) {
        // Leer datos obligatorios del inmueble en el Parcel
        inmovableId = parcel.readInt();
        name = parcel.readString();
        price = parcel.readDouble();
        department = parcel.readString();
        district = parcel.readString();
        location = parcel.readString();
        reference = parcel.readString();
        // Escribir latitud y longitud, para ello tenemos que ver primero si son nulos o no
        latitude = readNullableDoubleFromParcel(parcel);
        longitude = readNullableDoubleFromParcel(parcel);
    }

    private Double readNullableDoubleFromParcel(Parcel parcel) {
        int isNull = parcel.readInt();
        return (isNull == 1) ? parcel.readDouble() : null;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // Escribir datos obligatorios del inmueble en el Parcel
        parcel.writeInt(inmovableId);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(department);
        parcel.writeString(province);
        parcel.writeString(district);
        parcel.writeString(location);
        parcel.writeString(reference);
        // Escribir latitud y longitud, para ello tenemos que indicar primero si son nulos o no
        writeNullableDoubleToParcel(parcel, latitude);
        writeNullableDoubleToParcel(parcel, longitude);
    }

    private void writeNullableDoubleToParcel(Parcel parcel, Double nullableDouble) {
        if (nullableDouble != null) {
            parcel.writeInt(1);
            parcel.writeDouble(nullableDouble);
        } else {
            parcel.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Inmovable> CREATOR = new Parcelable.Creator<Inmovable>() {
        @Override
        public Inmovable createFromParcel(Parcel parcel) {
            return new Inmovable(parcel);
        }

        @Override
        public Inmovable[] newArray(int size) {
            return new Inmovable[size];
        }
    };
}
