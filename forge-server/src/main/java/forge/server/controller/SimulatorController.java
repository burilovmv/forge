package forge.server.controller;

import java.util.concurrent.atomic.AtomicLong;
import forge.server.model.SimulationStartResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimulatorController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/simulation")
    public SimulationStartResponse greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new SimulationStartResponse(counter.incrementAndGet(), String.format(template, name));
    }
}
