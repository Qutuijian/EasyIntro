/*
 * Copyright 2016 Alireza Eskandarpour Shoferi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.meness.easyintro;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import io.github.meness.easyintro.enums.SwipeDirection;
import io.github.meness.easyintro.interfaces.ICheck;
import io.github.meness.easyintro.interfaces.ISlide;
import io.github.meness.easyintro.interfaces.ITouch;
import io.github.meness.easyintro.listeners.OnBackPressListener;
import io.github.meness.easyintro.utils.BackPressImpl;

public class EasyIntroFragment extends Fragment implements ISlide, ICheck, ITouch, OnBackPressListener {

    @Override
    public void withNextSlide(boolean smoothScroll) {
        getBaseContext().withNextSlide(smoothScroll);
    }

    protected final CarouselFragment getBaseContext() {
        return ((EasyIntro) getContext()).getCarouselFragment();
    }

    @Override
    public void withPreviousSlide(boolean smoothScroll) {
        getBaseContext().withPreviousSlide(smoothScroll);
    }

    @Override
    public void withSlideTo(int page, boolean smoothScroll) {
        getBaseContext().withSlideTo(page, smoothScroll);
    }

    @Override
    public Fragment getCurrentSlide() {
        return getBaseContext().getCurrentSlide();
    }

    @Override
    public void withSlide(Fragment slide) {
        getBaseContext().withSlide(slide);
    }

    @Override
    public void withOverlaySlide(Fragment slide, @IdRes int container) {
        getBaseContext().withOverlaySlide(slide, container);
    }

    @Override
    public void withSlideTo(Class slideClass, boolean smoothScroll) {
        getBaseContext().withSlideTo(slideClass, smoothScroll);
    }

    @Override
    public boolean isLocked() {
        return getBaseContext().isLocked();
    }

    @Override
    public SwipeDirection getSwipeDirection() {
        return getBaseContext().getSwipeDirection();
    }

    @Override
    public int getPageMargin() {
        return getBaseContext().getPageMargin();
    }

    @Override
    public boolean isRightIndicatorVisible() {
        return getBaseContext().isRightIndicatorVisible();
    }

    @Override
    public boolean isRightIndicatorDisabled() {
        return getBaseContext().isRightIndicatorDisabled();
    }

    @Override
    public boolean isLeftIndicatorVisible() {
        return getBaseContext().isLeftIndicatorVisible();
    }

    @Override
    public boolean isLeftIndicatorDisabled() {
        return getBaseContext().isLeftIndicatorDisabled();
    }

    @Override
    public boolean onBackPressed() {
        return new BackPressImpl(this).onBackPressed();
    }

    @Override
    public void onPreviousTouch() {
        // empty
    }

    @Override
    public void onNextTouch() {
        // empty
    }

    @Override
    public void onDoneTouch() {
        // empty
    }

    @Override
    public void onSkipTouch() {
        // empty
    }
}
