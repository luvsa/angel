package com.jdy.angel.vary.request;

import com.jdy.angel.ebook.core.net.Request;
import org.luvsa.vary.AbstractFactory;
import org.luvsa.vary.TypeSupplier.Types;

/**
 * @author Aglet
 * @create 2022/7/13 0:03
 */
@Types(Request.class)
public class RFactory extends AbstractFactory<Request, RProvider> {
}
