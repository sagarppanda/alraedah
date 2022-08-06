package com.alraedah.cycle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DetectCycleResponseDto {
    List<CycleResponseDto> cycleResponseDtos;
}
