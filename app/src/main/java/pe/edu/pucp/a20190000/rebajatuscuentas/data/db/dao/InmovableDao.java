package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.InmovableMainData;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;

@Dao
public interface InmovableDao {
    @Query("SELECT INMOVABLE_ID, NAME, MAX_PRICE FROM RTC_INMOVABLE")
    List<InmovableMainData> listAllMainData();

    @Insert
    void insert(Inmovable inmovable);

    @Delete
    void delete(Inmovable inmovable);
}
