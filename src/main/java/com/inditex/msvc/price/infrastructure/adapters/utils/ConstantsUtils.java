package com.inditex.msvc.price.infrastructure.adapters.utils;

public final class ConstantsUtils {
    private ConstantsUtils() {
    }

    public static final String FORMAT_DATETIME = "yyyy-MM-dd-HH:mm:ss";
    public static final String NOT_FOUND = "No se encontro un registro para los parametros enviados. Id %s";
    public static final String UNEXPECTED_ERROR = "Se produjo un error inesperado, " +
            "por favor comuniquese con el administrador del sistema.";
    public static final String EMPTY_OR_NULL = "No se puede registrar un valor vacio o nulo.";
    public static final String PRICE_AMOUNT_INVALID = "Valor del precio invalido: ";
    public static final String DATE_NOT_NULL = "La fecha no puede ser nula.";
    public static final String PRODUCT_ID_NOT_NULL = "El id del producto no puede ser nulo.";
    public static final String BRAND_ID_NOT_NULL = "El id de la marca no puede ser nulo.";

}
