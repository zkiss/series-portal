package hu.bme.viaum105.service;

public enum ErrorType {
    /**
     * A felhasználói név foglalt
     */
    LOGINNAME_IS_IN_USE, //
    /**
     * Sikertelen lookup
     */
    NAMING_EXCEPTION, //
    /**
     * Sikertelen bejelentkezés
     */
    LOGIN_ERROR, //
    /**
     * Hibás paraméter
     */
    ILLEGAL_ARGUMENT, //

    ;

}
