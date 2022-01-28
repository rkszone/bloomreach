package org.example.exception;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Api exception class for the API application
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

    /**
     * To Response of the exception
     * @param ex Throwable exception
     * @return error response
     */
    public Response toResponse(Throwable ex) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new Error(String.format("Rest Exception Mapper : {0}",ex.getMessage())))
                .build();
    }

}