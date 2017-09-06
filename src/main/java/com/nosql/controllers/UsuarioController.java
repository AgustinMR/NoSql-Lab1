package com.nosql.controllers;

import com.nosql.entidades.Usuario;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@CrossOrigin
public class UsuarioController {

    @RequestMapping(value = "", method = RequestMethod.POST)
    public boolean create(@RequestParam(name = "username") String username, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "email") String email, @RequestParam(name = "password") String passowrd){
        return true;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public boolean update(@RequestParam(name = "username") String username, @RequestParam(name = "nombre") String nombre, @RequestParam(name = "apellido") String apellido, @RequestParam(name = "email") String email, @RequestParam(name = "password") String passowrd){
        return true;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable(name = "username") String username){
        return true;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public Usuario find(@PathVariable(name = "username") String Username){
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Usuario> findAll(@RequestParam(name = "filtro") String filtro, @RequestParam(name = "pagina") int pagina){
        return null;
    }
}
