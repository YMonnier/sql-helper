package com.ymonnier.sql.help.service.fatory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Project SqlHelper.
 * Package com.ymonnier.sql.help.service.
 * File EntityManagerFactory.java.
 * Created by Ysee on 15/03/2017 - 19:43.
 * www.yseemonnier.com
 * https://github.com/YMonnier
 */
public class EntityManagerFactory {
    private static javax.persistence.EntityManagerFactory managerFactory;
    private static final Queue<EntityManager> entityManagers = new LinkedList<>();

    private static final int numberOfInitialConnections = 6;

    private static String persistenceName = "";


    private static final String PERSISTENCE_FILE_NAME = "META-INF/persistence.xml";
    private static final String PERSISTENCE_ELEM = "persistence-unit";
    private static final String NAME_ATTR = "name";

/*
    static {
        class PersistenceReader extends DefaultHandler {
            @Override
            public void startElement(String s, String s1, String s2, Attributes attributes) throws SAXException {
                if (s2.equals(PERSISTENCE_ELEM))
                    persistenceName = attributes.getValue(NAME_ATTR);
            }
        }

        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            PersistenceReader handler = new PersistenceReader();
            xr.setContentHandler(handler);
            xr.setErrorHandler(handler);


            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            FileReader r = new FileReader(classloader
                    .getResource(PERSISTENCE_FILE_NAME)
                    .getPath());
            xr.parse(new InputSource(r));
            managerFactory = Persistence.createEntityManagerFactory(persistenceName);
            for (int i = 0; i < numberOfInitialConnections; i++) {
                entityManagers.add(managerFactory.createEntityManager());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
    public static void init(String persistenceName) {
        managerFactory = Persistence.createEntityManagerFactory(persistenceName);
        for (int i = 0; i < numberOfInitialConnections; i++) {
            entityManagers.add(managerFactory.createEntityManager());
        }
    }

    /**
     * Get an entity manager connection.
     * @return a connection.
     */
    public static synchronized EntityManager get() {
        EntityManager entityManager = null;

        if (entityManagers.isEmpty()) {
            entityManager = managerFactory.createEntityManager();
        } else {
            entityManager = entityManagers.remove();
        }
        return entityManager;
    }

    /**
     * Release connection
     * @param entityManager specific connection.
     */
    public static synchronized void release(EntityManager entityManager) {
        if (entityManagers.size() < numberOfInitialConnections) {
            entityManagers.add(managerFactory.createEntityManager());
        } else {
            entityManager.close();
        }
    }
}
