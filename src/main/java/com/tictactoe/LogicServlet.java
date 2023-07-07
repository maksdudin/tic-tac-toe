package com.tictactoe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name="LogicServlet",value = "/logic")
public class LogicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //Получаем текущую сессию, которую мы организовали при старте сервера с помощью InitServlet
        HttpSession curentSession = request.getSession();

        // Порлучаем объект игрового поля из сессии при помощи метода extractField(curentSession), который создадим
        // ниже, объект в сесии сохранился изначально и будет пересохранятся после  каждого действия
        Field field = extractField(curentSession);

        // получаем индекс той яычейки куда нажали
        int index=getSelectedIndex(request);

        // стаивим "крестик" в ячейке по которой кликнул игрок
        // т.е по field.getField() достаём Map из класса Field и записываем в ячейку с ключём имеющем значение index Sign.CROSS
        field.getField().put(index,Sign.CROSS);

        //считываем список значений карты
        List<Sign> data = field.getFieldData();

        //обновляем объект поля и список значков в сессии

        curentSession.setAttribute("field",field);
        curentSession.setAttribute("data",data);

        //  и перенаправляемся на пероначальную стрницу
        response.sendRedirect("/index.jsp");

    }

    private Field extractField(HttpSession curentSession) {
        Object fieldAtribute = curentSession.getAttribute("field");
        // если вдруг, досталине то что о чём мечталось кидаем исключение
        if(Field.class!=fieldAtribute.getClass()){
            throw  new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAtribute;
    }

    // метод дложен достать индекс из ячейки по которой кликнули.
    private int getSelectedIndex(HttpServletRequest request) {
       // событие клик организует запрос. Из него мы вытащим строку с номером /индексом
        String click=request.getParameter("click");
        // проверим, что в ячейке была цифра если (строка состоит только из цифр) "да", то булин переменная будет true
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        //а далее через тернар выводим значение цифры. если true либо 0 если false ( а если первый индекс 0?)
        return isNumeric ? Integer.parseInt(click):0 ;
    }
}
