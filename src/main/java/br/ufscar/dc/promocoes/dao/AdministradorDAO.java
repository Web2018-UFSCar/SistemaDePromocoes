package br.ufscar.dc.promocoes.dao;

import br.ufscar.dc.promocoes.beans.Administrador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdministradorDAO {

    private final static String CRIAR_ADMINISTRADOR_SQL = "INSERT INTO PROMOCOES.ADMINISTRADOR"
            + " (LOGIN, SENHA)"
            + " VALUES (?, ?)";

    private final static String RECUPERAR_ADMINISTRADOR_SQL = "SELECT"
            + " LOGIN, SENHA"
            + " FROM PROMOCOES.ADMINISTRADOR"
            + " WHERE LOGIN = ?";

    DataSource dataSource;

    public AdministradorDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Administrador gravarAdministrador(Administrador administrador) throws SQLException, NamingException {
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(CRIAR_ADMINISTRADOR_SQL);) {
            ps.setString(1, administrador.getLogin());
            ps.setString(2, administrador.getSenha());
            ps.execute();
        }
        return administrador;
    }

    public Administrador recuperarAdministrador(String login) throws SQLException, NamingException {
        Administrador administrador = new Administrador();
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(RECUPERAR_ADMINISTRADOR_SQL)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    administrador.setLogin(rs.getString("LOGIN"));
                    administrador.setSenha(rs.getString("SENHA"));
                }
            }
        }

        return administrador;
    }
}
