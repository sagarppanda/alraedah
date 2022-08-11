package com.alraedah.cycle.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DetectCycleRequestDto {
    @NotEmpty(message = "should not be empty")
    @ApiModelProperty(value = "List of integers", required = true)
    List<@NotEmpty(message = "should not be empty") List<Integer>> request;
}
