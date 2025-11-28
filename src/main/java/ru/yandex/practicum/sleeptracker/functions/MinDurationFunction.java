package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class MinDurationFunction implements SleepAnalysisFunction {
    private static final String RESULT_DESCRIPTION = "Минимальная продолжительность сессии (минут)";
    private static final long DEFAULT_MIN_DURATION = 0L;

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long minDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationInMinutes)
                .min()
                .orElse(DEFAULT_MIN_DURATION);

        return new SleepAnalysisResult(RESULT_DESCRIPTION, minDuration);
    }
}
