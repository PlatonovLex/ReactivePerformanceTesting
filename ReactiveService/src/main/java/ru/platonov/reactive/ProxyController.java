package ru.platonov.reactive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Random;

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

    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/async")
    public Mono<String> getUserUsingWebfluxWebclient(@RequestParam long delay) {
        return webClient.get().uri("/getTest?delay={delay}", delay)
                        .retrieve()
                        .bodyToMono(String.class);
    }

    @GetMapping(value = "/getTest")
    public Mono<String> proxy(@RequestParam long delay) {
        return webClient.get().uri("/getTest?delay={delay}", delay)
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping(value = "/logic/async")
    public Mono<String> asyncLogic(@RequestParam int length, @RequestParam int iteration) {
        return Mono.just("a")
                   .map(a -> {
                       calculations(length, iteration);
                       return "Calculation finished";
                   });
    }

    @GetMapping(value = "/logic/asyncandrequest")
    public Mono<String> asyncLogicAndRequest (@RequestParam int length, @RequestParam int iteration, @RequestParam long delay) {
        return webClient.get().uri("/logic/asyncandrequest?length={length}&iteration={iteration}&delay={delay}",
                length, iteration, delay)
                        .retrieve()
                        .bodyToMono(String.class)
                        .map(e -> {
                            calculations(length, iteration);
                            return "Calculation finished";
                        });
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
