package com.alraedah.cycle.controller;

import com.alraedah.cycle.controller.swagger.SwaggerDetectCycleController;
import com.alraedah.cycle.dto.CycleResponseDto;
import com.alraedah.cycle.dto.DetectCycleRequestDto;
import com.alraedah.cycle.dto.DetectCycleResponseDto;
import com.alraedah.cycle.model.DetectCycleModel;
import com.alraedah.cycle.service.DetectCycleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(path = "/alraedah/v1")
@RequiredArgsConstructor
public class DetectCycleController implements SwaggerDetectCycleController {

    private final DetectCycleService detectCycleService;

    @PostMapping("/detect")
    public ResponseEntity<DetectCycleResponseDto> detect(@RequestBody @Valid DetectCycleRequestDto request) {

        List<DetectCycleModel> detectCycleModels = detectCycleService.detectCycle(request.getRequest());

        List<CycleResponseDto> cycleResponseDtos = detectCycleModels.stream()
                .map(detectCycleModel ->
                        CycleResponseDto.builder()
                                .integers(detectCycleModel.getIntegers())
                                .isCycle(detectCycleModel.isCycle())
                                .build()
                )
                .collect(toList());

        DetectCycleResponseDto responseDto = DetectCycleResponseDto.builder()
                .cycleResponseDtos(cycleResponseDtos)
                .build();

        return ResponseEntity.ok(responseDto);

    }
}
