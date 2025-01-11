package com.catsriding.octalink.common.adapter.input.api;

public record ApiResponse<T>(
        String status,
        T data,
        String message) {

    private final static String API_STATUS_SUCCESS = "SUCCESS";
    private final static String API_STATUS_ERROR = "ERROR";

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(API_STATUS_SUCCESS, data, message);
    }
}
