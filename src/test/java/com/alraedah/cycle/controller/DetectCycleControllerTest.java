package com.alraedah.cycle.controller;

import com.alraedah.cycle.dto.CycleResponseDto;
import com.alraedah.cycle.dto.DetectCycleRequestDto;
import com.alraedah.cycle.dto.DetectCycleResponseDto;
import com.alraedah.cycle.model.DetectCycleModel;
import com.alraedah.cycle.service.DetectCycleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class DetectCycleControllerTest {

    private static final String DETECT_CYCLE_URL = "/alraedah/v1/detect";

    @Mock
    private DetectCycleService detectCycleService;
    @InjectMocks
    private DetectCycleController underTest;
    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(underTest)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    void itShouldThrowNotEmptyExceptionWhenRequestIsEmpty() throws Exception {
        // given
        var requestDto = DetectCycleRequestDto.builder().request(emptyList()).build();
        var requestJson = getObjectWriter().writeValueAsString(requestDto);

        // when
        var response = mvc.perform(
                        post(DETECT_CYCLE_URL)
                                .contentType(APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(response).contains("\"status\":400,\"fields\":{\"request\":\"should not be empty\"}");
    }

    @Test
    void itShouldThrowNotEmptyExceptionWhenIntegersEmpty() throws Exception {
        // given
        var requestJson = "{\n" +
                "  \"request\": [\n" +
                "    [\n" +
                "      \n" +
                "    ],\n" +
                "    [\n" +
                "      \n" +
                "    ]\n" +
                "  ]\n" +
                "}";

        // when
        var response = mvc.perform(
                        post(DETECT_CYCLE_URL)
                                .contentType(APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        assertThat(response).contains("\"status\":400,\"fields\":{\"request[1]\":\"should not be empty\",\"request[0]\":\"should not be empty\"}");
    }

    @Test
    void itShouldReturnResponse() throws Exception {
        // given
        var requestDto = DetectCycleRequestDto.builder()
                .request(
                        List.of(
                                List.of(1, 2, 3),
                                List.of(3, 0, 1, 2)
                        )
                )
                .build();
        when(detectCycleService.detectCycle(requestDto.getRequest()))
                .thenReturn(
                        List.of(
                                buildDetectCycleModel(List.of(1, 2, 3), false),
                                buildDetectCycleModel(List.of(3, 0, 1, 2), true)
                        )
                );
        var requestJson = getObjectWriter().writeValueAsString(requestDto);

        // when
        var response = mvc.perform(
                        post(DETECT_CYCLE_URL)
                                .contentType(APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        var actual = deserialize(response, DetectCycleResponseDto.class);
        var expected = buildExpected();
        assertThat(actual).isEqualTo(expected);
    }

    private DetectCycleResponseDto buildExpected() {
        return DetectCycleResponseDto.builder()
                .cycleResponseDtos(List.of(
                                buildCycleResponseDto(List.of(1, 2, 3), false),
                                buildCycleResponseDto(List.of(3, 0, 1, 2), true)
                        )
                )
                .build();
    }

    private CycleResponseDto buildCycleResponseDto(List<Integer> input, boolean isCycle) {
        return CycleResponseDto.builder()
                .integers(input)
                .isCycle(isCycle)
                .build();
    }

    private DetectCycleModel buildDetectCycleModel(List<Integer> input, boolean isCycle) {
        return DetectCycleModel.builder()
                .integers(input)
                .isCycle(isCycle)
                .build();
    }

    private ObjectWriter getObjectWriter() {
        return new ObjectMapper().writer().withDefaultPrettyPrinter();
    }

    private <T> T deserialize(String jsonString, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(jsonString, clazz);

        } catch (Exception e) {
            fail("Failed to deserialize due to: " + e.getMessage());
        }
        return null;
    }
}
