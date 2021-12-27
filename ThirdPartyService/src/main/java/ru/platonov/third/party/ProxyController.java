package ru.platonov.third.party;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    @GetMapping(value = "/getTest")
    public Mono<String> getUserSync(@RequestParam long delay) throws InterruptedException {
        return Mono.just("OLOLO-USER").delayElement(Duration.ofMillis(delay));
    }

    @GetMapping(value = "/logic/asyncandrequest")
    public Mono<String> asyncLogicAndRequest (@RequestParam int length, @RequestParam int iteration, @RequestParam long delay) throws InterruptedException {
        return Mono.just("OLOLO-USER").delayElement(Duration.ofMillis(delay));
    }

    @GetMapping(value = "/logic/syncandrequest")
    public Mono<String> syncLogicAndRequest (@RequestParam int length, @RequestParam int iteration, @RequestParam long delay) throws InterruptedException {
        return Mono.just("OLOLO-USER").delayElement(Duration.ofMillis(delay));
    }
}
