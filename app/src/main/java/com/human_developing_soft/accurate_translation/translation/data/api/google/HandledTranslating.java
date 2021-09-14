package com.human_developing_soft.accurate_translation.translation.data.api.google;

import com.human_developing_soft.accurate_translation.translation.data.api.ibm.Translating;

import org.json.JSONArray;
import org.json.JSONException;

public class HandledTranslating implements Translating {
    private final Translating mDecoratingObject;

    public HandledTranslating(Translating decorating) {
        mDecoratingObject = decorating;
    }

    @Override
    public String translate() throws JSONException {
        String ans = "";
        JSONArray jsonArray = new JSONArray(
                mDecoratingObject.translate());
        for (int i = 0; i < 1; i++) {

            JSONArray jsonArray1 = jsonArray.getJSONArray(0);
            for (int j = 0; j < jsonArray1.length(); j++) {

                ans = jsonArray1.getJSONArray(j).get(0).toString();
            }
        }
        return ans;
    }
}
