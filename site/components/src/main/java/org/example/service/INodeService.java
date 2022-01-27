package org.example.service;

import org.example.beans.NodeDocument;

import javax.jcr.RepositoryException;
import java.util.List;

/**
 * INodeService
 */
public interface INodeService {

    /**
     * Get all Root Nodes of JCR Repository
     * @return All Root Nodes of JCR Repository
     * @throws RepositoryException will be thrown
     */
    List<NodeDocument> getAllRootNodes() throws RepositoryException;

    /**
     * Get all Nodes of JCR Repository containing search text
     * @param searchText search text
     * @return all Nodes of JCR Repository containing search text
     * @throws RepositoryException will be thrown
     */
    List<NodeDocument> getNodesFromQuery(String searchText) throws RepositoryException;

    /**
     * Get Node of JCR Repository using uuid
     * @param uuid node identifier
     * @return all Nodes of JCR Repository uuid
     * @throws RepositoryException will be thrown
     */
    NodeDocument getNodesFromUUID(String uuid) throws RepositoryException;

    /**
     * Get Node of JCR Repository using path
     * @param path pathof the node
     * @return Node of JCR Repository using path
     * @throws RepositoryException will be thrown
     */
    NodeDocument getNodesFromPath(String path) throws RepositoryException;
}
