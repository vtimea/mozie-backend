package com.mozie.utils;

import com.mozie.model.api.ErrorMessage;

public class ErrorMessages {
    public static ErrorMessage ERROR_INVALID_FB_TOKEN = new ErrorMessage(1, "Invalid facebook token");
    public static ErrorMessage ERROR_CANNOT_CREATE_USER = new ErrorMessage(2, "User couldn't be created.");
}
