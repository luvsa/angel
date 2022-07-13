package com.jdy.angel.server.ebook.vary.request;

import com.jdy.angel.server.ebook.core.net.Request;
import org.luvsa.vary.DataType;
import org.luvsa.vary.Factory;
import org.luvsa.vary.FunctionManager;
import org.luvsa.vary.TypeSupplier.Types;

import java.util.function.Function;

/**
 * @author Aglet
 * @create 2022/7/13 0:03
 */
@Types(Request.class)
public class RFactory extends FunctionManager<Request, RProvider> implements Factory<Request> {

    @Override
    public Function<Request, ?> create(DataType type) {
        return cache.computeIfAbsent(type.getClazz(), this::offer);
    }

}
