package ng.riby.androidtest.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {KeepData.class}, version = 1, exportSchema = false)
public abstract class DataRoomDatabase extends RoomDatabase {

    public abstract DataDuo dataDuo();

    private static volatile DataRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static DataRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DataRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DataRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
