package ru.mezgin.tracker.web;

import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The class AuthFilterTest.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public class SignOutControllerTest {
    /**
     * This test for controller SignoutController.
     *
     * @throws ServletException servlet exception.
     * @throws IOException      IOException.
     */
    @Test
    public void whenSingOutThenReturnSessionAttributeIsNull() throws ServletException, IOException {
        SignOutController so = new SignOutController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("loginView.html")).thenReturn(dispatcher);

        so.doPost(request, response);
        verify(session).invalidate();
    }
}