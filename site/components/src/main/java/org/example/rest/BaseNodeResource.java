package org.example.rest;

import org.example.beans.NodeDocument;
import org.example.constants.Constants;
import org.example.service.INodeService;
import org.example.service.NodeServiceImpl;
import org.onehippo.cms7.essentials.components.rest.BaseRestResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import javax.jcr.RepositoryException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BaseNodeResource for provide searching and listing of nodes
 * and showing the detail of the node specified by part of URI path
 */
@Path("/api")
@Produces({MediaType.APPLICATION_JSON})
public class BaseNodeResource extends BaseRestResource {

    private static final Logger log = LoggerFactory.getLogger(BaseNodeResource.class);

    private final INodeService nodeService;

    public BaseNodeResource() {
        nodeService = new NodeServiceImpl();
    }

    /**
     * Get list of Nodes of repository root
     * Or List of nodes based on search query
     * @param queryParam search text
     * @return Response entity of list of node document
     */
    @GET
    @Path("/nodes")
    public ResponseEntity<List<NodeDocument>> getNodes(@QueryParam("q") String queryParam) {
        List<NodeDocument> nodeDocuments;
        try {
            if(queryParam != null && !queryParam.isEmpty()) {
                nodeDocuments = nodeService.getNodesFromQuery(queryParam);
            } else {
                nodeDocuments = nodeService.getAllRootNodes();
            }
        } catch (RepositoryException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(nodeDocuments, HttpStatus.OK);
    }

    /**
     * Get node based on uuid or path of node
     * @param identifier is either uuid or path of node
     * @return Response entity of node document
     */
    @GET
    @Path("/nodes/{identifier:.+}")
    public ResponseEntity<NodeDocument> getNodeWithIdentifier(@PathParam("identifier") String identifier) {
        NodeDocument nodeDocument;
        try {
            if(getMatcher(identifier, Constants.UUID_REGEX).find()) {
                nodeDocument = nodeService.getNodesFromUUID(identifier);
            } else if (getMatcher(identifier,Constants.PATH_REGEX).find()) {
                nodeDocument = nodeService.getNodesFromPath(identifier);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (RepositoryException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(nodeDocument, HttpStatus.OK);
    }

    private Matcher getMatcher(String identifier, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(identifier);
    }
}
