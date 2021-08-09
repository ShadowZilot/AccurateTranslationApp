package com.human_developing_soft.accurate_translation.translation.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HandledTranslating implements Translating {
    private final Translating mDecoratingObject;

    public HandledTranslating(Translating pDecorating) {
        mDecoratingObject = pDecorating;
    }

    @Override
    public String translate() throws JSONException {
        JSONObject object = new JSONObject(mDecoratingObject.translate());
        JSONArray translationsArray = object.getJSONArray("translations");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < translationsArray.length(); i++) {
            builder = builder.append(
                    translationsArray
                            .getJSONObject(i)
                            .getString("translation")
            );
            builder = builder.append(" ");
        }
        builder = builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
