package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

public class InmovableMainData {
    @PrimaryKey
    @ColumnInfo(name = "INMOVABLE_ID")
    private Integer id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "MAX_PRICE")
    private Double price;

    @ColumnInfo(name = "LOCATION")
    private String location;

    public InmovableMainData(Integer id, String name, Double price, String location) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
    public String getLocation() {
        return location;
    }
}
