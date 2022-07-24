package com.tweetapp.dto;


import lombok.Data;

import java.util.Date;


/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */
@Data
public class ExceptionDetails {

    /**
     * in short summary of the error  message occurred.
     */
    private final String shortMessage;

    /**
     * long message about why the error occurred.
     */
    private final String longMessage;

    /**
     * uri of the endpoint.
     */
    private final String resourcePath;

    /**
     * time stamp of the event occurred.
     */
    private final Date timeStamp;

    public ExceptionDetails(final String pShortMessage, final String pLongMessage,
                            final String pResourcePath, final Date pTimeStamp) {
        shortMessage = pShortMessage;
        longMessage = pLongMessage;
        resourcePath = pResourcePath;
        timeStamp = pTimeStamp;
    }
}
