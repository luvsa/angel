package com.jdy.angel.net;

/**
 * @author Aglet
 * @create 2022/7/12 19:35
 */
public final class Responses {
    private Responses() {
    }

    public static <T> Response ok(T data) {
        return new DataResponse<>(data);
    }


}
