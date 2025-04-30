import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertiesActionTest {

    private PropertiesAction propertiesAction;
    private Path tempConfigFile;
    private static final int NUMBER_THREADS = 3;

    private static final String CONFIGURATION_PROPERTIES = "configuration.properties";

    @TempDir
    Path tempDir;

    private static final String TEST_KEY = "test.key";

    @BeforeEach
    void setUp() throws IOException {
        //tempConfigFile = tempDir.resolve("configuration.properties");
        propertiesAction = new PropertiesAction();
    }

    @Test
    void testThreads() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_THREADS);
        List<Future<?>> futures = new ArrayList<>();
        AtomicInteger successfulWrites = new AtomicInteger(0);

        // Define a ação que cada thread irá executar
        Runnable updateTask = () -> {

            String newValue = "Updated-by-thread-" + Thread.currentThread().getId();
            propertiesAction.createProperties(TEST_KEY, newValue);

            // Simula uma leitura após a escrita para aumentar a chance de conflito
            Properties loadedProperties = propertiesAction.loadProperties(CONFIGURATION_PROPERTIES);

            if (newValue.equals(loadedProperties.getProperty(TEST_KEY))) {
                System.out.println("Thread " + Thread.currentThread().getId() + " conseguiu gravar e ler valor: " + newValue);
                successfulWrites.incrementAndGet();
            } else {
                System.out.println("Thread " + Thread.currentThread().getId() + " tentou gravar e ler o valor: " + newValue);
            }
        };

        // Dispara o numero de threads simultaneamente
        for (int i = 0; i < NUMBER_THREADS; i++) {
            futures.add(executorService.submit(updateTask));
        }

        // Espera todas as threads terminarem
        executorService.shutdown();
        assertTrue(executorService.awaitTermination(5, TimeUnit.SECONDS), "Timeout ao esperar as threads terminarem.");

        // Pega o resultado final do arquivo
        Properties finalProperties = propertiesAction.loadProperties(CONFIGURATION_PROPERTIES);
        String finalValue = finalProperties.getProperty(TEST_KEY);

        System.out.println("\nResultado final no arquivo da chave de teste: " + finalValue);
        System.out.println("Número de escritas bem sucedidas (valor lido após escrita): " + successfulWrites.get());

    }
}
