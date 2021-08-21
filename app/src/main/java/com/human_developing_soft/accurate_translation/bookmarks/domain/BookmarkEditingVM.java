package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.OnTranslationFieldChanged;
import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDBWrapped;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDatabase;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkStorage;
import com.human_developing_soft.accurate_translation.translation.data.LanguageStorage;
import com.human_developing_soft.accurate_translation.translation.data.LanguagesDatabase;
import com.human_developing_soft.accurate_translation.translation.domain.DomainTranslator;
import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;
import com.human_developing_soft.accurate_translation.translation.ui.SavingBookmarkObserver;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

public class BookmarkEditingVM extends ViewModel implements OnTranslationFieldChanged {
    private final DomainTranslator mTranslator;
    private final BookmarkStorage mBookmarkStorage;

    public BookmarkEditingVM(TranslatingObserver pObserver,
                             Context pContext,
                             String firstLanguage,
                             String secondLanguage) {
        LanguageStorage languageStorage = LanguagesDatabase.instance(pContext);
        SelectedLanguages selectedLanguages = new SelectedLanguages.Base(
                languageStorage.languageByName(firstLanguage),
                languageStorage.languageByName(secondLanguage)
        );
        mTranslator = new DomainTranslator(pObserver,
                selectedLanguages);
        mBookmarkStorage = BookmarkDBWrapped.instance(pContext);
    }

    @Override
    public void translateText(String translationField,
                              Boolean isFirstField,
                              StringProvider provider) {
        mTranslator.translateText(translationField,
                isFirstField,
                provider);
    }

    public void updateBookmark(Bookmark updatingBookmark,
                               SavingBookmarkObserver pObserver) {
        Runnable runnable = () -> {
            mBookmarkStorage.updateBookmark(updatingBookmark);
            pObserver.onBookmarkSaved(true);
        };
        new Thread(runnable).start();
    }
}
