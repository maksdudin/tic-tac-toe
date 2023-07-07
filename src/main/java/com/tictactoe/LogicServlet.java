package com.tictactoe;

import javax.servlet.RequestDispatcher;
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

        //достаём оттуда значение sign, ещё раз field - map где всё храниться, метод  getField() формирует из неё
        // (после сортировки по значению ключей/индексов) список значений, из которого мы берем значение текущее sign
        // и присваиванм его переменной  curentSign, которую в дальнейшем будем использовать для проверки
        Sign curentSign=field.getField().get(index);


        // Проверяем, что ячейка, по которой был клик пустая.
        // Если она не пустая отправляем пользователя на ту же страницу без изменений
        // параметров в сессии, русть кливает ещё раз?
        if (Sign.EMPTY != curentSign) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            return;
        }
        // а вот если пустая тогда идём дальше
        // стаивим "крестик" в ячейке по которой кликнул игрок
        // т.е по field.getField() достаём Map из класса Field и записываем в ячейку с ключём имеющем значение index Sign.CROSS
        field.getField().put(index,Sign.CROSS);

        // дальше прграмма должна поставить нолик (по логике метода в первую попавшуюся пустую ячейку, что строго говоря не "мя")

        // получаем пустую я чейку поля
        int emptyFieldSign=field.getEmptyFieldIndex();

        //проверяем, что она вообще есть, если её нет, то метод getEmptyFieldIndex() класса Field вернёт -1,
        // и вот если она есть, то записываем туда 0

        if (emptyFieldSign>=0){
            field.getField().put(emptyFieldSign,Sign.NOUGHT);
        }

        //считываем список значений карты
        List<Sign> data = field.getFieldData();

        //обновляем объект поля и список значков в сессии

        curentSession.setAttribute("field",field);
        curentSession.setAttribute("data",data);

        //  и перенаправляемся на пероначальную стрницу
        response.sendRedirect("/index.jsp");

    }
// достаёт атрибут из сессии сохранённый в не текущий field  и проверяет что  он тот что нужен.
    private Field extractField(HttpSession curentSession) {
        Object fieldAtribute = curentSession.getAttribute("field");
        // если вдруг, досталине то что о чём мечталось кидаем исключение
        if(Field.class!=fieldAtribute.getClass()){
            throw  new RuntimeException("Session is broken, try one more time");
        }
        return (Field) fieldAtribute;
    }

    // метод должен достать индекс из ячейки по которой кликнули.
    private int getSelectedIndex(HttpServletRequest request) {
       // событие клик организует запрос. Из него мы вытащим строку с номером /индексом
        String click=request.getParameter("click");
        // проверим, что в ячейке была цифра если (строка состоит только из цифр) "да", то булин переменная будет true
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        //а далее через тернар выводим значение цифры. если true либо 0 если false ( а если первый индекс 0?)
        return isNumeric ? Integer.parseInt(click):0 ;
    }

//    Метод проверяет, нет ли трех крестиков/ноликов в ряд.
//    Возвращает true/false
    private boolean chekWin(HttpServletResponse response,HttpSession curentSession, Field field) throws IOException {
        Sign winner = field.checkWin();// если есть заполненный ряд, то либо  NOUGHT либо CROSS, а если нет то EMPTY
        if (winner == Sign.CROSS || winner == Sign.NOUGHT) {
            curentSession.setAttribute("winner", winner);// если есть победитель добавляем в сесию новый атрибут победиетль

        //считываем список значений sign
        List<Sign> data = field.getFieldData();

        //обновляем этот список в сессии
        curentSession.setAttribute("data", data);

        //шлём редирект
            response.sendRedirect("/index.jsp");
            return true;
        }
        return  false;
    }

}
