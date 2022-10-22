package com.human_developing_soft.accurate_translation.bookmarks.domain;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.human_developing_soft.accurate_translation.bookmarks.data.Bookmark;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkDBWrapped;
import com.human_developing_soft.accurate_translation.bookmarks.data.BookmarkStorage;
import com.human_developing_soft.accurate_translation.translation.common.OnTranslationFieldChanged;
import com.human_developing_soft.accurate_translation.translation.data.LanguageStorage;
import com.human_developing_soft.accurate_translation.translation.data.LanguagesDBWrapped;
import com.human_developing_soft.accurate_translation.translation.domain.DomainTranslator;
import com.human_developing_soft.accurate_translation.translation.domain.SelectedLanguages;
import com.human_developing_soft.accurate_translation.translation.ui.SavingBookmarkObserver;
import com.human_developing_soft.accurate_translation.translation.ui.StringProvider;
import com.human_developing_soft.accurate_translation.translation.ui.TranslatingObserver;

import java.util.Locale;

public class BookmarkEditingVM extends ViewModel implements OnTranslationFieldChanged {
    private DomainTranslator mTranslator;
    private BookmarkStorage mBookmarkStorage;

    public BookmarkEditingVM(TranslatingObserver pObserver,
                             Context pContext,
                             String firstLanguage,
                             String secondLanguage) {
        new Thread(() -> {
            LanguageStorage languageStorage = LanguagesDBWrapped.instance(pContext);
            SelectedLanguages selectedLanguages = new SelectedLanguages.Base(
                    languageStorage.languageByCountry(firstLanguage),
                    languageStorage.languageByCountry(secondLanguage)
            );
            mTranslator = new DomainTranslator(pObserver,
                    selectedLanguages);
            mBookmarkStorage = BookmarkDBWrapped.instance(pContext);
        }).start();
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
                               SavingBookmarkObserver observer) {
        Runnable runnable = () -> observer.onBookmarkSaved(
                mBookmarkStorage.updateBookmark(
                        updatingBookmark
                )
        );
        new Thread(runnable).start();
    }

    public void deleteBookmark(Bookmark deletingBookmark,
                               OnDeleteBookmarkListener observer) {
        Runnable runnable = () -> {
            mBookmarkStorage.deleteBookmark(deletingBookmark);
            observer.onBookmarkDeleted();
        };
        new Thread(runnable).start();
    }

    public void updateObserver(TranslatingObserver newObserver) {
        mTranslator.updateObserver(newObserver);
    }

    public Locale localeByLanguage(Boolean isFirst) {
        return mTranslator.localeByLanguage(isFirst);
    }
}
