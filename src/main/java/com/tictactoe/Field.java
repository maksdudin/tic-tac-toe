package com.tictactoe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Field {
    private final Map<Integer, Sign> field;

    public Field() {
        field = new HashMap<>();
        field.put(0, Sign.EMPTY);
        field.put(1, Sign.EMPTY);
        field.put(2, Sign.EMPTY);
        field.put(3, Sign.EMPTY);
        field.put(4, Sign.EMPTY);
        field.put(5, Sign.EMPTY);
        field.put(6, Sign.EMPTY);
        field.put(7, Sign.EMPTY);
        field.put(8, Sign.EMPTY);
    }

    public Map<Integer, Sign> getField() {
        return field;
    }
    // а вот этот метод организует стрим из map field выбирая вней по фильтру только пустые элементы
    // и возвращает первое попавшийся индекс пустой ячейки, а если их нет, то значение -1
    public int getEmptyFieldIndex() {
        return field.entrySet().stream()
                .filter(e -> e.getValue() == Sign.EMPTY)
                .map(Map.Entry::getKey)
                .findFirst().orElse(-1);
    }

    // выдаст список состоящий из значений карты предварительно отсортированных по ключам(а ключи у нас индексы ячеек)
    public List<Sign> getFieldData() {
        return field.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
   //метод проверяет нет трёх крестиков/ноликов в ряд
    public Sign checkWin() {
        // в списке winPossibilitiesзабиты все возможные варианты рядов идущих подряд крестиков/ноликов
        List<List<Integer>> winPossibilities = List.of(
                List.of(0, 1, 2),
                List.of(3, 4, 5),
                List.of(6, 7, 8),
                List.of(0, 3, 6),
                List.of(1, 4, 7),
                List.of(2, 5, 8),
                List.of(0, 4, 8),
                List.of(2, 4, 6)
        );
        // а вот что тут?
        for (List<Integer> winPossibility : winPossibilities) {// пробегвем по всему списку списков
            if (field.get(winPossibility.get(0)) == field.get(winPossibility.get(1))// и проеряем что значение полей по указанным индексам равны друг другу
                && field.get(winPossibility.get(0)) == field.get(winPossibility.get(2))) {
                return field.get(winPossibility.get(0));//возвращаем значение которое замостило весь ряд
            }
        }
        return Sign.EMPTY;//а если нет заполненых рядов метод возвращает "пустоту"
    }
}