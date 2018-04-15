package ru.mezgin.tracker.web;

import org.junit.Before;
import org.junit.Test;
import ru.mezgin.tracker.model.Status;
import ru.mezgin.tracker.repository.JdbcStatusRepositoryImpl;
import ru.mezgin.tracker.service.ConnectionFactoryImpl;
import ru.mezgin.tracker.util.CreateTableAndPopulateDb;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The class TimeControllerTest.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public class TimeControllerTest {

    /**
     * The connection.
     */
    private final Connection conn = ConnectionFactoryImpl.INSTANCE.getConnection();

    /**
     * Run before any tests.
     */
    @Before
    public void before() {
        CreateTableAndPopulateDb create = new CreateTableAndPopulateDb(this.conn);
        create.populateDB();
    }

    /**
     * Returns status for each action.
     *
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    @Test
    public void whenGetActionComeOrOutThenReturnEachStatus() throws IOException, ServletException {
        TimeController tc = new TimeController();
        JdbcStatusRepositoryImpl repository = new JdbcStatusRepositoryImpl(this.conn);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("action")).thenReturn("come");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(100002);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        tc.doGet(request, response);
        verify(session).getAttribute("userId");
        Status result = repository.getLastStatusOnCondition(Status.GET_LAST_ACTION_USER, 100002);
        writer.flush();
        assertTrue(result.isStartNewWorkDay());
        assertTrue(stringWriter.toString().contains("true"));

        when(request.getParameter("action")).thenReturn("out");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(100002);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        tc.doGet(request, response);
        result = repository.getLastStatusOnCondition(Status.GET_LAST_ACTION_USER, 100002);
        writer.flush();
        assertThat(result.getName(), is("Ушёл"));
        assertTrue(stringWriter.toString().contains("true"));
    }

    /**
     * Returns status for each action.
     *
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    @Test
    public void whenGetActionWorkedThenReturnTimeWorked() throws IOException, ServletException {
        TimeController tc = new TimeController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("action")).thenReturn("worked");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userId")).thenReturn(100002);
        when(response.getOutputStream()).thenReturn(outputStream);

        tc.doGet(request, response);
        verify(session).getAttribute("userId");
    }
}