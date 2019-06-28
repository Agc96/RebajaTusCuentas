package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class InmovableMainData {
    @PrimaryKey
    @ColumnInfo(name = "INMOVABLE_ID")
    private Integer id;

    @ColumnInfo(name = "NAME")
    private String name;

    @ColumnInfo(name = "MAX_PRICE")
    private Double price;

    @ColumnInfo(name = "DIRECTION")
    private String direction;

    public InmovableMainData(Integer id, String name, Double price, String direction) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.direction = direction;
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
    public String getDirection() {
        return direction;
    }
}
