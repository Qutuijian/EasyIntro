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
import android.support.annotation.LayoutRes;
import android.support.annotation.RawRes;

import io.github.meness.easyintro.EasyIntro;
import io.github.meness.easyintro.enums.PageIndicator;
import io.github.meness.easyintro.enums.SlideTransformer;
import io.github.meness.easyintro.enums.ToggleIndicator;

/**
 * following methods may be configured once through {@link EasyIntro#initIntro()}
 */
public interface IConfigOnce {
    void withTranslucentStatusBar(boolean b);

    void withStatusBarColor(@ColorInt int statusBarColor);

    void withOffScreenPageLimit(int limit);

    void withTransparentStatusBar(boolean b);

    void withTransparentNavigationBar(boolean b);

    void withFullscreen(boolean b);

    void withTranslucentNavigationBar(boolean b);

    void withSlideTransformer(SlideTransformer transformer);

    void withToggleIndicator(ToggleIndicator indicators);

    void withVibrateOnSlide(int intensity);

    void withVibrateOnSlide();

    void withRtlSupport();

    void withPageMargin(int marginPixels);

    void setPageMarginDrawable(Drawable d);

    void setPageMarginDrawable(@DrawableRes int resId);

    void withToggleIndicatorsSound(boolean b);

    void withSlideSound(@RawRes int sound);

    void withOverScrollMode(int mode);

    void withPageIndicator(PageIndicator pageIndicator);

    // disable indicator globally
    void withRightIndicatorDisabled(boolean b);

    // disable indicator globally
    void withLeftIndicatorDisabled(boolean b);

    void withPageIndicator(@LayoutRes int indicator);
}
