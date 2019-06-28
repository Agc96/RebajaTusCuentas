package pe.edu.pucp.a20190000.rebajatuscuentas.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM RTC_USER WHERE USERNAME = :username LIMIT 1")
    User findByUsername(String username);

    @Query("SELECT * FROM RTC_USER WHERE USER_ID = :userId LIMIT 1")
    User findById(int userId);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

}

