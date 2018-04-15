package ru.mezgin.tracker.util;

import org.junit.Test;
import ru.mezgin.tracker.model.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * The class CalculationTimeWorkedTest.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public class CalculationTimeWorkedTest {

    /**
     * Test method calculate when list is not empty.
     */
    @Test
    public void whenGetListStatusThenReturnTimeWorked() {
        Status startDay = new Status(null, "Пришёл", LocalDateTime.of(2018, 04, 01, 10, 00, 00),
                true, false);
        Status out = new Status(null, "Пришёл", LocalDateTime.of(2018, 04, 01, 10, 30, 30),
                true, false);
        Status come = new Status(null, "Пришёл", LocalDateTime.of(2018, 04, 01, 11, 00, 00),
                true, false);
        Status worked = new Status(null, "Пришёл", LocalDateTime.of(2018, 04, 01, 12, 00, 15),
                true, false);

        List<Status> list = new ArrayList<>();
        list.add(worked);
        list.add(come);
        list.add(out);
        list.add(startDay);

        String result = CalculationTimeWorked.calculate(list);
        assertThat(result, is("01:30:45"));
    }

    /**
     * Test method calculate when list is == null.
     */
    @Test
    public void whenGetListEqualsNullThenReturnEmptyString() {
        List<Status> list = null;

        String result = CalculationTimeWorked.calculate(list);
        assertThat(result, is(""));
    }
}