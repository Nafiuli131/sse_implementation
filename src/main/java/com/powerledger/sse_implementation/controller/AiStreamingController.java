package com.powerledger.sse_implementation.controller;

import com.powerledger.sse_implementation.service.AiStreamingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiStreamingController {

    @Autowired
    AiStreamingService service;

    @PostMapping(value = "/ask", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter ask(@RequestBody Map<String, String> body) {
       return service.SseEmitterService(body);
    }

    @GetMapping(value = "/json", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter jsonSimulator() {
        return service.SseJsonEmitterService();
    }
}
