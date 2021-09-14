package com.human_developing_soft.accurate_translation.translation.data.api.google;

import com.human_developing_soft.accurate_translation.translation.data.api.ibm.Translating;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoogleTranslating implements Translating {
    private final String mTranslatingText;
    private final String mFirstCode;
    private final String mSecondCode;

    public GoogleTranslating(String pTranslatingText,
                String pFirstCode,
                String pSecondCode) {
        mTranslatingText = pTranslatingText;
        mFirstCode = pFirstCode;
        mSecondCode = pSecondCode;
    }

    @Override
    public String translate() throws JSONException {
        String requestText = "https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                + mFirstCode + "&tl=" + mSecondCode + "&dt=t&ie=UTF-8&ae=UTF-8&q="
                + mTranslatingText;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestText)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return "Not working request!";
        }
    }
}
