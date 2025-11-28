package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.Chronotype;
import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChronotypeFunction implements SleepAnalysisFunction {
    private static final int OWL_SLEEP_HOUR_THRESHOLD = 23;
    private static final int OWL_WAKE_HOUR_THRESHOLD = 9;
    private static final int LARK_SLEEP_HOUR_THRESHOLD = 22;
    private static final int LARK_WAKE_HOUR_THRESHOLD = 7;

    private static final String RESULT_DESCRIPTION = "Хронотип пользователя";

    private Chronotype classifyNight(SleepingSession session) {
        if (!session.isNightSleep()) {
            return null;
        }

        int sleepHour = session.getSleepStart().getHour();
        int wakeHour = session.getSleepEnd().getHour();

        if (sleepHour >= OWL_SLEEP_HOUR_THRESHOLD && wakeHour >= OWL_WAKE_HOUR_THRESHOLD) {
            return Chronotype.OWL;
        } else if (sleepHour <= LARK_SLEEP_HOUR_THRESHOLD && wakeHour <= LARK_WAKE_HOUR_THRESHOLD) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }

    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        Map<Chronotype, Long> chronotypeCounts = sessions.stream()
                .map(this::classifyNight)
                .filter(chronotype -> chronotype != null)
                .collect(Collectors.groupingBy(
                        chronotype -> chronotype,
                        Collectors.counting()
                ));

        Chronotype userChronotype = determineUserChronotype(chronotypeCounts);
        return new SleepAnalysisResult(RESULT_DESCRIPTION, userChronotype);
    }

    private Chronotype determineUserChronotype(Map<Chronotype, Long> counts) {
        long owlCount = counts.getOrDefault(Chronotype.OWL, 0L);
        long larkCount = counts.getOrDefault(Chronotype.LARK, 0L);
        long doveCount = counts.getOrDefault(Chronotype.DOVE, 0L);

        if (owlCount > larkCount && owlCount > doveCount) {
            return Chronotype.OWL;
        } else if (larkCount > owlCount && larkCount > doveCount) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }
}
