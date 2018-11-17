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

package io.github.meness.easyintro.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.meness.easyintro.EasyIntroFragment;
import io.github.meness.easyintro.app.R;

public class OverlaySlides extends EasyIntroFragment {

    //region Bind views
    @BindView(R.id.loginBtn)
    Button loginBtn;

    @BindView(R.id.joinBtn)
    Button joinBtn;
    //endregion

    public static OverlaySlides newInstance() {
        Bundle args = new Bundle();

        OverlaySlides fragment = new OverlaySlides();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overlay, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withSlide(LoginFragment.newInstance(), R.id.container, getChildFragmentManager(), false);
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withSlide(JoinFragment.newInstance(), R.id.container, getChildFragmentManager(), false);
            }
        });
    }
}
