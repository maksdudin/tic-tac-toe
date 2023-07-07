package com.tictactoe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

//сессия будет храниться на сервере, а клиент получит идентификатор сессии в куке с именем “JSESSIONID”.
// Но сессию не нужно создавать каждый раз, а только в начале игры.
@WebServlet(name = "InitServlet",value = "/start")
public class InitServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        //создание новой сессии
        HttpSession curenSesion = request.getSession(true);
        Field field = new Field();
        Map<Integer,Sign> fileData = field.getField();

        //получение списка значений полей
        List<Sign> data = field.getFieldData();

        // добавление в сесию параметров поля (нужно будет для хранения между запросами)
        curenSesion.setAttribute("field",field);
        // добавление в сесию значений поля отсортированный по индексу (сортируется в getFieldData()) для  отрисовки
        // крестиков и ноликов
        curenSesion.setAttribute("data",data);

        //перенапрвление запроса   на страницу index.jsp через сервер
         getServletContext().getRequestDispatcher("/index.jsp").forward(request,response);

         // стартовую страницу в томкете нужно сменить на http://localhost:8080/start

    }
}
