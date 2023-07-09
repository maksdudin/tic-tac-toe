package com.tictactoe;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RestartServlet",value = "/restart")
public class RestartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();//удаляет из сессии все объекты
        response.sendRedirect("/start");//перезапускает сервлет старт
    }
}
//После победы появится кнопка “Start again”. После клика по ней – поле полностью очистится, и игра начнется сначала.
//основная логика написана в <script></script>   и body файла index.jsp