package com.jdy.angel.server.ebook.core;

import java.net.URI;

/**
 * @author Aglet
 * @create 2022/7/3 21:22
 */
public record Success(URI uri, String body) implements Result {
}
