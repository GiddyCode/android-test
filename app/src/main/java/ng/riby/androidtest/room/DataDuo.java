package ng.riby.androidtest.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDuo {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(KeepData data);

    @Query("DELETE FROM data_table")
    void deleteAll();

    @Query("SELECT * from data_table ORDER BY data ASC")
    LiveData<List<KeepData>> getLocationData();
}
