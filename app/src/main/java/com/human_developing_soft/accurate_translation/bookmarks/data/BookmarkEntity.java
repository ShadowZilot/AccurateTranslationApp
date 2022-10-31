package com.human_developing_soft.accurate_translation.bookmarks.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

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
            String mTag,
            @NonNull Long pDate) {
        this.mId = mId;
        this.mFirstTranslation = mFirstTranslation;
        this.mSecondTranslation = mSecondTranslation;
        this.mFirstLanguage = mFirstLanguage;
        this.mSecondLanguage = mSecondLanguage;
        this.mTag = mTag;
        this.mDate = pDate;
    }

    @PrimaryKey(autoGenerate = true)
    Integer mId;
    @ColumnInfo(name = "firstTranslation")
    private final String mFirstTranslation;
    @ColumnInfo(name = "secondTranslation")
    private final String mSecondTranslation;
    @ColumnInfo(name = "firstLanguage")
    private final String mFirstLanguage;
    @ColumnInfo(name = "secondLanguage")
    private final String mSecondLanguage;
    @ColumnInfo(name = "tag")
    private final String mTag;
    @ColumnInfo(name = "date")
    @NotNull
    private final Long mDate;

    public Bookmark toBookmark() {
        return new Bookmark.Base(
                mId,
                mFirstTranslation,
                mSecondTranslation,
                mFirstLanguage,
                mSecondLanguage,
                mTag,
                mDate
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

    public Long getDate() { return mDate; }
}
