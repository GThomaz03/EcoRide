package br.com.global.config;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnection {

    Connection get() throws SQLException;
}
