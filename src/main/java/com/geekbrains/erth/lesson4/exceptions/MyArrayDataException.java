package com.geekbrains.erth.lesson4.exceptions;

public class MyArrayDataException extends RuntimeException {

    public MyArrayDataException(int row, int column) {
        super("Данные в ячейке " + row + "х" + column + " некорректны");
    }
    
}
