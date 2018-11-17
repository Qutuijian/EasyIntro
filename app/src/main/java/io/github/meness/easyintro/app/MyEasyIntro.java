/*
 * Copyright 2018 Alireza Eskandarpour
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
        withSlide(HelloFragment.newInstance());
        withSlide(OverlaySlides.newInstance());
        withSlide(EndFragment.newInstance());

        withSlideTransformer(SlideTransformer.ZOOM_OUT_SLIDE);
        // define overlay slides animations once
        withOverlaySlideAnimation(android.R.anim.slide_in_left, android.R.anim.slide_out_right, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        withFullscreen(true);
    }
}
