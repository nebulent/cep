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
import nebulent.schema.software.cep.types._1.Monitor;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.nebulent.cep.service.resource.response.StatusResponse;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/monitors")
public interface MonitorResource {

	@Descriptions({
        @Description(value = "Gets list of all monitors", target = DocTarget.METHOD),
        @Description(value = "Returns list of existing monitors", target = DocTarget.RETURN),
        @Description(value = "", target = DocTarget.REQUEST),
        @Description(value = "GET http://{host}/cep/monitors", target = DocTarget.RESOURCE)
    })
    @GET
    List<Monitor> getMonitors();

	@Descriptions({
        @Description(value = "Gets monitor by monitorId", target = DocTarget.METHOD),
        @Description(value = "Returns found monitor", target = DocTarget.RETURN),
        @Description(value = "Long monitorId", target = DocTarget.REQUEST),
        @Description(value = "GET http://{host}/cep/monitors/{monitorId}", target = DocTarget.RESOURCE)
    })
    @GET
    @Path("{monitorId}")
    Monitor getMonitor(@Description("Long monitorId") @PathParam("monitorId") Long monitorId);

	@Descriptions({
        @Description(value = "Gets list of all alerts generated by monitor", target = DocTarget.METHOD),
        @Description(value = "Returns list of existing alerts by monitorId sorted by date descending", target = DocTarget.RETURN),
        @Description(value = "", target = DocTarget.REQUEST),
        @Description(value = "GET http://{host}/cep/monitors/{monitorId}/alerts", target = DocTarget.RESOURCE)
    })
    @GET
    @Path("{monitorId}/alerts")
    List<Alert> getAlerts(@Description("Long monitorId") @PathParam("monitorId") Long monitorId);
	
	@Descriptions({
        @Description(value = "Updates monitor by monitorId", target = DocTarget.METHOD),
        @Description(value = "Returns updated monitor", target = DocTarget.RETURN),
        @Description(value = "Long monitorId, Monitor monitor", target = DocTarget.REQUEST),
        @Description(value = "PUT http://{host}/cep/monitors/{monitorId}", target = DocTarget.RESOURCE)
    })
    @PUT
    @Path("{monitorId}")
    Monitor updateMonitor(@Description("Long monitorId") @PathParam("monitorId") Long monitorId, @Description("Monitor mointor") Monitor monitor);

	@Descriptions({
        @Description(value = "Creates monitor", target = DocTarget.METHOD),
        @Description(value = "Returns created monitor", target = DocTarget.RETURN),
        @Description(value = "Monitor monitor", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/monitors", target = DocTarget.RESOURCE)
    })
    @POST
    Monitor createMonitor(@Description("Monitor monitor") Monitor monitor);

	@Descriptions({
        @Description(value = "Starts monitor", target = DocTarget.METHOD),
        @Description(value = "Returns StatusResponse", target = DocTarget.RETURN),
        @Description(value = "Long monitorId", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/monitors/{monitorId}/start", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("{monitorId}/start")
	StatusResponse startMonitor(@Description("Long monitorId") @PathParam("monitorId") Long monitorId);
	
	@Descriptions({
        @Description(value = "Stops monitor", target = DocTarget.METHOD),
        @Description(value = "Returns StatusResponse", target = DocTarget.RETURN),
        @Description(value = "Long monitorId", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/monitors/{monitorId}/stop", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("{monitorId}/stop")
	StatusResponse stopMonitor(@Description("Long monitorId") @PathParam("monitorId") Long monitorId);
	
	@Descriptions({
        @Description(value = "Removes monitor by monitorId", target = DocTarget.METHOD),
        @Description(value = "Returns StatusResponse", target = DocTarget.RETURN),
        @Description(value = "Long monitorId", target = DocTarget.REQUEST),
        @Description(value = "DELETE http://{host}/cep/monitors/{monitorId}", target = DocTarget.RESOURCE)
    })
    @DELETE
    @Path("{monitorId}")
    StatusResponse removeMonitor(@Description("Long monitorId") @PathParam("monitorId") Long monitorId);
}