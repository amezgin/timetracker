package ru.mezgin.tracker.util;

import ru.mezgin.tracker.model.Status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.ZoneOffset.UTC;

/**
 * The class CalculationTimeWorked.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class CalculationTimeWorked {

    /**
     * Calculate the the elapsed time.
     *
     * @param statusList the list of users statuses.
     * @return the elapsed time
     */
    public static String calculate(List<Status> statusList) {
        StringBuilder result = new StringBuilder();
        if (statusList != null) {
            long startWork = 0;
            long endWork = 0;
            long fullTime = 0;
            int index = 0;
            while (index < statusList.size()) {
                if (statusList.get(index).getName().equals("Отработал") && !statusList.get(index + 1).getName().equals("Ушёл")) {
                    endWork = statusList.get(index).getDateTime().atZone(UTC).toInstant().toEpochMilli();
                    startWork = statusList.get(index + 1).getDateTime().atZone(UTC).toInstant().toEpochMilli();
                    index += 2;
                } else if (statusList.get(index).getName().equals("Отработал") && statusList.get(index + 1).getName().equals("Ушёл")) {
                    endWork = statusList.get(index + 1).getDateTime().atZone(UTC).toInstant().toEpochMilli();
                    startWork = statusList.get(index + 2).getDateTime().atZone(UTC).toInstant().toEpochMilli();
                    index += 3;
                } else {
                    endWork = statusList.get(index).getDateTime().atZone(UTC).toInstant().toEpochMilli();
                    startWork = statusList.get(index + 1).getDateTime().atZone(UTC).toInstant().toEpochMilli();
                    index += 2;
                }
                fullTime += endWork - startWork;
            }
            LocalDateTime time = Instant.ofEpochMilli(fullTime).atZone(UTC).toLocalDateTime();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
            result.append(format.format(time));
        }
        return result.toString();
    }
}
