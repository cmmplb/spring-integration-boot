package com.cmmplb.excel.handler.test;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ExcelSelect {
 
    String[] staticData() default {};
    String parentColumn() default "";
    Class<? extends ColumnDynamicSelectDataHandler> handler() ;
    String parameter() default "";
    int firstRow() default 1;
    int lastRow() default 0x10000;
}
