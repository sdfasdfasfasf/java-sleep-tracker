package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;

public class TotalSessionsFunction implements SleepAnalysisFunction {
    private static final String RESULT_DESCRIPTION = "Общее количество сессий сна";

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        long total = sessions.size();
        return new SleepAnalysisResult(RESULT_DESCRIPTION, total);
    }
}
