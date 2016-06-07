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

package io.github.meness.easyintro.interfaces;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;

import io.github.meness.easyintro.enums.PageIndicator;
import io.github.meness.easyintro.enums.SlideTransformer;
import io.github.meness.easyintro.enums.ToggleIndicators;

public interface IConfig {
    void withTranslucentStatusBar(boolean b);

    void withPageIndicatorVisibility(boolean b);

    void withStatusBarColor(@ColorInt int statusBarColor);

    void withOffScreenPageLimit(int limit);

    void withTransparentStatusBar(boolean b);

    void withTransparentNavigationBar(boolean b);

    void withFullscreen(boolean b);

    void withTranslucentNavigationBar(boolean b);

    void withSlideTransformer(SlideTransformer transformer);

    void withRightIndicatorDisabled(boolean b);

    void withLeftIndicatorDisabled(boolean b);

    void withToggleIndicators(ToggleIndicators indicators);

    void withVibrateOnSlide(int intensity);

    void withVibrateOnSlide();

    void withRtlSwipe();

    void withPageMargin(int marginPixels);

    void setPageMarginDrawable(Drawable d);

    void setPageMarginDrawable(@DrawableRes int resId);

    void withToggleIndicatorsSound(boolean b);

    void withSlideSound(@RawRes int sound);

    void withOverScrollMode(int mode);

    void withPageIndicator(PageIndicator pageIndicator);

    void withLock(boolean b, Fragment lock);
}
