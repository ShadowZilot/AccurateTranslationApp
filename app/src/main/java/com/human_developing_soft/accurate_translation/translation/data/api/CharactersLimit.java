package com.human_developing_soft.accurate_translation.translation.data.api;

public interface CharactersLimit {
    Integer limit();

    class Constant implements CharactersLimit {
        private final Integer mConstantLimit = 90;

        @Override
        public Integer limit() {
            return mConstantLimit;
        }
    }
}
