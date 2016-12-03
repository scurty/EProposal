/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package endpoints.backend;


import com.example.Proposal;
import com.example.ProposalItem;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.cloud.sql.jdbc.Statement;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.ServletException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.endpoints",
                ownerName = "backend.endpoints",
                packagePath = ""
        )
)
public class MyEndpoint {

    private int id;
    private String nome;
    private String descricao;
    private String senha;
    private String imagepath;
    private String timestamp;

    private int id_prop;
    private int seq;
    private int type;
    private String menu;

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String namep) throws ServletException {

        // store only the first two octets of a users ip address
        String userIp = namep;
        List list = new ArrayList();

        final String createTableSql = "CREATE TABLE IF NOT EXISTS prop ( prop_id INT NOT NULL "
                + "AUTO_INCREMENT, name VARCHAR(46) NOT NULL, descricao VARCHAR(100) NOT NULL, " +
                "senha VARCHAR(50) NOT NULL, imagepath VARCHAR(500) NOT NULL, timestamp DATETIME NOT NULL, "
                + "PRIMARY KEY (prop_id) )";
        final String createVisitSql = "INSERT INTO prop (name, descricao, senha, imagepath, timestamp) VALUES (?, ?, ?, ? ,?)";
        final String selectSql = "SELECT name, descricao, senha, imagepath, timestamp FROM prop ORDER BY timestamp DESC ";
        //+ "LIMIT 10";

        String url;
        if (System
                .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                try {
                    throw new ServletException("Error loading Google JDBC Driver", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }

        try {
            Connection conn = DriverManager.getConnection(url);

            PreparedStatement statementCreateVisit = conn.prepareStatement(createVisitSql);
            conn.createStatement().executeUpdate(createTableSql);
            statementCreateVisit.setString(1, userIp);
            statementCreateVisit.setString(2, userIp);
            statementCreateVisit.setString(3, userIp);
            statementCreateVisit.setString(4, userIp);
            statementCreateVisit.setTimestamp(5, new Timestamp(new Date().getTime()));
            statementCreateVisit.executeUpdate();


            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            // out.print("Last 10 visits:\n");

            Proposal prop = new Proposal();
            while (rs.next()) {

                nome = rs.getString("name");
                descricao = rs.getString("descricao");
                senha = rs.getString("senha");
                imagepath = rs.getString("imagepath");
                timestamp = rs.getString("timestamp");

                prop.setName(nome);
                prop.setDescription(descricao);
                prop.setSenha(senha);
                prop.setImagepath(imagepath);
                prop.setTimestamp(timestamp);
                list.add(new JSONObject(prop));

                //  out.print("Time: " + timeStamp + " Addr: " + savedIp + "\n");
            }
               /* out.print(list.toString());

                resp.setContentType("text/plain");
                resp.getWriter().println(list.toString());*/

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }


        MyBean response = new MyBean();
        response.setData(list.toString());

        return response;
    }

    @ApiMethod(name = "listar")
    public MyBean listar(@Named("name") String name) throws ServletException {

        // store only the first two octets of a users ip address
        String userIp = name;
        List list = new ArrayList();

        final String createTableSql = "CREATE TABLE IF NOT EXISTS prop ( prop_id INT NOT NULL "
                + "AUTO_INCREMENT, name VARCHAR(46) NOT NULL, descricao VARCHAR(200) NOT NULL, " +
                "senha VARCHAR(50) NOT NULL, imagepath VARCHAR(500) NOT NULL, timestamp DATETIME NOT NULL, "
                + "PRIMARY KEY (prop_id) )";
        // final String createVisitSql = "INSERT INTO prop (name, desc, timestamp) VALUES (?, ?,?)";
        final String selectSql = "SELECT prop_id, name, descricao, senha, imagepath, timestamp FROM prop ORDER BY timestamp DESC ";
        //+ "LIMIT 10";

        String url;
        if (System
                .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                try {
                    throw new ServletException("Error loading Google JDBC Driver", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }
        try {
            Connection conn = DriverManager.getConnection(url);

            /* PreparedStatement statementCreateVisit = conn.prepareStatement(createVisitSql)) {
            conn.createStatement().executeUpdate(createTableSql);
            statementCreateVisit.setString(1, userIp);
            statementCreateVisit.setTimestamp(2, new Timestamp(new Date().getTime()));
            statementCreateVisit.executeUpdate();*/

            ResultSet rs = conn.prepareStatement(selectSql).executeQuery();
            // out.print("Last 10 visits:\n");

            Proposal prop = new Proposal();
            while (rs.next()) {

                id = rs.getInt("prop_id");
                nome = rs.getString("name");
                descricao = rs.getString("descricao");
                senha = rs.getString("senha");
                imagepath = rs.getString("imagepath");
                timestamp = rs.getString("timestamp");

                prop.setId(Long.valueOf(id));
                prop.setName(nome);
                prop.setDescription(descricao);
                prop.setSenha(senha);
                prop.setImagepath(imagepath);
                prop.setTimestamp(timestamp);
                list.add(new JSONObject(prop));

                //  out.print("Time: " + timeStamp + " Addr: " + savedIp + "\n");
            }
               /* out.print(list.toString());

                resp.setContentType("text/plain");
                resp.getWriter().println(list.toString());*/

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }


        MyBean response = new MyBean();
        response.setData(list.toString());

        return response;
    }

    //http://stackoverflow.com/questions/17801984/how-to-decode-in-java-a-json-representation-of-google-appengine-text
    @ApiMethod(name = "inserir")
    public MyBean inserir(@Named("name") String name) throws ServletException {

        // store only the first two octets of a users ip address
        JSONObject j = new JSONObject(name);
        nome = j.getString("nome");
        descricao = j.getString("descricao");
        senha = j.getString("senha");
        imagepath = j.getString("imagepath");

        // String userIp = name;
        List list = new ArrayList();

        final String createTableSql = "CREATE TABLE IF NOT EXISTS prop ( prop_id INT NOT NULL "
                + "AUTO_INCREMENT, name VARCHAR(46) NOT NULL, descricao VARCHAR(200) NOT NULL, " +
                "senha VARCHAR(200) NOT NULL, imagepath VARCHAR(500) NOT NULL,  timestamp DATETIME NOT NULL, "
                + "PRIMARY KEY (prop_id) )";
        final String createVisitSql = "INSERT INTO prop (name, descricao, senha, imagepath, timestamp) VALUES (?, ?, ?, ?,?)";
        final String selectSql = "SELECT name, descricao, senha, imagepath, timestamp FROM prop ORDER BY timestamp DESC ";
        //+ "LIMIT 10";

        String url;
        if (System
                .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                try {
                    throw new ServletException("Error loading Google JDBC Driver", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }
        try {
            Connection conn = DriverManager.getConnection(url);

            conn.createStatement().executeUpdate(createTableSql);

            PreparedStatement statementCreateVisit = conn.prepareStatement(createVisitSql, Statement.RETURN_GENERATED_KEYS);
            statementCreateVisit.setString(1, nome);
            statementCreateVisit.setString(2, descricao);
            statementCreateVisit.setString(3, senha);
            statementCreateVisit.setString(4, imagepath);
            statementCreateVisit.setTimestamp(5, new Timestamp(new Date().getTime()));
            statementCreateVisit.executeUpdate();
            ResultSet rs2 = statementCreateVisit.getGeneratedKeys();
            if (rs2.next()) {
                int last_inserted_id = rs2.getInt(1);
                MyBean response = new MyBean();
                response.setData(String.valueOf(last_inserted_id));

                return response;

            }

          /*  try (ResultSet rs = conn.prepareStatement(selectSql).executeQuery()) {
                // out.print("Last 10 visits:\n");

                Proposal prop = new Proposal();
                while (rs.next()) {

                    nome = rs.getString("name");
                    descricao = rs.getString("descricao");
                    senha = rs.getString("senha");
                    imagepath =  rs.getString("imagepath");
                    timestamp = rs.getString("timestamp");

                    prop.setName(nome);
                    prop.setDescription(descricao);
                    prop.setSenha(senha);
                    prop.setImagepath(imagepath);
                    prop.setTimestamp(timestamp);
                    list.add(new JSONObject(prop));

                    //  out.print("Time: " + timeStamp + " Addr: " + savedIp + "\n");
                }
               *//* out.print(list.toString());

                resp.setContentType("text/plain");
                resp.getWriter().println(list.toString());*//*
            }*/
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }

        MyBean response = new MyBean();
        response.setData("nok");

        return response;
    }

    @ApiMethod(name = "listaritem")
    public MyBean listaritem(@Named("name") String name) throws ServletException {

        // store only the first two octets of a users ip address
        String prop_item = name;
        List list = new ArrayList();

        final String createTableSql = "CREATE TABLE IF NOT EXISTS prop_item ( prop_item_id INT NOT NULL "
                + "AUTO_INCREMENT, prop_id INT NOT NULL, seq INT NOT NULL, menu VARCHAR(200) NOT NULL, " +
                "nome VARCHAR(50) NOT NULL, type INT NOT NULL, imagepath VARCHAR(500) NOT NULL, timestamp DATETIME NOT NULL, "
                + "PRIMARY KEY (prop_item_id) )";
        // final String createVisitSql = "INSERT INTO prop (name, desc, timestamp) VALUES (?, ?,?)";
        final String selectSql = "SELECT prop_item_id, prop_id, seq, menu, nome, type, imagepath, timestamp " +
                "FROM prop_item WHERE prop_id = ? ORDER BY seq ASC ";
        //+ "LIMIT 10";

        String url;
        if (System
                .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                try {
                    throw new ServletException("Error loading Google JDBC Driver", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }
        try {
            Connection conn = DriverManager.getConnection(url);

            /* PreparedStatement statementCreateVisit = conn.prepareStatement(createVisitSql)) {
            conn.createStatement().executeUpdate(createTableSql);
            statementCreateVisit.setString(1, userIp);
            statementCreateVisit.setTimestamp(2, new Timestamp(new Date().getTime()));
            statementCreateVisit.executeUpdate();*/

            PreparedStatement statementCreateVisit = conn.prepareStatement(selectSql);
            statementCreateVisit.setString(1, prop_item);

            ResultSet rs = statementCreateVisit.executeQuery();
            // out.print("Last 10 visits:\n");

            ProposalItem propItem = new ProposalItem();
            while (rs.next()) {

                id = rs.getInt("prop_id");
                id_prop = rs.getInt("prop_item_id");
                nome = rs.getString("nome");
                menu = rs.getString("menu");
                seq = rs.getInt("seq");
                type = rs.getInt("type");
                imagepath = rs.getString("imagepath");
                timestamp = rs.getString("timestamp");

                propItem.setId(Long.valueOf(id));
                propItem.setId_prop(Long.valueOf(id_prop));
                propItem.setName(nome);
                propItem.setMenu(menu);
                propItem.setType(type);
                propItem.setSeq(seq);
                propItem.setImagepath(imagepath);
                // propItem.setTimestamp(timestamp);
                list.add(new JSONObject(propItem));

                //  out.print("Time: " + timeStamp + " Addr: " + savedIp + "\n");
            }
               /* out.print(list.toString());

                resp.setContentType("text/plain");
                resp.getWriter().println(list.toString());*/

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }


        MyBean response = new MyBean();
        response.setData(list.toString());

        return response;
    }

    @ApiMethod(name = "inseriritem")
    public MyBean inseriritem(@Named("name") String name) throws ServletException {

        // store only the first two octets of a users ip address
        JSONObject j = new JSONObject(name);
        id_prop = j.getInt("id_prop");
        nome = j.getString("nome");
        menu = j.getString("menu");
        type = j.getInt("type");
        seq = j.getInt("seq");
        imagepath = j.getString("imagepath");

        // String userIp = name;
        List list = new ArrayList();

        final String createTableSql = "CREATE TABLE IF NOT EXISTS prop_item ( prop_item_id INT NOT NULL "
                + "AUTO_INCREMENT, prop_id INT NOT NULL, seq INT NOT NULL, menu VARCHAR(200) NOT NULL, " +
                "nome VARCHAR(50) NOT NULL, type INT NOT NULL, imagepath VARCHAR(500) NOT NULL, timestamp DATETIME NOT NULL, "
                + "PRIMARY KEY (prop_item_id) )";
        final String createVisitSql = "INSERT INTO prop_item (prop_id, seq, menu, nome, type, imagepath, timestamp) VALUES (?, ?, ?, ?,?,?,?)";
        final String selectSql = "SELECT prop_item_id, prop_id, seq, menu, nome, type, imagepath, timestamp FROM prop_item ORDER BY seq ASC ";
        //+ "LIMIT 10";

        String url;
        if (System
                .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                try {
                    throw new ServletException("Error loading Google JDBC Driver", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }
        try {
            Connection conn = DriverManager.getConnection(url);

            conn.createStatement().executeUpdate(createTableSql);

            PreparedStatement statementCreateVisit = conn.prepareStatement(createVisitSql, Statement.RETURN_GENERATED_KEYS);
            statementCreateVisit.setInt(1, id_prop);
            statementCreateVisit.setInt(2, seq);
            statementCreateVisit.setString(3, menu);
            statementCreateVisit.setString(4, nome);
            statementCreateVisit.setInt(5, type);
            statementCreateVisit.setString(6, imagepath);
            statementCreateVisit.setTimestamp(7, new Timestamp(new Date().getTime()));
            statementCreateVisit.executeUpdate();
            ResultSet rs2 = statementCreateVisit.getGeneratedKeys();
            if (rs2.next()) {
                int last_inserted_id = rs2.getInt(1);
                MyBean response = new MyBean();
                // response.setData(String.valueOf(last_inserted_id));
                response.setData(String.valueOf(seq));
                return response;

            }

          /*  try (ResultSet rs = conn.prepareStatement(selectSql).executeQuery()) {
                // out.print("Last 10 visits:\n");

                Proposal prop = new Proposal();
                while (rs.next()) {

                    nome = rs.getString("name");
                    descricao = rs.getString("descricao");
                    senha = rs.getString("senha");
                    imagepath =  rs.getString("imagepath");
                    timestamp = rs.getString("timestamp");

                    prop.setName(nome);
                    prop.setDescription(descricao);
                    prop.setSenha(senha);
                    prop.setImagepath(imagepath);
                    prop.setTimestamp(timestamp);
                    list.add(new JSONObject(prop));

                    //  out.print("Time: " + timeStamp + " Addr: " + savedIp + "\n");
                }
               *//* out.print(list.toString());

                resp.setContentType("text/plain");
                resp.getWriter().println(list.toString());*//*
            }*/
        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }

        MyBean response = new MyBean();
        response.setData("nok");

        return response;
    }

    @ApiMethod(name = "selecionar")
    public MyBean selecionar(@Named("name") String name) throws ServletException {

        // store only the first two octets of a users ip address
        JSONObject j = new JSONObject(name);
        nome = j.getString("nome");
        senha = j.getString("senha");

        // String userIp = name;
        List list = new ArrayList();

        final String createTableSql = "CREATE TABLE IF NOT EXISTS prop ( prop_id INT NOT NULL "
                + "AUTO_INCREMENT, name VARCHAR(46) NOT NULL, descricao VARCHAR(200) NOT NULL, " +
                "senha VARCHAR(200) NOT NULL, imagepath VARCHAR(500) NOT NULL,  timestamp DATETIME NOT NULL, "
                + "PRIMARY KEY (prop_id) )";
        final String selectSql = "SELECT prop_id, name, descricao, imagepath, timestamp FROM prop " +
                "WHERE name = ? AND senha = ? ";

        String url;
        if (System
                .getProperty("com.google.appengine.runtime.version").startsWith("Google App Engine/")) {
            // Check the System properties to determine if we are running on appengine or not
            // Google App Engine sets a few system properties that will reliably be present on a remote
            // instance.
            url = System.getProperty("ae-cloudsql.cloudsql-database-url");
            try {
                // Load the class that provides the new "jdbc:google:mysql://" prefix.
                Class.forName("com.mysql.jdbc.GoogleDriver");
            } catch (ClassNotFoundException e) {
                try {
                    throw new ServletException("Error loading Google JDBC Driver", e);
                } catch (ServletException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            // Set the url with the local MySQL database connection url when running locally
            url = System.getProperty("ae-cloudsql.local-database-url");
        }
        try {
            Connection conn = DriverManager.getConnection(url);

            conn.createStatement().executeUpdate(createTableSql);

            PreparedStatement statementCreateVisit = conn.prepareStatement(selectSql);
            statementCreateVisit.setString(1, nome);
            statementCreateVisit.setString(2, senha);
            // statementCreateVisit.executeUpdate();

            ResultSet rs = statementCreateVisit.executeQuery();

            Proposal prop = new Proposal();
            while (rs.next()) {

                id = rs.getInt("prop_id");
                nome = rs.getString("name");
                descricao = rs.getString("descricao");
                // senha = rs.getString("senha");
                imagepath = rs.getString("imagepath");
                timestamp = rs.getString("timestamp");

                prop.setId(Long.valueOf(id));
                prop.setName(nome);
                prop.setDescription(descricao);
                //  prop.setSenha(senha);
                prop.setImagepath(imagepath);
                prop.setTimestamp(timestamp);
                list.add(new JSONObject(prop));

                //  out.print("Time: " + timeStamp + " Addr: " + savedIp + "\n");
            }
               /* out.print(list.toString());

                resp.setContentType("text/plain");
                resp.getWriter().println(list.toString());*/

        } catch (SQLException e) {
            throw new ServletException("SQL error", e);
        }


        MyBean response = new MyBean();
        response.setData(list.toString());

        return response;
    }
}
