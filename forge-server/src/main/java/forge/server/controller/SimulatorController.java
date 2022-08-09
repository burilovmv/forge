package forge.server.controller;

import forge.server.core.SimulationsQueue;
import forge.server.model.SimulationStartRequest;
import forge.server.model.SimulationStartResponse;
import forge.server.model.SimulationStatusResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class SimulatorController {

    @PostMapping("/simulation")
    public SimulationStartResponse startSimulation(
            @RequestBody SimulationStartRequest request
            ) {
        long simId = SimulationsQueue.getInstance().start(request);
        if(simId > 0) {
            return new SimulationStartResponse(simId, "Simulation started", true);
        }

        return new SimulationStartResponse(simId, "Server busy", false);
    }

    @GetMapping("/simulation/{id}")
    public SimulationStatusResponse getSimulationStatus(@PathVariable Long id) {
        return SimulationsQueue.getInstance().getStatus(id);
    }
}
