package ng.riby.androidtest.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")

public class KeepData {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "data")
    private String mData;

    public KeepData (@NonNull String data) {this.mData = data;}

    public String getData(){return this.mData;}
}
