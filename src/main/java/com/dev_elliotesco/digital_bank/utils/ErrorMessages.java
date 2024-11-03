package com.dev_elliotesco.digital_bank.utils;

public class ErrorMessages {

    public static final String ACCOUNT_NUMBER_NOT_EMPTY = "El número de cuenta no puede estar vacío";
    public static final String ACCOUNT_TYPE_NOT_EMPTY = "El tipo de cuenta no puede estar vacío";
    public static final String ACCOUNT_TYPE_NOT_VALID = "El tipo de cuenta debe ser 'ahorros' o 'corriente'";
    public static final String ACCOUNT_TYPE = "ahorros|corriente";
    public static final String TRANSACTION_TYPE_NOT_EMPTY = "El tipo de transacción no puede estar vacío";
    public static final String TRANSACTION_TYPE_NOT_VALID = "El tipo de transacción debe ser 'retiro' o 'depósito'";
    public static final String TRANSACTION_TYPE = "retiro|depósito";
    public static final String TRANSACTION_AMOUNT_NOT_EMPTY = "La cantidad no puede estar vacía";
    public static final String TRANSACTION_AMOUNT_NOT_VALID = "La cantidad debe ser mayor o igual a 0";
    public static final String USER_NAME_NOT_EMPTY = "El nombre no puede estar vacío";
    public static final String USER_EMAIL_NOT_EMPTY = "El correo no puede estar vacío";
    public static final String USER_EMAIL_NOT_VALID = "El correo no es válido";
    public static final String USER_ADRRESS_NOT_EMPTY = "La dirección no puede estar vacía";
    public static final String USER_NOT_FOUND  = "Usuario no encontrado cn el ID: ";
    public static final String ACCOUNT_ALREADY_EXISTS  = "La cuenta ya existe con el numero: ";
}
