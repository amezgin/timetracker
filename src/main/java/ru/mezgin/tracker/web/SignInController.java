package ru.mezgin.tracker.web;

import ru.mezgin.tracker.model.User;
import ru.mezgin.tracker.repository.JdbcUserRepositoryImpl;
import ru.mezgin.tracker.service.ConnectionFactory;
import ru.mezgin.tracker.service.ConnectionFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * The class SigninController.
 *
 * @author Alexander Mezgin
 * @version 1.1
 * @since 13.04.2018
 */
public class SignInController extends HttpServlet {

    /**
     * The connection factory.
     */
    private final ConnectionFactory conFactory = ConnectionFactoryImpl.INSTANCE;

    @Override
    public void destroy() {
        conFactory.closeConnectionsPool();
        super.destroy();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("loginView.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        JdbcUserRepositoryImpl users = null;
        Connection conn = conFactory.getConnection();
        users = new JdbcUserRepositoryImpl(conn);
        if (users.isCredentional(login, password)) {
            HttpSession session = req.getSession();
            User user = users.getByLogin(login);
            session.setAttribute("userId", user.getId());
            session.setAttribute("user", user);
            if ("ADMIN".equals(user.getRole().getName())) {
                resp.getWriter().write("./adminView.html");
            } else if ("USER".equals(user.getRole().getName())) {
                resp.getWriter().write("./userView.html");
            }
        } else {
            resp.getWriter().write("false");
        }
    }
}