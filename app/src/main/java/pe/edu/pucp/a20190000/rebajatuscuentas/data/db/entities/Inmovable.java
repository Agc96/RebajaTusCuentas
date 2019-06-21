package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "RTC_INMOVABLE")
public class Inmovable {

    @PrimaryKey
    @ColumnInfo(name = "INMOVABLE_ID")
    private Integer inmovableId;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "MAX_PRICE")
    private Double price;

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

    public Inmovable(Integer inmovableId, String name, Double price, String department, String province,
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

    public Integer getInmovableId() {
        return inmovableId;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
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
}
