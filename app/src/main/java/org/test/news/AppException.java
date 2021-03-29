package org.test.news;


public class AppException extends Exception {
    private int code;
    private String message = "There seems to be some error in getting response.\nPlease try again or visit later";

    public AppException(int code) {
        this(code, "There seems to be some error in getting response.\nPlease try again or visit later");
    }

    public AppException(String message) {
        this(-1, message);
    }

    public AppException(int code, String message) {
        this.code = code;
        this.message = message != null ? message : this.message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
