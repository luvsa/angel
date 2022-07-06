package com.jdy.angel.server.ebook.service;

import org.springframework.lang.NonNull;

/**
 * 爬虫服务
 *
 * @author Aglet
 * @create 2022/7/3 19:41
 */
public interface ReptileService {

   default void start(@NonNull String domain){
//        start(URI.create(domain));
   }
}
