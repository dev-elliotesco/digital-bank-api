package com.dev_elliotesco.digital_bank.utils;

public class ErrorMessages {

    public static final String ACCOUNT_NUMBER_NOT_EMPTY = "El número de cuenta no puede estar vacío";
    public static final String ACCOUNT_TYPE_NOT_EMPTY = "El tipo de cuenta no puede estar vacío";
    public static final String ACCOUNT_TYPE_NOT_VALID = "El tipo de cuenta debe ser 'AHORROS' o 'CORRIENTE'";
    public static final String ACCOUNT_TYPE = "AHORROS|CORRIENTE";
    public static final String TRANSACTION_TYPE_NOT_EMPTY = "El tipo de transacción no puede estar vacío";
    public static final String TRANSACTION_TYPE_NOT_VALID = "El tipo de transacción debe ser 'RETIRO' o 'DEPOSITO'";
    public static final String TRANSACTION_TYPE = "RETIRO|DEPOSITO";
    public static final String TRANSACTION_AMOUNT_NOT_EMPTY = "La cantidad no puede estar vacía";
    public static final String TRANSACTION_AMOUNT_NOT_VALID = "La cantidad debe ser mayor o igual a 0";
    public static final String USER_NAME_NOT_EMPTY = "El nombre no puede estar vacío";
    public static final String USER_EMAIL_NOT_EMPTY = "El correo no puede estar vacío";
    public static final String USER_EMAIL_NOT_VALID = "El correo no es válido";
    public static final String USER_ADRRESS_NOT_EMPTY = "La dirección no puede estar vacía";
    public static final String USER_NOT_FOUND  = "Usuario no encontrado cn el ID: ";
    public static final String ACCOUNT_ALREADY_EXISTS  = "La cuenta ya existe con el numero: ";
    public static final String ACCOUNT_NOT_FOUND = "La cuenta no existe con el numero:";
    public static final String INSUFFICIENT_FUNDS = "No hay saldo suficiente para realizar el retiro.";
}
