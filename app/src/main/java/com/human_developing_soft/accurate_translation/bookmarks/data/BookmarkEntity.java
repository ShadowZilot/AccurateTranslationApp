package com.human_developing_soft.accurate_translation.bookmarks.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "bookmarks"
)
public class BookmarkEntity {

    public BookmarkEntity(
            Integer mId,
            String mFirstTranslation,
            String mSecondTranslation,
            String mFirstLanguage,
            String mSecondLanguage,
            String mTag) {
        this.mId = mId;
        this.mFirstTranslation = mFirstTranslation;
        this.mSecondTranslation = mSecondTranslation;
        this.mFirstLanguage = mFirstLanguage;
        this.mSecondLanguage = mSecondLanguage;
        this.mTag = mTag;
    }

    @PrimaryKey(autoGenerate = true)
    Integer mId;
    @ColumnInfo(name = "firstTranslation")
    private String mFirstTranslation;
    @ColumnInfo(name = "secondTranslation")
    private String mSecondTranslation;
    @ColumnInfo(name = "firstLanguage")
    private String mFirstLanguage;
    @ColumnInfo(name = "secondLanguage")
    private String mSecondLanguage;
    @ColumnInfo(name = "tag")
    private String mTag;

    public Bookmark toBookmark() {
        return new Bookmark.Base(
                mId,
                mFirstTranslation,
                mSecondTranslation,
                mFirstLanguage,
                mSecondLanguage,
                mTag
        );
    }

    public Integer getId() {
        return mId;
    }

    public String getFirstTranslation() {
        return mFirstTranslation;
    }

    public String getSecondTranslation() {
        return mSecondTranslation;
    }

    public String getFirstLanguage() {
        return mFirstLanguage;
    }

    public String getSecondLanguage() {
        return mSecondLanguage;
    }

    public String getTag() {
        return mTag;
    }
}
