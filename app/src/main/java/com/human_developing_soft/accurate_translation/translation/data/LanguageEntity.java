package com.human_developing_soft.accurate_translation.translation.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Languages")
public class LanguageEntity {
    @PrimaryKey(autoGenerate = true)
    private Integer mID;
    @ColumnInfo(defaultValue = "name")
    private String mName;
    @ColumnInfo(defaultValue = "languageCode")
    private String mLanguageCode;
    @ColumnInfo(defaultValue = "countryCode")
    private String mCountryCode;

    public LanguageEntity(
            String name,
            String languageCode,
            String countryCode
    ) {
        mName = name;
        mLanguageCode = languageCode;
        mCountryCode = countryCode;
    }

    public void setID(Integer id) {
        mID = id;
    }

    public Integer getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getLanguageCode() {
        return mLanguageCode;
    }

    public String getCountryCode() {
        return mCountryCode;
    }
}
