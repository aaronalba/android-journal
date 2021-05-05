/**
 * Class that shows a prompt for confirming the deletion of an Entry.
 * @author Aaron Alba
 */

package com.practice.journal.fragments;

import android.app.Activity;
import android.content.Intent;

import com.practice.journal.models.Entry;

public class PromptDeleteEntryFragment extends PromptDialogFragment {
    private Entry mEntry;

    public static final String EXTRA_PROMPT_ENTRYID = "com.practice.journal.prompt_entryid";

    /**
     * Creates an Alert Dialog with the given message
     *
     * @param dialogMsg   The message that will be shown in the Title of the Alert Dialog
     * @param positiveMsg The meesage that will be shown in the Positive button of the Alert Dialog
     */
    public PromptDeleteEntryFragment(String dialogMsg, String positiveMsg, Entry entry) {
        super(dialogMsg, positiveMsg);
        mEntry = entry;
    }

    /**
     * Method used to send back the result and the entry referenced by this prompt
     * @param value The result of the prompt.
     */
    protected void sendResult(boolean value) {
        // create the intent that will hold the result
        Intent intent = new Intent();
        intent.putExtra(EXTRA_PROMPT, value);
        intent.putExtra(EXTRA_PROMPT_ENTRYID, mEntry.getId().toString());

        // send the result back to the caller using Target Fragment
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

    }
}
