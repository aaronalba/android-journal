/**
 * Class for representing a Journal Entry.
 * @author Aaron Alba
 */

package com.practice.journal;

import java.util.Date;
import java.util.UUID;

public class Entry {
    /**
     * A unique id for this journal entry which will be used for identifying this entry.
     */
    private UUID mId;

    /**
     * The title of this journal entry.
     */
    private String mTitle;

    /**
     * The date on which this entry was created.
     */
    private Date mDate;

    /**
     * The journal entry.
     */
    private String mContent;




    /**
     * Creates a new journal entry.
     * Default Constructor
     */
    public Entry() {
        this.mId = UUID.randomUUID();    // create an id for this journal entry
        this.mDate = new Date();    // set the date of this entry
    }


    /**
     * This constructor creates a new Entry using a given UUID. This is used for recreating an entry
     * that is retrieved from a database.
     * @param uuidString The string representation of the UUID
     */
    public Entry(String uuidString) {
        this.mId = UUID.fromString(uuidString);
        this.mDate = new Date();
    }




    /**
     * Returns the id of this journal entry.
     * @return UUID this entry.
     */
    public UUID getId() {
        return mId;
    }


    /**
     * Returns the title of this journal entry.
     * @return String containing the title of this entry.
     */
    public String getTitle() {
        return mTitle;
    }


    /**
     * Returns the date of this journal entry.
     * @return Date object containing the date when this entry was created.
     */
    public Date getDate() {
        return mDate;
    }


    /**
     * Returns the content of this journal entry.
     * @return String containing the content of the entry.
     */
    public String getContent() {
        return mContent;
    }


    /**
     * Setter method for the content of this journal entry.
     * @param content The content to be set to this journal entry.
     */
    public void setContent(String content) {
        mContent = content;
    }


    /**
     * Setter method for the date of this journal entry.
     * @param date The date to be set to this journal entry.
     */
    public void setDate(Date date) {
        mDate = date;
    }


    /**
     * Setter method for the title of this journal entry.
     * @param title The title to be set to this entry.
     */
    public void setTitle(String title) {
        mTitle = title;
    }
}
