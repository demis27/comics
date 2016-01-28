package org.demis.comics.web.controller;

import org.demis.comics.business.service.ActorBusinessService;
import org.demis.comics.data.Range;
import org.demis.comics.data.SortParameterElement;
import org.demis.comics.data.jpa.entity.ActorEntity;
import org.demis.comics.web.converter.ActorWebConverter;
import org.demis.comics.web.dto.ActorWebDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorController.class);

    @Autowired
    @Qualifier("actorBusinessService" )
    private ActorBusinessService service;

    @Autowired
    @Qualifier("actorWebConverter" )
    private ActorWebConverter converter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<ActorWebDTO> getActors(@RequestParam(value="sort", required = false) String sortParameters,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<ActorWebDTO> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<SortParameterElement> sorts = getSorts(sortParameters);

        List<ActorEntity> entities = service.findPart(range, sorts);
        if (entities.isEmpty()) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            response.setHeader(HttpHeaders.CONTENT_RANGE, "resources " + range.getStart() + "-" + Math.min(range.getEnd(), entities.size()) + "/*");
            response.setStatus(HttpStatus.OK.value());
            dtos = converter.convertEntities(entities);
        }
        return dtos;
    }

    @ResponseBody
    @RequestMapping(value = {"/{id}"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Object getActor(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        ActorEntity incomingEmail = service.findById(id);
        if (incomingEmail != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, incomingEmail.getUpdated().getTime());
            return converter.convertEntity(incomingEmail);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(consumes = {"application/json"},
            produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.POST)
    @ResponseBody
    public Object postActor(@RequestBody ActorWebDTO dto, HttpServletResponse httpResponse) {
        ActorEntity entity = service.create(converter.convertWebDTO(dto));
        if (entity != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, entity.getUpdated().getTime());
            return converter.convertEntity(entity);
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postActors() {
        LOGGER.info("Try to call POST HTTP method on a resource collection");
    }

    // ------------------------------------------------------------------------
    // PUT
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putActors() {
        LOGGER.info("Try to call PUT HTTP method on a resource");
    }

    @RequestMapping(value = {"/{id}"},
            consumes = {"application/json"},
            produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.PUT)
    @ResponseBody
    public Object putActor(@PathVariable("id") Long id, @RequestBody ActorWebDTO dto, HttpServletResponse httpResponse) {
        ActorEntity entity = service.findById(id);
        if (entity != null) {
            converter.updateEntity(entity, dto);
            try {
                ActorEntity result = service.update(entity);
                httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, result.getUpdated().getTime());
                return converter.convertEntity(result);
            } catch (EntityNotFoundException e) {
                LOGGER.warn("Can't modify the resource: " + entity, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                return null;
            }
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteActors() {
        LOGGER.info("Try to call DELETE HTTP method on a resource collection");
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteActor(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        ActorEntity entity = service.findById(id);
        if (entity != null) {
            try {
                service.delete(id);
            } catch (EntityNotFoundException e) {
                LOGGER.warn("Can't delete the resource: " + entity, e);
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            httpResponse.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
        }
        return null;
    }

    // ------------------------------------------------------------------------
    // OPTIONS
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsActors(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,OPTIONS,POST");
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsActor(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,DELETE,OPTIONS");
    }
}