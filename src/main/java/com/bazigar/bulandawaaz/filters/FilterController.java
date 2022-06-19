package com.bazigar.bulandawaaz.filters;

import com.bazigar.bulandawaaz.util.Response;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/my-post-api/index.php/api/User")
public class FilterController {

    @Autowired
    private FilterService filterService;

    @PostMapping(value = "/addFilter")
    Response addFilter(String filterName, MultipartFile filterIcon, MultipartFile filterLUT) throws JSONException, IOException {
        return filterService.addFilter(filterName, filterIcon, filterLUT);
    }

    @PostMapping(value = "/getAllFilters")
    Response getAllFilters() {
        return filterService.getAllFilters();
    }

}
