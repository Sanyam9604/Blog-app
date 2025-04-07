package com.javasb.blog.blog_app_apis.exceptions;

public class UnsupportedFileTypeException extends RuntimeException {

    String givenFileType;

    String fieldName;

    String fieldValue;

    public UnsupportedFileTypeException(String givenFileType, String fieldName, String fieldValue) {
        super(String.format("%s type is not supported for file %s : %s", givenFileType, fieldName, fieldValue));
        this.givenFileType = givenFileType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
