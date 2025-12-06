package com.example.dsl.ast;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Target {
    private TargetScope scope;
    private String name;
}
