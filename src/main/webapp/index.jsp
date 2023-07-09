<%@ page import="com.tictactoe.Sign" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tic-Tac-Toe</title>
    <link href="static/main.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="<c:url value="/static/jquery-3.6.0.min.js"/>"></script>
</head>
<body>
<h1>Tic-Tac-Toe</h1>

<!-- обавим функцию, которая умеет оправлять POST запрос на сервер. Функцию сделаем синхронной
, и когда придет ответ с сервера – перезагрузит текущую страницу.-->
<script>
    function restart() {
        $.ajax({
            url: '/restart',
            type: 'POST',
            contentType: 'application/json;charset=UTF-8',
            async: false,
            success: function () {
                location.reload();
            }
        });
    }
</script>

<table>
    <tr>
        <td onclick="window.location='/logic?click=0'">${data.get(0).getSign()}</td> <!--  здесь и далее - ждёт событие "клик по ячейке" по которому дложен отработать код в сервлете LogicServlet-->
        <td onclick="window.location='/logic?click=1'">${data.get(1).getSign()}</td> <!--  теперь это работает так в по клику в сервлете LogicServlet  в map Field по ключу. который суть значения индекса ячейки в ставляется Sign "крестик" -->
        <td onclick="window.location='/logic?click=2'">${data.get(2).getSign()}</td> <!-- а потом значения из этой Map после упорядочивания записываются в спиок data (т.е нулевой ячейке списка будет соответствовать значения из Map по ключу 0) -->
    </tr>                                                                            <!-- а getSign() возвращает энум sign из списка date хранящийся на позиции c номером индекс -->
    <tr>
        <td onclick="window.location='/logic?click=3'">${data.get(3).getSign()}</td>
        <td onclick="window.location='/logic?click=4'">${data.get(4).getSign()}</td>
        <td onclick="window.location='/logic?click=5'">${data.get(5).getSign()}</td>
    </tr>
    <tr>
        <td onclick="window.location='/logic?click=6'">${data.get(6).getSign()}</td>
        <td onclick="window.location='/logic?click=7'">${data.get(7).getSign()}</td>
        <td onclick="window.location='/logic?click=8'">${data.get(8).getSign()}</td>
    </tr>
</table>
<hr>
<c:set var="CROSSES" value="<%=Sign.CROSS%>"/>
<c:set var="NOUGHTS" value="<%=Sign.NOUGHT%>"/>
<c:if test="${winner ==CROSSES}">
    <h1>CROSSES WIN</h1>
    <button onclick="restart()">Start again</button>
</c:if>
<c:if test="${winner == NOUGHTS}">
    <h1>NOUGHTS WIN</h1>
    <button onclick="restart()">Start again</button>
</c:if>
<c:if test="${draw}"><!-- если ничья -->
    <h1>IT'S A DRAW</h1><!-- выводим IT'S A DRAW -->
    <br> <!-- раздел -->
    <button onclick="restart()">Start again</button><!-- делаем кнопку Start again по которой рестартим игру, функция сверху в script -->
</c:if>
</body>
</html>