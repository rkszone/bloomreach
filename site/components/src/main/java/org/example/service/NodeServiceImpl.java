package org.example.service;

import org.example.beans.NodeDocument;
import org.example.connector.RConnector;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;
import java.util.List;

public class NodeServiceImpl implements INodeService {

    /**
     * Get all Root Nodes of JCR Repository
     * @return All Root Nodes of JCR Repository
     * @throws RepositoryException will be thrown
     */
    @Override
    public List<NodeDocument> getAllRootNodes() throws RepositoryException {
        List<NodeDocument> nodeDocuments = new ArrayList<>();
        NodeIterator nodeIterator = RConnector.getSession().getRootNode().getNodes();
        populateNodeList(nodeIterator, nodeDocuments);
        return nodeDocuments;
    }

    /**
     * Get all Nodes of JCR Repository containing search text
     * @param searchText search text
     * @return all Nodes of JCR Repository containing search text
     * @throws RepositoryException will be thrown
     */
    @Override
    public List<NodeDocument> getNodesFromQuery(String searchText) throws RepositoryException {
        List<NodeDocument> nodeDocuments = new ArrayList<>();
        Query query = RConnector.getSession().getWorkspace()
                .getQueryManager()
                .createQuery("//*[jcr:contains(.,'"+searchText+"')]",Query.XPATH);
        query.setLimit(10);
        QueryResult queryResult = query.execute();
        NodeIterator nodeIterator = queryResult.getNodes();
        populateNodeList(nodeIterator, nodeDocuments);
        return nodeDocuments;
    }

    /**
     * Get Node of JCR Repository using uuid
     * @param uuid node identifier
     * @return all Nodes of JCR Repository uuid
     * @throws RepositoryException will be thrown
     */
    @Override
    public NodeDocument getNodesFromUUID(String uuid) throws RepositoryException {
        NodeDocument nodeDocument = new NodeDocument();
        Query query = RConnector.getSession().getWorkspace()
                .getQueryManager()
                .createQuery("//*[@jcr:uuid='"+uuid+"']",Query.XPATH);
        query.setLimit(1);
        QueryResult queryResult = query.execute();
        if(queryResult.getNodes().hasNext()) {
            Node node = queryResult.getNodes().nextNode();
            nodeDocument.setId(node.getIdentifier());
            nodeDocument.setName(node.getName());
        }
        return nodeDocument;
    }

    /**
     * Get Node of JCR Repository using path
     * @param path path of the node
     * @return Node of JCR Repository using path
     * @throws RepositoryException will be thrown
     */
    @Override
    public NodeDocument getNodesFromPath(String path) throws RepositoryException {
        NodeDocument nodeDocument = new NodeDocument();
        Node node = RConnector.getSession().getRootNode().getNode(path);
        nodeDocument.setId(node.getIdentifier());
        nodeDocument.setName(node.getName());
        return nodeDocument;
    }


    private void populateNodeList(NodeIterator nodeIterator, List<NodeDocument> nodeDocuments) throws RepositoryException {
        NodeDocument nodeDocument;
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.nextNode();
            if (!node.isNodeType("hippofacnav:facetnavigation")) {
                nodeDocument = new NodeDocument();
                nodeDocument.setId(node.getIdentifier());
                nodeDocument.setName(node.getName());
                nodeDocuments.add(nodeDocument);
            }
        }
    }
}
