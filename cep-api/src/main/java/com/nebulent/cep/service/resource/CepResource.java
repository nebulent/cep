package com.nebulent.cep.service.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nebulent.schema.software.cep.events._1.APIProbeSysLogEvent;
import nebulent.schema.software.cep.events._1.AppServerHighCPUEvent;
import nebulent.schema.software.cep.events._1.DatabaseSlowQueryEvent;
import nebulent.schema.software.cep.events._1.JMXApplicationAlertEvent;
import nebulent.schema.software.cep.events._1.LoginEvent;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.nebulent.cep.service.resource.response.StatusResponse;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/cep")
public interface CepResource {

	/**
	 * @param event
	 * @return
	 */
	Object logAnyEvent(Object event);
	
	@Descriptions({
        @Description(value = "Log DatabaseSlowQueryEvent event in Esper", target = DocTarget.METHOD),
        @Description(value = "Returns acknowledgement", target = DocTarget.RETURN),
        @Description(value = "DatabaseSlowQueryEvent event", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/cep/dbSlowQuery", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("dbSlowQuery")
	StatusResponse log(@Description("DatabaseSlowQueryEvent event") DatabaseSlowQueryEvent event);
	
	@Descriptions({
        @Description(value = "Log LoginEvent event in Esper", target = DocTarget.METHOD),
        @Description(value = "Returns acknowledgement", target = DocTarget.RETURN),
        @Description(value = "LoginEvent event", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/cep/login", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("login")
	StatusResponse log(@Description("LoginEvent event") LoginEvent event);
	
	@Descriptions({
        @Description(value = "Log AppServerHighCPUEvent event in Esper", target = DocTarget.METHOD),
        @Description(value = "Returns acknowledgement", target = DocTarget.RETURN),
        @Description(value = "AppServerHighCPUEvent event", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/cep/highCpu", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("highCpu")
	StatusResponse log(@Description("AppServerHighCPUEvent event") AppServerHighCPUEvent event);
	
	@Descriptions({
        @Description(value = "Log APIProbeSysLogEvent event in Esper", target = DocTarget.METHOD),
        @Description(value = "Returns acknowledgement", target = DocTarget.RETURN),
        @Description(value = "APIProbeSysLogEvent event", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/cep/apiProbe", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("apiProbe")
	StatusResponse log(@Description("APIProbeSysLogEvent event") APIProbeSysLogEvent event);
	
	@Descriptions({
        @Description(value = "Log JMXApplicationAlertEvent event in Esper", target = DocTarget.METHOD),
        @Description(value = "Returns acknowledgement", target = DocTarget.RETURN),
        @Description(value = "JMXApplicationAlertEvent event", target = DocTarget.REQUEST),
        @Description(value = "POST http://{host}/cep/cep/jmxAppAlert", target = DocTarget.RESOURCE)
    })
    @POST
    @Path("jmxAppAlert")
	StatusResponse log(@Description("JMXApplicationAlertEvent event") JMXApplicationAlertEvent event);
}
