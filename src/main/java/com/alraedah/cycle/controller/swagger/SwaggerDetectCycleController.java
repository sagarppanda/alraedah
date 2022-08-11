package com.alraedah.cycle.controller.swagger;

import com.alraedah.cycle.dto.DetectCycleRequestDto;
import com.alraedah.cycle.dto.DetectCycleResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@Api(value = "/alraedah/v1")
public interface SwaggerDetectCycleController {

    @ApiOperation(value = "This endpoint detects occurrence of cycle in array list")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            code = 200,
                            message = "All transactions have been successfully validated"
                    )
            }
    )
    ResponseEntity<DetectCycleResponseDto> detect(DetectCycleRequestDto requestDto);
}
