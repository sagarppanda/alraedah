package com.alraedah.cycle.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DetectCycleRequestDto {
    @ApiModelProperty(value = "List of integers", required = true)
    List<List<Integer>> request;
}
