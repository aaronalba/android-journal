/**
 * DialogFragment containing a prompt that ask the user to confirm an action using an AlertDialog.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PromptDialogFragment extends DialogFragment {
    // String used for the message of the Alert Dialog
    private String mDialogMessage;

    // String used for the label of the positive button
    private String mPositiveButtonMessage;

    private static final String NEGATIVE_BTN_MSG = "Cancel";
    private static final String EXTRA_PROMPT = "com.practice.journal.prompt";

    /**
     * Creates an Alert Dialog with the given message
     * @param dialogMsg The message that will be shown in the Title of the Alert Dialog
     * @param positiveMsg The meesage that will be shown in the Positive button of the Alert Dialog
     */
    public PromptDialogFragment(String dialogMsg, String positiveMsg) {
        mDialogMessage = dialogMsg;
        mPositiveButtonMessage = positiveMsg;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // create the alert dialog that will be shown by this fragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mDialogMessage)
                .setPositiveButton(mPositiveButtonMessage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(true);
                    }
                })
                .setNegativeButton(NEGATIVE_BTN_MSG, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(false);
                    }
                });

        return builder.create();
    }


    /**
     * Method used to send back the result of this prompt back to the caller using the Target Fragment
     * @param value The result of the prompt.
     */
    private void sendResult(boolean value) {
        // create the intent that will hold the result
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PROMPT, value);

        // send the result back to the caller using Target Fragment
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

    }
}
