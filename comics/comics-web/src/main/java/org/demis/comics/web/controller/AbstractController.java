package org.demis.comics.web.controller;

import org.demis.comics.data.Range;
import org.demis.comics.data.RequestedRangeUnsatisfiableException;
import org.demis.comics.data.Sort;
import org.demis.comics.web.RangeConverter;
import org.demis.comics.web.RestConfiguration;
import org.demis.comics.web.SortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;


public class AbstractController {

    @Autowired
    private RestConfiguration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    protected Range getRange(String requestRange) throws RangeException {
        Range range;

        if (requestRange != null) {
            try {
                range = RangeConverter.parse(requestRange);
            } catch (RequestedRangeUnsatisfiableException e) {
                String reason = "Wrong format for the range parameter. The format is: \"resources: page=[page-number];size=[page-size]\" and the parameter value is: " + requestRange;
                throw new RangeException(reason);
            }
        }
        else {
            range = new Range(0, configuration.getDefaultPageSize());
        }
        return range;
    }

    protected List<Sort> getSorts(String sortParameters) {
        List<Sort> sorts;
        if (sortParameters != null && sortParameters.length() > 0) {
            sorts = SortConverter.parse(sortParameters);
        }
        else {
            sorts = Collections.emptyList();
        }
        return sorts;
    }

    @ExceptionHandler(RangeException.class)
    public Object handleRangeException(HttpServletRequest request, HttpServletResponse httpResponse, RangeException ex) {
        LOGGER.warn(ex.getReason());
        httpResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ex;
    }
}
