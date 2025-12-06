package com.example.dsl.functions;

import com.example.dsl.exec.EvalContext;
import java.util.List;
import java.util.Map;

public interface DslFunction {
    Object apply(List<Object> args, Map<String, Object> namedArgs, EvalContext ctx);
}
