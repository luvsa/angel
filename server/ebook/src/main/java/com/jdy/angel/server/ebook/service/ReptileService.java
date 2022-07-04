package com.jdy.angel.server.ebook.service;

import com.jdy.angel.server.ebook.core.Reptilian;
import org.springframework.lang.NonNull;

import java.net.URI;

/**
 * 爬虫服务
 *
 * @author Aglet
 * @create 2022/7/3 19:41
 */
public interface ReptileService extends Reptilian {

   default void start(@NonNull String domain){
        start(URI.create(domain));
   }
}
