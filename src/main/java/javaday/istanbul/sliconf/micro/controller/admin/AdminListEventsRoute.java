package javaday.istanbul.sliconf.micro.controller.admin;

import io.swagger.annotations.*;
import javaday.istanbul.sliconf.micro.model.event.Event;
import javaday.istanbul.sliconf.micro.model.response.ResponseMessage;
import javaday.istanbul.sliconf.micro.service.event.EventRepositoryService;
import javaday.istanbul.sliconf.micro.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Objects;


@Api
@Path("/service/admin/list/events")
@Produces("application/json")
@Component
public class AdminListEventsRoute implements Route {

    private EventRepositoryService eventRepositoryService;

    @Autowired
    public AdminListEventsRoute(EventRepositoryService eventRepositoryService) {
        this.eventRepositoryService = eventRepositoryService;
    }

    @POST
    @ApiOperation(value = "Lists events for admin", nickname = "AdminListEventsRoute")
    @ApiImplicitParams({})
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success", response = ResponseMessage.class), //
            @ApiResponse(code = 400, message = "Invalid input data", response = ResponseMessage.class), //
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessage.class), //
            @ApiResponse(code = 404, message = "User not found", response = ResponseMessage.class) //
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public ResponseMessage handle(@ApiParam(hidden = true) Request request, @ApiParam(hidden = true) Response response) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return getEvents(authentication);
    }

    public ResponseMessage getEvents(Authentication authentication) {

        if (Objects.isNull(authentication)) {
            return new ResponseMessage(false, "You have no authorization to do this!", new Object());
        }

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ROLE_ADMIN))) {
            return new ResponseMessage(false, "You have no authorization to do this!", new Object());
        }

        List<Event> events = eventRepositoryService.findAll();

        return new ResponseMessage(true, "Events listed", events);
    }
}
