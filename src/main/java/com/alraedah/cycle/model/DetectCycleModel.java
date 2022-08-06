package com.alraedah.cycle.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DetectCycleModel {
    List<Integer> integers;
    boolean isCycle;
}
