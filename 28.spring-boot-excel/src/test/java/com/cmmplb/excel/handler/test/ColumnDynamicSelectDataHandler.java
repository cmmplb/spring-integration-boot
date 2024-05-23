package com.cmmplb.excel.handler.test;

import java.util.function.Function;

public interface ColumnDynamicSelectDataHandler<T, R> {
    Function<T, R> source();
}
