package ru.mezgin.tracker.web;

import org.junit.Before;
import org.junit.Test;
import ru.mezgin.tracker.model.Role;
import ru.mezgin.tracker.model.User;
import ru.mezgin.tracker.repository.JdbcRoleRepositoryImpl;
import ru.mezgin.tracker.repository.JdbcUserRepositoryImpl;
import ru.mezgin.tracker.repository.UserRepository;
import ru.mezgin.tracker.service.ConnectionFactoryImpl;
import ru.mezgin.tracker.util.CreateTableAndPopulateDb;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The class AdminControllerTest.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 15.04.2018
 */
public class AdminControllerTest {

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
     * Test doPost.
     *
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    @Test
    public void whenSaveOrUpdateUserThenItSavedOrUpdated() throws IOException, ServletException {
        AdminController ac = new AdminController();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        UserRepository userRepository = new JdbcUserRepositoryImpl(this.conn);
        Role role = new JdbcRoleRepositoryImpl(this.conn).get(100000);
        User user = new User(100004, "user1", "user1", role, true);

        when(request.getParameter("id")).thenReturn("0");
        when(request.getParameter("login")).thenReturn("user1");
        when(request.getParameter("password")).thenReturn("user1");
        when(request.getParameter("role")).thenReturn("100000");
        when(request.getParameter("enabled")).thenReturn("true");

        ac.doPost(request, response);

        User result = userRepository.get(100004);

        assertThat(result, is(user));
    }
}