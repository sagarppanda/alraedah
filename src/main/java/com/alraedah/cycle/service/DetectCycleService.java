package com.alraedah.cycle.service;

import com.alraedah.cycle.model.DetectCycleModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DetectCycleService {

    public List<DetectCycleModel> detectCycle(List<List<Integer>> input) {

        List<DetectCycleModel> detectCycleModels = new ArrayList<>();
        input.forEach(integers -> {
                    boolean isCycle = isPerfectCycle(integers.stream().mapToInt(Integer::intValue).toArray());
                    DetectCycleModel model = DetectCycleModel.builder()
                            .integers(integers)
                            .isCycle(isCycle)
                            .build();
                    detectCycleModels.add(model);
                }
        );
        return detectCycleModels;
    }

    private int countLengthOfCycle(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int count = 1;
        int fast = arr[arr[0]];
        int slow = arr[0];
        while (fast != slow) {
            if (fast >= arr.length || arr[fast] >= arr.length) {
                return -1;
            }
            count++;
            fast = arr[arr[fast]];
            slow = arr[slow];
        }
        return count;
    }

    private boolean isPerfectCycle(int[] arr) {
        return arr.length == countLengthOfCycle(arr);
    }
}
