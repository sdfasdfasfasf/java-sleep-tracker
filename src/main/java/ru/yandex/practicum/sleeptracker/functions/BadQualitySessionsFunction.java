package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepQuality;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class BadQualitySessionsFunction implements SleepAnalysisFunction {
    private static final String RESULT_DESCRIPTION = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long badQualityCount = sessions.stream()
                .filter(session -> session.getQuality() == SleepQuality.BAD)
                .count();

        return new SleepAnalysisResult(RESULT_DESCRIPTION, badQualityCount);
    }
}
