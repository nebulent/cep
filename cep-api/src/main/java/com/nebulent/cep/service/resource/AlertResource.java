package com.nebulent.cep.service.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nebulent.schema.software.cep.types._1.Alert;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.nebulent.cep.service.resource.response.StatusResponse;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/alerts")
public interface AlertResource {

	@Descriptions({
        @Description(value = "Creates alert", target = DocTarget.METHOD),
        @Description(value = "Returns created alert", target = DocTarget.RETURN),
        @Description(value = "Alert alert", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/alerts", target = DocTarget.RESOURCE)
    })
    @POST
    Alert createAlert(@Description("Alert alert") Alert alert);
	
	@Descriptions({
        @Description(value = "Gets list of all alerts", target = DocTarget.METHOD),
        @Description(value = "Returns list of existing alerts sorted by date descending", target = DocTarget.RETURN),
        @Description(value = "", target = DocTarget.REQUEST),
        @Description(value = "GET http://{host}/cep/alerts", target = DocTarget.RESOURCE)
    })
    @GET
    List<Alert> getAlerts();

	@Descriptions({
        @Description(value = "Gets alert by alertId", target = DocTarget.METHOD),
        @Description(value = "Returns found alert", target = DocTarget.RETURN),
        @Description(value = "Long alertId", target = DocTarget.REQUEST),
        @Description(value = "GET http://{host}/cep/alerts/{alertId}", target = DocTarget.RESOURCE)
    })
    @GET
    @Path("{alertId}")
    Alert getAlert(@Description("Long alertId") @PathParam("alertId") Long alertId);

	@Descriptions({
        @Description(value = "Updates alert by alertId", target = DocTarget.METHOD),
        @Description(value = "Returns updated alert", target = DocTarget.RETURN),
        @Description(value = "Long alertId, Alert Alert", target = DocTarget.REQUEST),
        @Description(value = "PUT http://{host}/cep/alerts/{alertId}", target = DocTarget.RESOURCE)
    })
    @PUT
    @Path("{alertId}")
	Alert updateAlert(@Description("Long alertId") @PathParam("alertId") Long alertId, @Description("Alert alert") Alert alert);
	
	@Descriptions({
        @Description(value = "Removes alert by alertId", target = DocTarget.METHOD),
        @Description(value = "Returns StatusResponse", target = DocTarget.RETURN),
        @Description(value = "Long alertId", target = DocTarget.REQUEST),
        @Description(value = "DELETE http://{host}/cep/alerts/{alertId}", target = DocTarget.RESOURCE)
    })
    @DELETE
    @Path("{alertId}")
    StatusResponse removeAlert(@Description("Long alertId") @PathParam("alertId") Long alertId);
}
