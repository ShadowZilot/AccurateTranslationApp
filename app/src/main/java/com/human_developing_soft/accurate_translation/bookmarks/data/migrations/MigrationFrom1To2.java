package com.human_developing_soft.accurate_translation.bookmarks.data.migrations;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
public class MigrationFrom1To2 extends Migration {
    public MigrationFrom1To2(int startVersion, int endVersion) {
        super(startVersion, endVersion);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        long currentTime = System.currentTimeMillis();
        database.execSQL(
                "ALTER TABLE bookmarks ADD COLUMN date INTEGER NOT NULL DEFAULT 0;"
                        .replace("0", Long.toString(currentTime))
        );
    }
}
