package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.model.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.model.SleepingSession;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SleeplessNightsFunction implements SleepAnalysisFunction {
    @Override
    public SleepAnalysisResult analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0);
        }

        Set<LocalDate> nightsWithSleep = sessions.stream()
                .filter(SleepingSession::isNightSleep)
                .map(session -> {
                    LocalDateTime sleepStart = session.getSleepStart();

                    if (sleepStart.getHour() >= 12) {
                        return sleepStart.toLocalDate().plusDays(1);
                    } else {
                        return sleepStart.toLocalDate();
                    }
                })
                .collect(Collectors.toSet());

        LocalDate startDate = sessions.stream()
                .map(SleepingSession::getSleepStart)
                .map(LocalDateTime::toLocalDate)
                .min(LocalDate::compareTo)
                .get();

        LocalDate endDate = sessions.stream()
                .map(SleepingSession::getSleepEnd)
                .map(LocalDateTime::toLocalDate)
                .max(LocalDate::compareTo)
                .get();

        long totalNights = startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> {
                    LocalDateTime nightStart = LocalDateTime.of(date.minusDays(1), LocalTime.of(0, 0));
                    LocalDateTime nightEnd = LocalDateTime.of(date, LocalTime.of(6, 0));

                    return sessions.stream().anyMatch(session ->
                            session.getSleepStart().isBefore(nightEnd) &&
                                    session.getSleepEnd().isAfter(nightStart)
                    );
                })
                .count();

        long sleeplessNights = totalNights - nightsWithSleep.size();
        return new SleepAnalysisResult("Количество бессонных ночей", sleeplessNights);
    }
}
