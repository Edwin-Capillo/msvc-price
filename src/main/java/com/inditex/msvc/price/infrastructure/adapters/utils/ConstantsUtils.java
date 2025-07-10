package com.inditex.msvc.price.infrastructure.adapters.utils;

public final class ConstantsUtils {
    private ConstantsUtils() {
    }

    public static final String FORMAT_DATETIME = "yyyy-MM-dd-HH:mm:ss";
    public static final String NOT_FOUND = "No se encontro un registro para los parametros enviados. Id %s";
    public static final String NOT_NULL_VARIABLE = "Ninguna variable a consultar debe ser vacio.";
    public static final String UNEXPECTED_ERROR = "Se produjo un error inesperado, " +
            "por favor comuniquese con el administrador del sistema.";
    public static final String EMPTY_OR_NULL = "No se puede registrar un valor vacio o nulo.";
    public static final String PRICE_AMOUNT_INVALID = "Valor del precio invalido: ";
}
