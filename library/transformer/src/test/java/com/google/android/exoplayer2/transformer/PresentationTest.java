/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.exoplayer2.transformer;

import static com.google.common.truth.Truth.assertThat;

import android.util.Size;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for {@link Presentation}.
 *
 * <p>See {@code PresentationPixelTest} for pixel tests testing {@link Presentation}.
 */
@RunWith(AndroidJUnit4.class)
public final class PresentationTest {
  @Test
  public void configure_noEdits_leavesFramesUnchanged() {
    int inputWidth = 200;
    int inputHeight = 150;
    Presentation presentation = new Presentation.Builder().build();

    Size outputSize = presentation.configure(inputWidth, inputHeight);

    assertThat(outputSize.getWidth()).isEqualTo(inputWidth);
    assertThat(outputSize.getHeight()).isEqualTo(inputHeight);
  }

  @Test
  public void configure_setResolution_changesDimensions() {
    int inputWidth = 200;
    int inputHeight = 150;
    int requestedHeight = 300;
    Presentation presentation = new Presentation.Builder().setResolution(requestedHeight).build();

    Size outputSize = presentation.configure(inputWidth, inputHeight);

    assertThat(outputSize.getWidth()).isEqualTo(requestedHeight * inputWidth / inputHeight);
    assertThat(outputSize.getHeight()).isEqualTo(requestedHeight);
  }

  @Test
  public void configure_setAspectRatio_changesDimensions() {
    int inputWidth = 300;
    int inputHeight = 200;
    float aspectRatio = 2f;
    Presentation presentation =
        new Presentation.Builder()
            .setAspectRatio(aspectRatio, Presentation.LAYOUT_SCALE_TO_FIT)
            .build();

    Size outputSize = presentation.configure(inputWidth, inputHeight);

    assertThat(outputSize.getWidth()).isEqualTo(Math.round(aspectRatio * inputHeight));
    assertThat(outputSize.getHeight()).isEqualTo(inputHeight);
  }

  @Test
  public void configure_setAspectRatioAndResolution_changesDimensions() {
    int inputWidth = 300;
    int inputHeight = 200;
    float aspectRatio = 2f;
    int requestedHeight = 100;
    Presentation presentation =
        new Presentation.Builder()
            .setAspectRatio(aspectRatio, Presentation.LAYOUT_SCALE_TO_FIT)
            .setResolution(requestedHeight)
            .build();

    Size outputSize = presentation.configure(inputWidth, inputHeight);

    assertThat(outputSize.getWidth()).isEqualTo(Math.round(aspectRatio * requestedHeight));
    assertThat(outputSize.getHeight()).isEqualTo(requestedHeight);
  }
}