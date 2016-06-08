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

package io.github.meness.easyintro.app;

import io.github.meness.easyintro.EasyIntro;
import io.github.meness.easyintro.app.fragments.EndFragment;
import io.github.meness.easyintro.app.fragments.HelloFragment;
import io.github.meness.easyintro.app.fragments.OverlaySlides;
import io.github.meness.easyintro.enums.SlideTransformer;

public class MyEasyIntro extends EasyIntro {
    @Override
    protected void initIntro() {
        withSlide(HelloFragment.instantiate(getApplicationContext(), HelloFragment.class.getName()));
        withSlide(OverlaySlides.instantiate(getApplicationContext(), OverlaySlides.class.getName()));
        withSlide(EndFragment.instantiate(getApplicationContext(), EndFragment.class.getName()));

        withSlideTransformer(SlideTransformer.ZOOM_OUT_SLIDE);
        withFullscreen(true);
    }
}
