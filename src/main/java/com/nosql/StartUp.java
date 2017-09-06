package com.nosql;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.server.OServer;
import com.orientechnologies.orient.server.OServerMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StartUp {

    public static void main(String args[]) throws Exception {
        SpringApplication.run(StartUp.class, args);
        OServer server = OServerMain.create();
        server.startup(
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
            + "<orient-server>"
            + "<network>"
            + "<protocols>"
            + "<protocol name=\"binary\" implementation=\"com.orientechnologies.orient.server.network.protocol.binary.ONetworkProtocolBinary\"/>"
            + "<protocol name=\"http\" implementation=\"com.orientechnologies.orient.server.network.protocol.http.ONetworkProtocolHttpDb\"/>"
            + "</protocols>"
            + "<listeners>"
            + "<listener ip-address=\"0.0.0.0\" port-range=\"2424-2430\" protocol=\"binary\"/>"
            + "<listener ip-address=\"0.0.0.0\" port-range=\"2480-2490\" protocol=\"http\"/>"
            + "</listeners>"
            + "</network>"
            + "<users>"
            + "<user name=\"root\" password=\"root\" resources=\"*\"/>"
            + "</users>"
            + "<properties>"
            + "<entry name=\"orientdb.www.path\" value=\"C:/work/dev/orientechnologies/orientdb/releases/1.0rc1-SNAPSHOT/www/\"/>"
            + "<entry name=\"orientdb.config.file\" value=\"C:/work/dev/orientechnologies/orientdb/releases/1.0rc1-SNAPSHOT/config/orientdb-server-config.xml\"/>"
            + "<entry name=\"server.cache.staticResources\" value=\"false\"/>"
            + "<entry name=\"log.console.level\" value=\"info\"/>"
            + "<entry name=\"log.file.level\" value=\"fine\"/>"
            //The following is required to eliminate an error or warning "Error on resolving property: ORIENTDB_HOME"
            + "<entry name=\"plugin.dynamic\" value=\"false\"/>"
            + "</properties>" + "</orient-server>");
        server.activate();
        ODatabaseDocumentTx db = new ODatabaseDocumentTx("plocal:/databases/lab1nosql");
        if (!db.exists()) db.create();
        if (db.isClosed()) db.open("admin", "admin");
        if (!db.getMetadata().getSchema().existsClass("Usuario")) db.getMetadata().getSchema().createClass("Usuario");
    }

}
