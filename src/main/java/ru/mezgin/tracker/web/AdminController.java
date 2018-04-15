package ru.mezgin.tracker.web;

import com.google.gson.Gson;
import ru.mezgin.tracker.model.User;
import ru.mezgin.tracker.repository.JdbcRoleRepositoryImpl;
import ru.mezgin.tracker.repository.JdbcUserRepositoryImpl;
import ru.mezgin.tracker.service.ConnectionFactory;
import ru.mezgin.tracker.service.ConnectionFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

/**
 * The class AdminController.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class AdminController extends HttpServlet {

    /**
     * The connection factory.
     */
    private final ConnectionFactory conFactory = ConnectionFactoryImpl.INSTANCE;

    /**
     * The users storage.
     */
    private JdbcUserRepositoryImpl users;

    /**
     * The roles storage.
     */
    private JdbcRoleRepositoryImpl roles;

    @Override
    public void destroy() {
        conFactory.closeConnectionsPool();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = conFactory.getConnection();
        users = new JdbcUserRepositoryImpl(conn);
        roles = new JdbcRoleRepositoryImpl(conn);
        User user = new User(Integer.parseInt(req.getParameter("id")), req.getParameter("login"), req.getParameter("password"),
                roles.get(Integer.parseInt(req.getParameter("role"))), Boolean.parseBoolean(req.getParameter("enabled")));
        users.save(user);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            Connection conn = conFactory.getConnection();
            users = new JdbcUserRepositoryImpl(conn);
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            String json = new Gson().toJson(users.getAll());
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(json);
            writer.flush();
        } else if (action.equals("search")) {
            Connection conn = conFactory.getConnection();
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            users = new JdbcUserRepositoryImpl(conn);
            User user = users.getByLogin(req.getParameter("searchLogin"));
            String json = new Gson().toJson(user);
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(json);
            writer.flush();
        } else if (action.equals("delete")) {
            Connection conn = conFactory.getConnection();
            Integer id = Integer.parseInt(req.getParameter("id"));
            users = new JdbcUserRepositoryImpl(conn);
            users.delete(id);
            resp.sendRedirect("./adminView.html");
        } else {
            resp.setContentType("text/json");
            resp.setCharacterEncoding("UTF-8");
            User user = null;
            if (action.equals("create")) {
                user = new User(0, null, null, null, false);
            } else {
                Connection conn = conFactory.getConnection();
                Integer id = Integer.parseInt(req.getParameter("id"));
                users = new JdbcUserRepositoryImpl(conn);
                user = users.get(id);
                user.setId(id);
            }
            String userJson = new Gson().toJson(user);
            PrintWriter writer = new PrintWriter(resp.getOutputStream());
            writer.append(userJson);
            writer.flush();
        }
    }
}
