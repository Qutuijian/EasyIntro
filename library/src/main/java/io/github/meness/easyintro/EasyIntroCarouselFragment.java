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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.mikepenz.materialize.MaterializeBuilder;

import java.lang.reflect.InvocationTargetException;

import io.github.meness.easyintro.enums.PageIndicator;
import io.github.meness.easyintro.enums.SlideTransformer;
import io.github.meness.easyintro.enums.SwipeDirection;
import io.github.meness.easyintro.enums.ToggleIndicator;
import io.github.meness.easyintro.interfaces.ICheck;
import io.github.meness.easyintro.interfaces.IConfig;
import io.github.meness.easyintro.interfaces.IConfigOnce;
import io.github.meness.easyintro.interfaces.ISlide;
import io.github.meness.easyintro.listeners.EasyIntroInteractionsListener;
import io.github.meness.easyintro.listeners.OnBackPressListener;
import io.github.meness.easyintro.listeners.OnToggleIndicatorsClickListener;
import io.github.meness.easyintro.utils.AndroidUtils;
import io.github.meness.easyintro.views.DirectionalViewPager;
import io.github.meness.easyintro.views.LeftToggleIndicator;
import io.github.meness.easyintro.views.RightToggleIndicator;

public class EasyIntroCarouselFragment extends Fragment implements ICheck, IConfigOnce, ISlide, OnBackPressListener, IConfig, OnToggleIndicatorsClickListener {
    private SparseArray<Class> mDisableLeftIndicatorOn = new SparseArray<>();
    private SparseArray<Class> mDisableRightIndicatorOn = new SparseArray<>();
    private EasyIntroPagerAdapter mAdapter;
    private DirectionalViewPager mPager;
    private ViewGroup mIndicatorsContainer;
    private ToggleIndicator mToggleIndicator = ToggleIndicator.DEFAULT;
    private RightToggleIndicator mRightIndicator;
    private LeftToggleIndicator mLeftIndicator;
    @RawRes
    private int mSoundRes = -1; // no sound by default
    @LayoutRes
    private int mIndicatorRes = PageIndicator.CIRCLE.getIndicatorRes(); // circle page indicator by default
    private Vibrator mVibrator;
    private int mVibrateIntensity;
    private boolean mVibrate;
    private boolean mRtlSupport;
    private SwipeDirection mSwipeDirection = SwipeDirection.ALL;
    private MaterializeBuilder mMaterializeBuilder;
    private EasyIntroInteractionsListener mInteractionsListener;

    public void setInteractionsListener(EasyIntroInteractionsListener listener) {
        this.mInteractionsListener = listener;
    }

    @CallSuper
    protected void onSlideChanged(Fragment fragment) {
        if (mSoundRes != -1) {
            MediaPlayer.create(getContext(), mSoundRes).start();
        }
        if (mVibrate) {
            mVibrator.vibrate(mVibrateIntensity);
        }

        // disable left indicator for specific slide
        for (int i = 0; i < mDisableLeftIndicatorOn.size(); i++) {
            if (fragment.getClass().getName().equalsIgnoreCase(mDisableLeftIndicatorOn.get(i).getName())) {
                mLeftIndicator.withDisabled(true);
                mPager.setAllowedSwipeDirection(SwipeDirection.LEFT);
                break;
            } else {
                mLeftIndicator.withDisabled(false);
                withSwipeDirection(mSwipeDirection);
            }
        }
        // disable right indicator for specific slide
        for (int i = 0; i < mDisableRightIndicatorOn.size(); i++) {
            if (fragment.getClass().getName().equalsIgnoreCase(mDisableRightIndicatorOn.get(i).getName())) {
                mRightIndicator.withDisabled(true);
                mPager.setAllowedSwipeDirection(SwipeDirection.RIGHT);
                break;
            } else {
                mRightIndicator.withDisabled(false);
                withSwipeDirection(mSwipeDirection);
            }
        }

        updateToggleIndicators();
    }

    /**
     * set custom indicator. The indicator must have the `setViewPager(ViewPager)` method
     *
     * @param resId Custom indicator resource
     */
    @Override
    public final void withPageIndicator(@LayoutRes int resId) {
        mIndicatorRes = resId;
    }

    @Override
    public final void withTranslucentStatusBar(boolean b) {
        mMaterializeBuilder.withTranslucentStatusBarProgrammatically(b);
    }

    @Override
    public final void withStatusBarColor(@ColorInt int statusBarColor) {
        mMaterializeBuilder.withStatusBarColor(statusBarColor);
    }

    @Override
    public final void withOffScreenPageLimit(int limit) {
        mPager.setOffscreenPageLimit(limit);
    }

    @Override
    public final void withTransparentStatusBar(boolean b) {
        mMaterializeBuilder.withTransparentStatusBar(b);
    }

    @Override
    public final void withTransparentNavigationBar(boolean b) {
        mMaterializeBuilder.withTransparentNavigationBar(b);
    }

    @Override
    public final void withFullscreen(boolean b) {
        mMaterializeBuilder.withSystemUIHidden(b);
    }

    @Override
    public final void withTranslucentNavigationBar(boolean b) {
        mMaterializeBuilder.withTranslucentNavigationBarProgrammatically(b);
    }

    @Override
    public final void withSlideTransformer(SlideTransformer transformer) {
        mPager.setPageTransformer(true, transformer.getTransformer());
    }

    @Override
    public final void withToggleIndicator(ToggleIndicator indicators) {
        mToggleIndicator = indicators;

        // RTL swipe support
        if (mRtlSupport && indicators.getSwipeDirection() == SwipeDirection.LEFT) {
            withSwipeDirection(SwipeDirection.RIGHT);
        } else {
            withSwipeDirection(indicators.getSwipeDirection());
        }
    }

    private void withSwipeDirection(SwipeDirection direction) {
        mSwipeDirection = direction;
        mPager.setAllowedSwipeDirection(direction);
    }

    @Override
    public final void withVibrateOnSlide(int intensity) {
        setVibrateEnabled();
        mVibrateIntensity = intensity;
    }

    @Override
    public final void withVibrateOnSlide() {
        withVibrateOnSlide(20);
    }

    @Override
    public final void withRtlSupport() {
        mRtlSupport = true;
    }

    @Override
    /**
     * @see ViewPager#setPageMargin(int)
     */
    public final void withPageMargin(int marginPixels) {
        mPager.setPageMargin(marginPixels);
    }

    @Override
    /**
     * @see ViewPager#setPageMarginDrawable(Drawable)
     */
    public final void setPageMarginDrawable(Drawable d) {
        mPager.setPageMarginDrawable(d);
    }

    @Override
    /**
     * @see ViewPager#setPageMarginDrawable(int)
     */
    public final void setPageMarginDrawable(@DrawableRes int resId) {
        mPager.setPageMarginDrawable(resId);
    }

    @Override
    public final void withToggleIndicatorSound(boolean b) {
        mLeftIndicator.setSoundEffectsEnabled(b);
        mRightIndicator.setSoundEffectsEnabled(b);
    }

    @Override
    /**
     * Play a sound while sliding.
     * Pass 0 for no sound (default)
     *
     * @param sound Sound raw resource
     */
    public final void withSlideSound(@RawRes int sound) {
        mSoundRes = sound;
    }

    @Override
    /**
     * @see android.view.View#setOverScrollMode(int)
     */
    public final void withOverScrollMode(int mode) {
        mPager.setOverScrollMode(mode);
    }

    @Override
    /**
     * set predefined indicator.
     *
     * @param pageIndicator Custom indicator
     */
    public final void withPageIndicator(PageIndicator pageIndicator) {
        mIndicatorRes = pageIndicator.getIndicatorRes();
    }

    @Override
    public void withRightIndicatorDisabled(boolean b) {
        mRightIndicator.withDisabled(b);
    }

    @Override
    public void withLeftIndicatorDisabled(boolean b) {
        mLeftIndicator.withDisabled(b);
    }

    private void setVibrateEnabled() {
        if (!AndroidUtils.hasVibratePermission(getContext())) {
            Log.d(EasyIntro.TAG, getString(R.string.exception_permission_vibrate));
            return;
        }
        mVibrate = true;
    }

    @Override
    public final void withPageIndicatorVisibility(boolean b) {
        mIndicatorsContainer.findViewById(R.id.pageIndicator).setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public final void withRightIndicatorDisabled(boolean b, @NonNull Class slide) {
        mDisableRightIndicatorOn.append(mDisableRightIndicatorOn.size(), slide);
        if (b) {
            mPager.setAllowedSwipeDirection(SwipeDirection.RIGHT);
        } else {
            withSwipeDirection(mSwipeDirection);
        }
    }

    @Override
    public final void withLeftIndicatorDisabled(boolean b, @NonNull Class slide) {
        mDisableLeftIndicatorOn.append(mDisableLeftIndicatorOn.size(), slide);
        if (b) {
            mPager.setAllowedSwipeDirection(SwipeDirection.LEFT);
        } else {
            withSwipeDirection(mSwipeDirection);
        }
    }

    @Override
    public void withBothIndicatorsDisabled(boolean b, Class slide) {
        withLeftIndicatorDisabled(b, slide);
        withRightIndicatorDisabled(b, slide);
    }

    @Override
    public final void withNextSlide(boolean smoothScroll) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1, smoothScroll);
    }

    @Override
    public final void withPreviousSlide(boolean smoothScroll) {
        mPager.setCurrentItem(mPager.getCurrentItem() - 1, smoothScroll);
    }

    @Override
    public final void withSlideTo(int page, boolean smoothScroll) {
        mPager.setCurrentItem(page, smoothScroll);
    }

    @Override
    public final Fragment getCurrentSlide() {
        return mAdapter.getRegisteredFragment(mPager.getCurrentItem());
    }

    @Override
    public final void withSlide(Fragment slide) {
        mAdapter.addFragment(slide);
        updateToggleIndicators();
    }

    private void updateToggleIndicators() {
        if (mToggleIndicator == ToggleIndicator.NONE) {
            hideLeftIndicator();
            hideRightIndicator();
            return;
        }

        int slidesCount = mAdapter.getCount() - 1;
        int totalSlides = mAdapter.getCount();
        int currentItem = mPager.getCurrentItem();

        if (totalSlides == 0) {
            hideLeftIndicator();
            hideRightIndicator();
        } else if (totalSlides == 1) {
            hideLeftIndicator();
            showRightIndicator();
            mRightIndicator.makeItDone();
        } else {
            showRightIndicator();
            if (mToggleIndicator == ToggleIndicator.NO_LEFT_INDICATOR) {
                hideLeftIndicator();
            } else {
                showLeftIndicator();
            }
        }

        if (currentItem == slidesCount) {
            mRightIndicator.makeItDone();
            mLeftIndicator.makeItPrevious();
        } else if (currentItem < slidesCount) {
            if (currentItem == 0) {
                if (mToggleIndicator == ToggleIndicator.WITHOUT_SKIP || mToggleIndicator == ToggleIndicator.NO_LEFT_INDICATOR) {
                    hideLeftIndicator();
                } else {
                    mLeftIndicator.makeItSkip();
                    showLeftIndicator();
                }
            } else {
                if (mToggleIndicator == ToggleIndicator.NO_LEFT_INDICATOR) {
                    hideLeftIndicator();
                } else {
                    mLeftIndicator.makeItPrevious();
                    showLeftIndicator();
                }
            }
            mRightIndicator.makeItNext();
        }
    }

    private void hideLeftIndicator() {
        mLeftIndicator.hide();
    }

    private void hideRightIndicator() {
        mRightIndicator.hide();
    }

    private void showRightIndicator() {
        mRightIndicator.show();
    }

    private void showLeftIndicator() {
        mLeftIndicator.show();
    }

    @Override
    public void withOverlaySlide(Fragment slide, @IdRes int container) {
        Fragment currentSlide = getCurrentSlide();
        FragmentTransaction transaction = currentSlide.getChildFragmentManager().beginTransaction();
        // add to back stack
        transaction.addToBackStack(null);
        transaction.replace(container, slide).commit();
    }

    @Override
    public void withSlideTo(Class slideClass, boolean smoothScroll) {
        withSlideTo(mAdapter.getItemPosition(slideClass), smoothScroll);
    }

    @Override
    public final void onRightToggleClick() {
        int slidesCount = mAdapter.getCount() - 1;
        int currentItem = mPager.getCurrentItem();
        int nextItem = currentItem + 1;

        // we're on the last slide
        if (currentItem == slidesCount) {
            onDonePressed();
        }
        // we're going to the last slide
        else if (nextItem == slidesCount) {
            mLeftIndicator.makeItPrevious();
            mRightIndicator.makeItDone();
            onNextSlide();
        }
        // we're going to the next slide
        else {
            mLeftIndicator.makeItPrevious();
            onNextSlide();
        }
    }

    @Override
    public final void onLeftToggleClick() {
        int currentItem = mPager.getCurrentItem();
        int previousItem = currentItem - 1;

        // we're on the first slide
        if (currentItem == 0) {
            onSkipPressed();
        }
        // we're going to the first slide
        else if (previousItem == 0) {
            mRightIndicator.makeItNext();
            mLeftIndicator.makeItSkip();
            onPreviousSlide();
        }
        // we're going to the previous slide
        else {
            mRightIndicator.makeItNext();
            mLeftIndicator.makeItPrevious();
            onPreviousSlide();
        }
    }

    @Override
    public void onPreviousSlide() {
        // pass to current fragment
        Fragment currentFragment = mAdapter.getRegisteredFragment(mPager.getCurrentItem());
        if (currentFragment instanceof EasyIntroFragment) {
            ((EasyIntroFragment) currentFragment).onPreviousSlide();
        }
        // pass to EasyIntro activity
        mInteractionsListener.onPreviousSlide();
        withPreviousSlide(true);
    }

    @Override
    public void onNextSlide() {
        // pass to current fragment
        Fragment currentFragment = mAdapter.getRegisteredFragment(mPager.getCurrentItem());
        if (currentFragment instanceof EasyIntroFragment) {
            ((EasyIntroFragment) currentFragment).onNextSlide();
        }
        // pass to EasyIntro activity
        mInteractionsListener.onNextSlide();
        withNextSlide(true);
    }

    @Override
    public void onDonePressed() {
        // pass to current fragment
        Fragment currentFragment = mAdapter.getRegisteredFragment(mPager.getCurrentItem());
        if (currentFragment instanceof EasyIntroFragment) {
            ((EasyIntroFragment) currentFragment).onDonePressed();
        }
        // pass to EasyIntro activity
        mInteractionsListener.onDonePressed();
    }

    @Override
    public void onSkipPressed() {
        // pass to current fragment
        Fragment currentFragment = mAdapter.getRegisteredFragment(mPager.getCurrentItem());
        if (currentFragment instanceof EasyIntroFragment) {
            ((EasyIntroFragment) currentFragment).onSkipPressed();
        }
        // pass to EasyIntro activity
        mInteractionsListener.onSkipPressed();
    }

    @Override
    public boolean isLocked() {
        return getSwipeDirection() == SwipeDirection.NONE;
    }

    @Override
    public SwipeDirection getSwipeDirection() {
        return mPager.getSwipeDirection();
    }

    @Override
    public final int getPageMargin() {
        return mPager.getPageMargin();
    }

    @Override
    public final boolean isRightIndicatorVisible() {
        return mRightIndicator.isVisible();
    }

    @Override
    public final boolean isRightIndicatorDisabled() {
        return mRightIndicator.isDisabled();
    }

    @Override
    public final boolean isLeftIndicatorVisible() {
        return mLeftIndicator.isVisible();
    }

    @Override
    public final boolean isLeftIndicatorDisabled() {
        return mLeftIndicator.isDisabled();
    }

    /**
     * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
     *
     * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
     */
    public boolean onBackPressed() {
        // currently visible tab Fragment
        OnBackPressListener currentFragment = (OnBackPressListener) mAdapter.getRegisteredFragment(mPager.getCurrentItem());

        if (currentFragment != null) {
            // lets see if the currentFragment or any of its childFragment can handle onBackPressed
            return currentFragment.onBackPressed();
        }

        // this Fragment couldn't handle the onBackPressed call
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new EasyIntroPagerAdapter(getChildFragmentManager());
        mVibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_easyintro_carousel, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // init views
        mPager = (DirectionalViewPager) view.findViewById(R.id.pager);
        mIndicatorsContainer = (ViewGroup) view.findViewById(R.id.indicatorsContainer);
        mRightIndicator = (RightToggleIndicator) view.findViewById(R.id.nextIndicator);
        mLeftIndicator = (LeftToggleIndicator) view.findViewById(R.id.previousIndicator);

        mRightIndicator.setListener(this);
        mLeftIndicator.setListener(this);

        mPager.setAdapter(mAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                onSlideChanged(mAdapter.getRegisteredFragment(position));
            }
        });

        mInteractionsListener.onCarouselViewCreated(view, savedInstanceState);

        addIndicator();
        updateToggleIndicators();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // native init
        mMaterializeBuilder = new MaterializeBuilder(getActivity());

        // finalize
        mMaterializeBuilder.build();
    }

    private void addIndicator() {
        if (mIndicatorRes != -1) {
            ViewStub viewStub = (ViewStub) mIndicatorsContainer.findViewById(R.id.pageIndicator);
            viewStub.setLayoutResource(mIndicatorRes);
            // id must be set
            viewStub.inflate().setId(R.id.pageIndicator);
            setViewPagerToPageIndicator();
        }
    }

    private void setViewPagerToPageIndicator() {
        try {
            View view = mIndicatorsContainer.findViewById(R.id.pageIndicator);
            // invoke indicator `setViewPager(ViewPager)` method
            view.getClass().getMethod("setViewPager", ViewPager.class).invoke(view, mPager);
        } catch (NoSuchMethodException e) {
            Log.e(EasyIntro.TAG, getString(R.string.exception_no_such_method_set_view_pager));
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.e(EasyIntro.TAG, getString(R.string.exception_invocation_target_set_view_pager));
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(EasyIntro.TAG, getString(R.string.exception_illegal_access_set_view_pager));
            e.printStackTrace();
        }
    }
}