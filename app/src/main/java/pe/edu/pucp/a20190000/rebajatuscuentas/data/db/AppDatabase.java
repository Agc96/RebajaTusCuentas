package pe.edu.pucp.a20190000.rebajatuscuentas.data.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.dao.InmovableDao;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.dao.UserDao;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.Inmovable;
import pe.edu.pucp.a20190000.rebajatuscuentas.data.db.entities.User;

@Database(entities = {User.class, Inmovable.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "rebajatuscuentas.db";
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    DB_NAME).fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
    public abstract InmovableDao inmovableDao();
}
