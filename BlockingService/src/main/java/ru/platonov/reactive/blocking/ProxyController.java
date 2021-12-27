package ru.platonov.reactive.blocking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * ProxyController
 * <p>
 * </p>
 *
 * @author Platonov Alexey
 * @since 16.12.2021
 */
@RestController
public class ProxyController {

    @Value("${http.client.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/sync")
    public String getUserSync(@RequestParam long delay) throws ExecutionException, InterruptedException {
        return restTemplate.getForObject(
                new StringBuilder().append(url).append("/getTest?delay=").append(delay).toString(),
                String.class
        );
    }

    @GetMapping(value = "/getTest")
    public String proxy(@RequestParam long delay) throws ExecutionException, InterruptedException {
        return restTemplate.getForObject(
                new StringBuilder().append(url).append("/getTest?delay=").append(delay).toString(),
                String.class
        );
    }

    @GetMapping(value = "/logic/sync")
    public ResponseEntity<String> syncLogic(@RequestParam int length, @RequestParam int iteration) {
        calculations(length, iteration);
        return ResponseEntity.ok("Calculation finished");
    }

    @GetMapping(value = "/logic/syncandrequest")
    public ResponseEntity<String> syncLogicAndRequest (@RequestParam int length, @RequestParam int iteration, @RequestParam long delay) {
        restTemplate.getForObject(
                new StringBuilder()
                        .append(url)
                        .append("/logic/syncandrequest?length=").append(length)
                        .append("&iteration=").append(iteration)
                        .append("&delay=").append(delay)
                        .toString(),
                String.class
        );
        calculations(length, iteration);
        return ResponseEntity.ok("Calculation finished");
    }

    private static int [] getRandomArray(int length) {
        int [] arr = new int[length];
        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            arr[i] = random.nextInt(1000);
        }

        return arr;
    }

    private static void calculations(int length, int iteration) {
        for (int i = 0; i < iteration; i++) {
            Arrays.sort(getRandomArray(length));
        }
    }
}
