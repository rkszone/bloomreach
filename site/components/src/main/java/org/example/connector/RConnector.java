package org.example.connector;

import org.hippoecm.hst.site.HstServices;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

/**
 * RConnector for creating session with main Repository
 */
public class RConnector {

    /**
     * Get Session for the repository
     * @return Session for the repository
     * @throws RepositoryException will be thrown
     */
    public static Session getSession() throws RepositoryException {
        Repository repository = HstServices.getComponentManager().getComponent(Repository.class.getName());
        return repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
    }

}
