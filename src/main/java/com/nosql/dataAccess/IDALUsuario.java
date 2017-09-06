package com.nosql.dataAccess;

import com.nosql.entidades.Usuario;

import java.util.List;


public interface IDALUsuario {

    boolean addUsuario(Usuario usu);
    boolean updateUsuario(Usuario usu);
    boolean deleteUsuario(String username);
    Usuario getUsuario(String username);
    List<Usuario> getAllUsuarios();

}
