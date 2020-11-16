package com.mozie.utils;

import com.mozie.model.api.ErrorMessage;

public class ErrorResponses {
    public static ErrorMessage ERROR_INVALID_FB_TOKEN = new ErrorMessage(1, "Invalid facebook token");
    public static ErrorMessage ERROR_CANNOT_CREATE_USER = new ErrorMessage(2, "User couldn't be created.");
    public static ErrorMessage ERROR_INVALID_DATE_OR_CINEMA = new ErrorMessage(3, "Invalid date or cinema.");
    public static ErrorMessage ERROR_MOVIEID_NULL = new ErrorMessage(4, "Invalid movieId");
}
