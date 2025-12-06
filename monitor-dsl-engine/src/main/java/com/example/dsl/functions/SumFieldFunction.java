package com.example.dsl.functions;

import com.example.dsl.exec.EvalContext;
import java.util.List;
import java.util.Map;

public class SumFieldFunction implements DslFunction {

    @Override
    public Object apply(List<Object> args, Map<String, Object> namedArgs, EvalContext ctx) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) args.get(0);
        String field = (String) args.get(1); // The field name passed as arg
        
        long sum = 0;
        for (Map<String, Object> item : items) {
            Number n = (Number) item.get(field);
            if (n != null) {
                sum += n.longValue();
            }
        }
        return sum;
    }
}
