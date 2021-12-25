package br.com.beveragesuggester.control;

import java.util.concurrent.ThreadLocalRandom;

public class RandomizerService {

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public int randomInt(int bound) {
        return random.nextInt(bound);
    }
}
