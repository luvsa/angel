package com.jdy.angel.server.ebook.core;

import java.util.Map;

/**
 * @author Aglet
 * @create 2022/7/4 13:09
 */
abstract class Block extends Segment {

    protected Map<String, String> attributes;

    public Block() {
    }

    public Block(Map<String, String> attributes) {
        this.attributes = attributes;
    }


}
