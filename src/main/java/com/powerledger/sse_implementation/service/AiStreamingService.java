package com.powerledger.sse_implementation.service;

import com.powerledger.sse_implementation.model.Diagnosis;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
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

    public SseEmitter SseJsonEmitterService() {
        SseEmitter emitter = new SseEmitter(0L);

        executor.execute(() -> {
            try {

                emitter.send(SseEmitter.event()
                        .data(Map.of("type", "start")));

                List<Diagnosis> diagnoses = getDiagnoses();

                for (Diagnosis d : diagnoses) {
                    emitter.send(SseEmitter.event()
                            .data(Map.of(
                                    "type", "delta",
                                    "item", d
                            )));
                    Thread.sleep(700);
                }

                emitter.send(SseEmitter.event()
                        .data(Map.of("type", "end")));

                emitter.complete();

            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    //fake diagonalises json
    public List<Diagnosis> getDiagnoses() {

        List<Diagnosis> list = new ArrayList<>();

        list.add(new Diagnosis(
                "Urinary Tract Infection, unspecified",
                0.642,
                "N39.0",
                "Elderly patients often present with nonspecific fever.",
                List.of(
                        "Age 79 years",
                        "Mild leukocytosis",
                        "No other source found"
                )
        ));

        list.add(new Diagnosis(
                "Community-acquired pneumonia",
                0.499,
                "J18.9",
                "Pneumonia may present with isolated fever.",
                List.of(
                        "Advanced age",
                        "Possible infection"
                )
        ));

        list.add(new Diagnosis(
                "Drug-induced fever",
                0.579,
                "T88.7",
                "Medication reaction possible.",
                List.of(
                        "Chronic therapy"
                )
        ));

        list.add(new Diagnosis(
                "Fever of unknown origin",
                0.444,
                "R50.9",
                "No definitive source identified.",
                List.of(
                        "Pending investigation"
                )
        ));

        return list;
    }
}
