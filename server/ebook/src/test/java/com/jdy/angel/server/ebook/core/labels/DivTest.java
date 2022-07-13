package com.jdy.angel.server.ebook.core.labels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aglet
 * @create 2022/7/12 22:49
 */
class DivTest {

    @Test
    void of() {
        var div = Div.of("id='123'");
        System.out.println(div);
    }
}