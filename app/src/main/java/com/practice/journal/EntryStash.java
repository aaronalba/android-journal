/**
 * Class that serves as an interface to the list of entries being maintained by the Journal App.
 * This class follows a singleton pattern to avoid problems in having multiple instances of the
 * list of journal entries.
 * @author Aaron Alba
 */

package com.practice.journal;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class EntryStash {
    // the singleton instance of this class
    private static EntryStash sEntryStash;

    // the list containing entries
    private List<Entry> mList;


    // Private constructor to avoid instantiating objects from this class
    private EntryStash(Context context) {
        mList = new ArrayList<>();
        dummy();    // test method that fills the list with dummy values
    }


    /**
     * This method returns the only instance of this class.
     * @param context The application context to be used in opening an SQLite Database as a backend for this application. (future implementation)
     * @return The single instance of this EntryStash.
     */
    public static EntryStash get(Context context) {
        if (sEntryStash == null) {
            sEntryStash = new EntryStash(context);
        }
        return sEntryStash;
    }


    /**
     * Returns the list of journal entries.
     * @return List
     */
    public List<Entry> getEntries() {
        return mList;
    }


    /**
     * Adds an entry to the list of journal entries.
     * @param entry The entry to be added to the list.
     */
    public void addEntry(Entry entry) {
        this.mList.add(entry);
    }


    public void updateEntry() {
        // TODO: implement update of an entry in the list of entries
    }


    public void deleteEntry() {
        // TODO: implement deletion of an entry in the list of entries
    }


    // tester method that adds 100 dummy entries to the list
    private void dummy() {
        final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vitae varius nulla, quis consequat urna. Cras tincidunt odio dui, ut pretium arcu convallis mollis. Nulla sagittis neque ac ex sodales consequat. Praesent eu arcu elit. Vestibulum blandit aliquam dolor at finibus. Praesent id turpis sollicitudin, varius magna ac, blandit eros. Duis rutrum risus eget nulla mattis auctor. Nulla luctus in orci ut interdum. Quisque vehicula orci nisi, et vulputate felis convallis dignissim. Ut dapibus velit vitae porta vulputate. Proin laoreet elementum tincidunt. Nunc interdum, erat ut varius bibendum, mi diam efficitur arcu, id interdum orci orci non nulla. Sed facilisis et nibh eget scelerisque. Proin id neque sit amet magna fringilla ullamcorper sed quis ligula. Integer luctus rhoncus justo, a elementum risus laoreet vel.\n" +
                "\n" +
                "Duis velit ipsum, fringilla eu bibendum ut, egestas eu nulla. Nunc placerat in eros sed imperdiet. Nam iaculis ipsum pellentesque lacinia pretium. Cras imperdiet, arcu venenatis cursus eleifend, lacus risus sagittis orci, non lobortis nibh arcu id mauris. Duis laoreet quam nisi. Nulla facilisi. Nulla ut condimentum urna. Nam lacus erat, bibendum sit amet vehicula eu, pretium sed turpis. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent porttitor quam leo. Duis eleifend lacus sed ullamcorper volutpat. Donec tempus rhoncus massa, vel lobortis magna faucibus vitae. Integer in iaculis dui.";

        for(int i=0; i<100; i++) {
            Entry e = new Entry();
            e.setTitle("Title " + (i+1));
            e.setContent(lorem);

            this.addEntry(e);
        }
    }
}
