package com.powerledger.sse_implementation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AiStreamingService {

    private final ExecutorService executor = Executors.newCachedThreadPool();

    public SseEmitter SseEmitterService(Map<String, String> body) {
        String prompt = body.get("prompt");

        SseEmitter emitter = new SseEmitter(0L); // no timeout

        executor.execute(() -> {
            try {
                // start event
                emitter.send(SseEmitter.event()
                        .data(Map.of("type", "start")));

                // fake token generation
                String response = generateFakeResponse(prompt);
                String[] tokens = response.split(" ");
                for (String token : tokens) {

                    emitter.send(SseEmitter.event()
                            .data(Map.of(
                                    "type", "delta",
                                    "content", token + " "
                            )));

                    Thread.sleep(300); // simulate thinking
                }

                // end event
                emitter.send(SseEmitter.event()
                        .data(Map.of("type", "end")));

                emitter.complete();

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    private String generateFakeResponse(String prompt) {
        return "Why did the chicken cross the road? To get to the other side.";
    }
}
