package com.alraedah.cycle.service;

import com.alraedah.cycle.model.DetectCycleModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class DetectCycleServiceTest {

    @InjectMocks
    private DetectCycleService underTest;

    @Test
    void itShouldDetectAndReturnCycleModels() {

        // given
        List<List<Integer>> input = List.of(
                List.of(1, 2, 3),
                List.of(3, 0, 1, 2)
        );

        // when
        List<DetectCycleModel> actual = underTest.detectCycle(input);

        // then
        List<DetectCycleModel> expected = List.of(
                buildExpected(List.of(1, 2, 3), false),
                buildExpected(List.of(3, 0, 1, 2), true)
        );

        assertThat(actual).isEqualTo(expected);
    }

    private DetectCycleModel buildExpected(List<Integer> input, boolean isCycle) {
        return DetectCycleModel.builder()
                .integers(input)
                .isCycle(isCycle)
                .build();
    }
}
