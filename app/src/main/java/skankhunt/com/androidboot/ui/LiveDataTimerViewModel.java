/*
 * Copyright 2017, The Android Open Source Project
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

package skankhunt.com.androidboot.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;

import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * A ViewModel used for the {@link skankhunt.com.androidboot.LoginActivity}.
 * 1.在构造函数+ @Inject
 * 2.在di.ViewModelModule 添加
 */
public class LiveDataTimerViewModel extends ViewModel {

    private static final int ONE_SECOND = 1000;

    private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

    private long mInitialTime;
    @Inject
    public LiveDataTimerViewModel() {
        //mInitialTime = SystemClock.elapsedRealtime();
        Timer timer = new Timer();

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long newValue = (SystemClock.elapsedRealtime() - mInitialTime) / 1000;
                newValue = 60L -newValue <=0 ? 0:60L -newValue ;
                mElapsedTime.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);

    }

    public void setElapsedTime() {
        mInitialTime = SystemClock.elapsedRealtime();
    }

    @SuppressWarnings("unused")  // Will be used when step is completed
    public LiveData<Long> getElapsedTime() {
        return mElapsedTime;
    }
}
