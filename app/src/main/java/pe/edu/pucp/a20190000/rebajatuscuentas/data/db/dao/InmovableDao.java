package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.InmovableMainData;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;

@Dao
public interface InmovableDao {
    @Query("SELECT INMOVABLE_ID, NAME, MAX_PRICE, DIRECTION FROM RTC_INMOVABLE")
    List<InmovableMainData> listAllMainData();

    @Insert
    long insert(Inmovable inmovable);

    @Delete
    void delete(Inmovable inmovable);
}
