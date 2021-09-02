package com.human_developing_soft.accurate_translation.translation.domain;

import java.util.List;

public interface TagLoadingObserver {
    void onTagLoaded(List<String> tags);
}
