package com.jdy.angel.server.ebook.core.net;

import org.springframework.lang.NonNull;

/**
 * @author Aglet
 * @create 2022/7/12 23:01
 */
public interface Sender {

    void start(@NonNull Request domain) throws Exception;

}
