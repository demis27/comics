package org.demis.comics.web.controller;

import org.demis.comics.business.service.ComicBookBusinessService;
import org.demis.comics.data.Range;
import org.demis.comics.data.Sort;
import org.demis.comics.data.jpa.entity.ComicBookEntity;
import org.demis.comics.web.converter.ComicBookWebConverter;
import org.demis.comics.web.dto.ComicBookWebDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/rest/")
public class ComicBookController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComicBookController.class);

    @Autowired
    @Qualifier("comicBookBusinessService" )
    private ComicBookBusinessService service;

    @Autowired
    @Qualifier("comicBookWebConverter" )
    private ComicBookWebConverter converter;

    // ------------------------------------------------------------------------
    // GET
    // ------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.GET,
            value = {"comicBook", "comicBook/"},
            produces = {"application/json; charset=UTF-8"}
    )
    @ResponseBody
    public List<ComicBookWebDTO> getComicBooks(@RequestParam(value="sort", required = false) String sortParameters,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws RangeException {
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "resources");

        List<ComicBookWebDTO> dtos = null;
        Range range = getRange(request.getHeader("Range"));
        List<Sort> sorts = getSorts(sortParameters);

        List<ComicBookEntity> entities = service.findPart(range, sorts);
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
    @RequestMapping(value = {"comicBook/{id}","comicBook/{id}/"},
            method = RequestMethod.GET,
            produces = {"application/json; charset=UTF-8"}
    )
    public Object getComicBook(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        ComicBookEntity IncomingEmail = service.findById(id);
        if (IncomingEmail != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, IncomingEmail.getUpdated().getTime());
            return converter.convertEntity(IncomingEmail);
        } else {
            httpResponse.setStatus(HttpStatus.NOT_FOUND.value());
            return null;
        }
    }


    // ------------------------------------------------------------------------
    // POST
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"comicBook", "comicBook/"},
            consumes = {"application/json"},
            produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.POST)
    @ResponseBody
    public Object postComicBook(@RequestBody ComicBookWebDTO dto, HttpServletResponse httpResponse) {
        ComicBookEntity entity = service.create(converter.convertWebDTO(dto));
        if (entity != null) {
            httpResponse.setStatus(HttpStatus.OK.value());
            httpResponse.setDateHeader(HttpHeaders.LAST_MODIFIED, entity.getUpdated().getTime());
            return converter.convertEntity(entity);
        } else {
            httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return null;
        }
    }

    @RequestMapping(value = {"comicBook/{id}", "comicBook/{id}/"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void postComicBooks() {
    }

    // ------------------------------------------------------------------------
    // PUT
    // ------------------------------------------------------------------------

    @RequestMapping(value = {"comicBook", "comicBook/"}, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void putComicBooks() {
    }

    @RequestMapping(value = {"comicBook/{id}", "comicBook/{id}/"},
            consumes = {"application/json"},
            produces = {"application/json; charset=UTF-8"},
            method = RequestMethod.PUT)
    @ResponseBody
    public Object putComicBook(@PathVariable("id") Long id, @RequestBody ComicBookWebDTO dto, HttpServletResponse httpResponse) {
        ComicBookEntity entity = service.findById(id);
        if (entity != null) {
            converter.updateEntity(entity, dto);
            try {
                ComicBookEntity result = service.update(entity);
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

    @RequestMapping(value = {"comicBook", "comicBook/"}, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void deleteComicBooks() {
    }

    @RequestMapping(value = {"comicBook/{id}", "comicBook/{id}/"}, method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteComicBook(@PathVariable(value = "id") Long id, HttpServletResponse httpResponse) {
        ComicBookEntity entity = service.findById(id);
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

    @RequestMapping(value = {"comicBook", "comicBook/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsComicBooks(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,OPTIONS,POST");
    }

    @RequestMapping(value = {"comicBook/{id}", "comicBook/{id}/"}, method = RequestMethod.OPTIONS)
    @ResponseStatus(HttpStatus.OK)
    public void optionsComicBook(HttpServletResponse httpResponse){
        httpResponse.addHeader(HttpHeaders.ALLOW, "HEAD,GET,PUT,DELETE,OPTIONS");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String unsupportedMediaType() {
        return "unsupportedMediaType";
    }
}
