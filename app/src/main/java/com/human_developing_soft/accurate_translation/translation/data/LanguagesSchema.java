package com.human_developing_soft.accurate_translation.translation.data;

import com.human_developing_soft.accurate_translation.CommonSchema;

public class LanguagesSchema implements CommonSchema {
    private final String mTableName = "languages";
    private final String mColumnIdentifiable = "isIdentifiable";
    private final String mColumnAsTarget = "isSupAsTarget";
    private final String mColumnCode = "countryCode";
    private final String mColumnDirection = "direction";
    private final String mColumnLanguage = "language";
    private final String mColumnAsSource = "isSupAsSource";
    private final String mColumnNativeName = "nativeName";
    private final String mColumnName = "name";

    @Override
    public String tableName() {
        return mTableName;
    }

    @Override
    public String[] attributes() {
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

    @Override
    public String schema() {
        return String.format("%s (%s TEXT," +
                        " %s TEXT," +
                        " %s TEXT," +
                        " %s TEXT," +
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
