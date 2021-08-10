package com.human_developing_soft.accurate_translation.translation.data;

import java.util.ArrayList;
import java.util.List;

public class LanguagesSchema {
    private final String mTableName = "languages";
    private String mColumnIdentifiable = "isIdentifiable";
    private String mColumnAsTarget = "isSupAsTarget";
    private String mColumnCode = "countryCode";
    private String mColumnDirection = "direction";
    private String mColumnLanguage = "language";
    private String mColumnAsSource = "isSupAsSource";
    private String mColumnNativeName = "nativeName";
    private String mColumnName = "name";

    String tableName() {
        return mTableName;
    }

    String[] attributes() {
        return new String[]{
                mColumnLanguage,
                mColumnName,
                mColumnNativeName,
                mColumnCode,
                mColumnDirection,
                mColumnAsSource,
                mColumnAsTarget,
                mColumnIdentifiable
        };
    }

    String schema() {

        return String.format("%s (%s TEXT," +
                        " %s TEXT," +
                        " %s TEXT," +
                        " %s BOOLEAN," +
                        " %s TEXT," +
                        " %s BOOLEAN," +
                        " %s BOOLEAN," +
                        " %s BOOLEAN)",
                mTableName,
                mColumnLanguage,
                mColumnName,
                mColumnNativeName,
                mColumnCode,
                mColumnDirection,
                mColumnAsSource,
                mColumnAsTarget,
                mColumnIdentifiable
        );
    }
}
