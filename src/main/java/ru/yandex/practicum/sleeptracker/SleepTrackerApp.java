package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SleepTrackerApp {
    private final List<SleepAnalysisFunction> analysisFunctions;

    public SleepTrackerApp() {
        this.analysisFunctions = new ArrayList<>();
        initializeFunctions();
    }

    private void initializeFunctions() {
        analysisFunctions.add(new TotalSessionsFunction());
        analysisFunctions.add(new MinDurationFunction());
        analysisFunctions.add(new MaxDurationFunction());
        analysisFunctions.add(new AverageDurationFunction());
        analysisFunctions.add(new BadQualitySessionsFunction());
        analysisFunctions.add(new SleeplessNightsFunction());
        analysisFunctions.add(new ChronotypeFunction());
    }

    public List<SleepingSession> loadSleepSessions(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .filter(line -> !line.trim().isEmpty())
                .map(line -> {
                    String[] parts = line.split(";");

                    if (parts.length != 3) {
                        return null;
                    }

                    SleepingSession session = new SleepingSession(parts[0], parts[1], parts[2]);

                    long duration = session.getDurationInMinutes();

                    if (duration > 24 * 60) {
                        return null;
                    }

                    if (duration <= 0) {
                        return null;
                    }

                    return session;

                })
                .filter(Objects::nonNull)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public void analyzeAndPrintResults(List<SleepingSession> sessions) {
        analysisFunctions.stream()
                .map(function -> function.analyze(sessions))
                .forEach(result -> System.out.println(result));
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Использование: java SleepTrackerApp <путь_к_файлу_с_логом>");
        }

        String filePath = args[0];
        //String filePath = "src/main/resources/sleep_log.txt";
        SleepTrackerApp app = new SleepTrackerApp();

        try {
            List<SleepingSession> sessions = app.loadSleepSessions(filePath);
            System.out.println("=== Анализ сна ===");
            System.out.println("Загружено сессий сна: " + sessions.size());
            System.out.println();

            app.analyzeAndPrintResults(sessions);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при анализе данных: " + e.getMessage());
            e.printStackTrace();
        }
    }
}