package com.jdy.angel.server.ebook.core.labels;

import com.jdy.angel.ebook.core.labels.Div;
import org.junit.jupiter.api.Test;

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