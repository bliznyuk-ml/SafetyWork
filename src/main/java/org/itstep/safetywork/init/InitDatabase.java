package org.itstep.safetywork.init;

import lombok.RequiredArgsConstructor;
import org.itstep.safetywork.model.Tool;
import org.itstep.safetywork.repository.ToolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {
    private final ToolRepository toolRepository;

    @Override
    public void run(String... args) throws Exception {
        toolRepository.save(new Tool("Болгарка", "DFEG346FG"));
        toolRepository.save(new Tool("Дрель", "GFDGFC987JHGJHG"));
    }
}
