package javaday.istanbul.sliconf.micro.controller.event.speaker;

import io.swagger.annotations.*;
import javaday.istanbul.sliconf.micro.model.event.Event;
import javaday.istanbul.sliconf.micro.model.event.Speaker;
import javaday.istanbul.sliconf.micro.model.response.ResponseMessage;
import javaday.istanbul.sliconf.micro.service.event.EventRepositoryService;
import javaday.istanbul.sliconf.micro.specs.SpeakerSpecs;
import javaday.istanbul.sliconf.micro.util.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@Path("/service/events/speaker/create/:event-key")
@Produces("application/json")
@Component
public class CreateSpeakerRoute implements Route {


    private EventRepositoryService repositoryService;

    @Autowired
    public CreateSpeakerRoute(EventRepositoryService eventRepositoryService) {
        this.repositoryService = eventRepositoryService;
    }

    @POST
    @ApiOperation(value = "Creates a spakers", nickname = "CreateSpeakerRoute")
    @ApiImplicitParams({ //
            @ApiImplicitParam(required = true, dataType = "string", name = "token", paramType = "header"), //
            @ApiImplicitParam(required = true, dataType = "string", name = "event-key", paramType = "path"), //
            @ApiImplicitParam(required = true, dataTypeClass = Speaker[].class, name = "speakers", paramType = "body"), //
    }) //
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success", response = ResponseMessage.class), //
            @ApiResponse(code = 400, message = "Invalid input data", response = ResponseMessage.class), //
            @ApiResponse(code = 401, message = "Unauthorized", response = ResponseMessage.class), //
            @ApiResponse(code = 404, message = "User not found", response = ResponseMessage.class) //
    })
    @Override
    public ResponseMessage handle(@ApiParam(hidden = true) Request request, @ApiParam(hidden = true) Response response) throws Exception {
        ResponseMessage responseMessage;

        String body = request.body();

        if (Objects.isNull(body) || body.isEmpty()) {
            responseMessage = new ResponseMessage(false,
                    "Body can not be empty!", new Object());
            return responseMessage;
        }

        List<Speaker> speakers = JsonUtil.fromJsonForList(body, Speaker.class);

        String eventKey = request.params("event-key");

        return saveSpeakers(speakers, eventKey);
    }

    /**
     * Gelen Speakerlari eventkey ile gerekli evente  ekler
     *
     * @param speakers
     * @param eventKey
     * @return
     */
    public ResponseMessage saveSpeakers(List<Speaker> speakers, String eventKey) {

        ResponseMessage responseMessage;

        if (Objects.isNull(eventKey) || eventKey.isEmpty()) {
            responseMessage = new ResponseMessage(false,
                    "Event key can not empty", speakers);
            return responseMessage;
        }

        Event event = repositoryService.findEventByKeyEquals(eventKey);

        if (Objects.isNull(event)) {
            responseMessage = new ResponseMessage(false,
                    "Event can not found by given key", speakers);
            return responseMessage;
        }

        ResponseMessage responseMessageValid = SpeakerSpecs.isSpekarsValid(speakers);

        if (!responseMessageValid.isStatus()) {
            responseMessageValid.setReturnObject(speakers);
            return responseMessageValid;
        }

        SpeakerSpecs.generateSpeakerIds(speakers);

        SpeakerSpecs.sortSpeakersByName(speakers);

        event.setSpeakers(speakers);

        SpeakerSpecs.removeAgendaElementsWithNoSpeaker(event);

        ResponseMessage dbResponse = repositoryService.save(event);

        if (!dbResponse.isStatus()) {
            dbResponse.setReturnObject(speakers);
            return dbResponse;
        }

        responseMessage = new ResponseMessage(true,
                "Speakers saved successfully", speakers);

        return responseMessage;
    }
}
