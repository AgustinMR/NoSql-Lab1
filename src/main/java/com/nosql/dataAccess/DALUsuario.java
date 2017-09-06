package com.nosql.dataAccess;

import com.nosql.entidades.Usuario;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import java.util.ArrayList;
import java.util.List;

public class DALUsuario implements IDALUsuario {

    public DALUsuario() {

    }

    @Override
    public boolean addUsuario(Usuario usu) {
        ODatabase db = null;
        try {
            ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
            db = database.activateOnCurrentThread().open("admin", "admin");
            ODocument doc = new ODocument("Usuario");
            doc.field("nombre", usu.getNombre());
            doc.field("apellido", usu.getApellido());
            doc.field("email", usu.getEmail());
            doc.field("password", usu.getPassword());
            doc.field("username", usu.getUsername());

            doc.save();
            db.commit();
            db.close();
            return true;
        } catch (Exception ex) {
            db.close();
            return false;
        }
    }

    @Override
    public boolean updateUsuario(Usuario usu) {
        ODatabase db = null;
        try {
            ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
            db = database.activateOnCurrentThread().open("admin", "admin");
            ODocument u = getUsuario(db, usu.getUsername());

            u.field("nombre", usu.getNombre());
            u.field("apellido", usu.getApellido());
            u.field("email", usu.getEmail());
            u.field("password", usu.getPassword());
            u.field("username", usu.getUsername());

            u.save();
            db.commit();
            db.close();
            return true;
        } catch (Exception ex) {
            db.close();
            return false;
        }
    }

    @Override
    public boolean deleteUsuario(String username) {
        ODatabase db = null;
        try {
            ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
            db = database.activateOnCurrentThread().open("admin", "admin");
            ODocument u = getUsuario(db, username);
            u.delete();
            db.commit();
            db.close();
            return true;
        } catch (Exception ex) {
            db.close();
            return false;
        }
    }

    @Override
    public Usuario getUsuario(String username) {
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
        ODatabase db = database.activateOnCurrentThread().open("admin", "admin");
        ODocument od = (ODocument) db.query(new OSQLSynchQuery("SELECT FROM Usuario WHERE username=" + username)).get(0);
        return new Usuario(od.field("nombre"), od.field("apellido"), od.field("email"), od.field("password"), od.field("rid"));
    }
    
    private ODocument getUsuario(ODatabase db, String username) {
        return (ODocument) db.query(new OSQLSynchQuery("SELECT FROM Usuario WHERE username=" + username)).get(0);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        List<Usuario> ret = new ArrayList<>();
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
        ODatabase db = database.activateOnCurrentThread().open("admin", "admin");
        for (ODocument od : database.browseClass("Usaurio")) {
            //ret.add(u.toJSON());
            ret.add(new Usuario(od.field("nombre"), od.field("apellido"), od.field("email"), od.field("password"), od.field("rid")));
        }
        db.close();
        return ret;
    }

}
