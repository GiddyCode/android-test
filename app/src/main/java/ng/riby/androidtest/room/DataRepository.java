package ng.riby.androidtest.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {

    private DataDuo mDataDao;
    private LiveData<List<KeepData>> mAllLocationData;

    DataRepository(Application application) {
        DataRoomDatabase db = DataRoomDatabase.getDatabase(application);
        mDataDao = db.dataDuo();
        mAllLocationData = mDataDao.getLocationData();
    }


    LiveData<List<KeepData>> mAllLocationData() {
        return mAllLocationData();
    }


    void insert(KeepData data) {
        DataRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDataDao.insert(data);
        });
    }
}
