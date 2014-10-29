package com.swivl.furdey.swivlgit.content.client;

/**
 * Used to distinguish expected errors from the unexpected ones
 */
public class ClientException extends Exception {
    public ClientException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
