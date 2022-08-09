package forge.server;

import forge.gui.GuiBase;
import forge.model.FModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String... args) {
        GuiBase.setInterface(new GuiServer());
        FModel.initialize(null, null);

        SpringApplication.run(ServerApplication.class, args);
    }
}