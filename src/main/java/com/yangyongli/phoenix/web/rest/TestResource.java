package com.yangyongli.phoenix.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yangyongli.phoenix.domain.Book;
import com.yangyongli.phoenix.web.rest.util.HeaderUtil;
import com.yangyongli.phoenix.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangyongli on 7/29/16.
 */

@RestController
@RequestMapping("/api")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(BookResource.class);

    @RequestMapping(value = "/tests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> tests()
        throws URISyntaxException {
        log.debug("REST request to get a page of Books");
        Map<String, String> map = new HashMap<>();
        map.put("hello", "world");
        return new ResponseEntity(map,  HttpStatus.OK);
    }
}
