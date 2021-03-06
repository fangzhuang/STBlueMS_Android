/*
 * Copyright (c) 2019  STMicroelectronics – All rights reserved
 * The STMicroelectronics corporate logo is a trademark of STMicroelectronics
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions
 *   and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice, this list of
 *   conditions and the following disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name nor trademarks of STMicroelectronics International N.V. nor any other
 *   STMicroelectronics company nor the names of its contributors may be used to endorse or
 *   promote products derived from this software without specific prior written permission.
 *
 * - All of the icons, pictures, logos and other images that are provided with the source code
 *   in a directory whose title begins with st_images may only be used for internal purposes and
 *   shall not be redistributed to any third party or modified in any way.
 *
 * - Any redistributions in binary form shall not include the capability to display any of the
 *   icons, pictures, logos and other images that are provided with the source code in a directory
 *   whose title begins with st_images.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */
package com.st.BlueMS.demos.Cloud;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.st.BlueMS.R;

import java.util.Arrays;


public class CloudLogSelectIntervalDialogFragment extends DialogFragment {

    private static final String CURRENT_INTERVAL_INDEX = CloudLogSelectIntervalDialogFragment.class.getName()+".CURRENT_INTERVAL_INDEX";

    public interface CloudLogSelectIntervalDialogCallback{
        void onNewUpdateIntervalSelected(int newUpdateInterval);
    }

    private static int getCurrentTimeIndex(int possibleValues[],int currentTime){
        return Arrays.binarySearch(possibleValues,currentTime);
    }

    public static DialogFragment create(Context context,int currentInterval){
        int[] sampleIntevals = context.getResources().getIntArray(R.array
                .cloudLog_updateIntervalValues);

        DialogFragment fragment = new CloudLogSelectIntervalDialogFragment();

        Bundle args = new Bundle();
        args.putInt(CURRENT_INTERVAL_INDEX,getCurrentTimeIndex(sampleIntevals,currentInterval));

        fragment.setArguments(args);
        return fragment;
    }


    private CloudLogSelectIntervalDialogCallback getCloudFwUpgradeRequestCallback() {
        Context ctx = getActivity();
        if(ctx instanceof CloudLogSelectIntervalDialogCallback) {
            return (CloudLogSelectIntervalDialogCallback) ctx;
        }

        Fragment parent = getParentFragment();
        if(parent instanceof CloudLogSelectIntervalDialogCallback){
            return (CloudLogSelectIntervalDialogCallback) parent;
        }

        throw new IllegalStateException("CloudLogSelectIntervalDialogFragment must attach to something "+
                "implementing CloudLogSelectIntervalDialogCallback");

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int checkedItem = getArguments().getInt(CURRENT_INTERVAL_INDEX,3);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(R.string.cloudLog_updateIntervalMsg);
        //onClick
        dialog.setSingleChoiceItems(R.array.cloudLog_updateIntervalString,checkedItem,
                  (dialog1, which) -> {
                        int[] sampleIntervals = getResources().getIntArray(R.array
                              .cloudLog_updateIntervalValues);
                        getCloudFwUpgradeRequestCallback()
                                .onNewUpdateIntervalSelected(sampleIntervals[which]);
                        dismiss();
                  }
            );
        return dialog.create();
    }

}
