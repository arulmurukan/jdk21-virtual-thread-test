package com.arul.virtual.thread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.util.Map;

@SpringBootApplication
public class VirtualThreadTestApplication {

    public static void main(String[] args) {
        var threads = String.valueOf(Runtime.getRuntime().availableProcessors());

        Map.of("server.tomcat.threads.max", threads,
                        "jdk.virtualThreadScheduler.maxPoolSize", threads)
                .forEach(System::setProperty);

        SpringApplication.run(VirtualThreadTestApplication.class, args);
    }

    @Bean
    RestClient restClient(RestClient.Builder builder) {

        return builder.build();
    }

    @Controller
    @ResponseBody
    static class HttpBinClientController {
        private final RestClient client;

        public HttpBinClientController(RestClient client) {
            this.client = client;
        }

        @GetMapping("/{seconds}")
        Map<String, String> call(@PathVariable int seconds) {
            var result = client.get()
                    .uri("http://localhost:9091/delay/" + seconds)
                    .retrieve()
                    .body(String.class);

            return Map.of("response", result);
        }
    }

}
