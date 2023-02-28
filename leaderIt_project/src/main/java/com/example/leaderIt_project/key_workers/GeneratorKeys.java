package com.example.leaderIt_project.key_workers;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GeneratorKeys {

    private final List<String> firstSubstringsForKey = new ArrayList<>(List.of("WILDCAT", "WIDEBEAR", "SMALLGIRAFFE",
            "FATBIRD", "HIGHRHINOCEROUS", "SPOTYDOG", "WAWYPARROT", "DANGEROUSHIENA", "BROUNSHARK", "CUTEEEL"));


    public String getGeneratedKey() {
        Random random = new Random();
        List<String> keys = new ArrayList<>(firstSubstringsForKey);
        String randomKey = keys.get(random.nextInt(keys.size()) );
        return randomKey + ((int) (Math.random() * 100000));
    }
}
