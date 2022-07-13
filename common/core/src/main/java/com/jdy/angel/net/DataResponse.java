package com.jdy.angel.net;

/**
 * @author Aglet
 * @create 2022/7/12 19:40
 */
public record DataResponse<T>(T data) implements Response {
}
