package ru.mezgin.tracker.web;

import org.junit.Test;
import ru.mezgin.tracker.model.Role;
import ru.mezgin.tracker.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The class SignInControllerTest.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public class SignInControllerTest {

    /**
     * This test for controller SignInController with user parameters.
     *
     * @throws ServletException servlet exception.
     * @throws IOException      IOException.
     */
    @Test
    public void whenGetUserLoginAndPasswordThenReturnTrue() throws ServletException, IOException {
        SignInController si = new SignInController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);
        User user = new User(null, "user", "user", new Role(null, "ADMIN"), true);

        when(request.getParameter("login")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("user");
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(String.format("%s/user", request.getContextPath()))).thenReturn(dispatcher);
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        si.doPost(request, response);
        verify(session).setAttribute("userId", 100002);
        verify(session).setAttribute("user", user);
        writer.flush();
        assertTrue(stringWriter.toString().contains("./userView.html"));
    }
}