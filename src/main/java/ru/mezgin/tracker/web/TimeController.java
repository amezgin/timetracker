package ru.mezgin.tracker.web;

import ru.mezgin.tracker.model.Status;
import ru.mezgin.tracker.repository.JdbcStatusRepositoryImpl;
import ru.mezgin.tracker.service.ConnectionFactory;
import ru.mezgin.tracker.service.ConnectionFactoryImpl;
import ru.mezgin.tracker.util.CalculationTimeWorked;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The class TimeController.
 *
 * @author Alexander Mezgin
 * @version 1.0
 * @since 13.04.2018
 */
public class TimeController extends HttpServlet {

    /**
     * The connection factory.
     */
    private final ConnectionFactory conFactory = ConnectionFactoryImpl.INSTANCE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Status lastStatusUser = null;
        JdbcStatusRepositoryImpl repository = null;
        List<Status> allTime = null;
        repository = new JdbcStatusRepositoryImpl(conFactory.getConnection());
        lastStatusUser = repository.getLastStatusOnCondition(Status.GET_LAST_ACTION_USER, userId);

        switch (action) {
            case "come":
                if (lastStatusUser == null) {
                    repository.save(new Status(null, "Пришёл", LocalDateTime.now(), true, false), userId);
                } else if (lastStatusUser.isEndWorkDay()) {
                    repository.save(new Status(null, "Пришёл", LocalDateTime.now(), true, false), userId);
                } else if (lastStatusUser.getName().equals("Ушёл")) {
                    repository.save(new Status(null, "Пришёл", LocalDateTime.now(), false, false), userId);
                }
                resp.getWriter().write("true");
                break;
            case "out":
                if (lastStatusUser != null && lastStatusUser.getName().equals("Пришёл")) {
                    repository.save(new Status(null, "Ушёл", LocalDateTime.now(), false, false), userId);
                }
                resp.getWriter().write("true");
                break;
            case "worked":
                if (lastStatusUser != null && !lastStatusUser.getName().equals("Отработал")) {
                    repository.save(new Status(null, "Отработал", LocalDateTime.now(), false, true), userId);
                    allTime = repository.getBetween(repository.getLastStatusOnCondition(Status.GET_LAST_START_WORK_DAY, userId).getDateTime(),
                            repository.getLastStatusOnCondition(Status.GET_LAST_END_WORK_DAY, userId).getDateTime(), userId);
                    String timeWorked = CalculationTimeWorked.calculate(allTime);
                    PrintWriter writer = new PrintWriter(resp.getOutputStream());
                    writer.append("{\"timework\":\"");
                    writer.append(timeWorked);
                    writer.append("\"}");
                    writer.flush();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void destroy() {
        conFactory.closeConnectionsPool();
        super.destroy();
    }
}
