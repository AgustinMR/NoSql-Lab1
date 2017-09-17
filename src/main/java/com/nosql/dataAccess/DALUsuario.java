package com.nosql.dataAccess;

import com.nosql.entidades.Usuario;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
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
            /*int recordsUpdated = db.command(
                    new OCommandSQL("UPDATE Usuario SET username = ?, email = ?, nombre = ?, apellido = ?, password = ? WHERE @rid = ?")).
                    execute(usu.getUsername(), usu.getEmail(), usu.getNombre(), usu.getApellido(), usu.getPassword(), usu.getRid());*/

            ODocument doc = getUsuario(db, usu.getRid());
            if(doc != null){
                doc.field("username", usu.getUsername());
                doc.field("email", usu.getEmail());
                doc.field("nombre", usu.getNombre());
                doc.field("apellido", usu.getApellido());
                doc.field("password", usu.getPassword());
                doc.save();
            }
            //db.commit();
            db.close();
            return true;
            //return recordsUpdated == 1;
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
            ODocument u = ((List<ODocument>)db.command(new OSQLSynchQuery<ODocument>("SELECT FROM Usuario WHERE username = ?")).execute(username)).get(0);
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
        OSQLSynchQuery<ODocument> query = new OSQLSynchQuery<ODocument>("SELECT FROM Usuario WHERE username = ?");
        List<ODocument> result = database.command(query).execute(username);
        ODocument od = result.get(0);
        return new Usuario(od.field("nombre"), od.field("apellido"), od.field("email"), od.field("password"), od.field("username"), od.field("@rid").toString());
    }
    
    private ODocument getUsuario(ODatabase db, String rid) {
        List<ODocument> result = db.command(new OSQLSynchQuery<ODocument>("SELECT FROM " + rid)).execute();
        return result.get(0);
    }

    public List<Usuario> getAllUsuarios(String filtro, int pagina){
        int skip = ((pagina * 10) - 10);
        List<Usuario> ret = new ArrayList<>();
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
        ODatabase db = database.activateOnCurrentThread().open("admin", "admin");
        List<ODocument> list = database.command(new OSQLSynchQuery("SELECT FROM Usuario WHERE username LIKE ? LIMIT 10 SKIP ?")).execute("%" + filtro + "%", skip);
        for(ODocument od : list){
            ret.add(new Usuario(od.field("nombre"), od.field("apellido"), od.field("email"), od.field("password"), od.field("username"), od.field("@rid").toString()));
        }
        db.close();
        return ret;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        List<Usuario> ret = new ArrayList<>();
        ODatabaseDocumentTx database = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
        ODatabase db = database.activateOnCurrentThread().open("admin", "admin");
        for (ODocument od : database.browseClass("Usuario")) {
            //ret.add(u.toJSON());
            ret.add(new Usuario(od.field("nombre"), od.field("apellido"), od.field("email"), od.field("password"), od.field("username")));
        }
        db.close();
        return ret;
    }

}
