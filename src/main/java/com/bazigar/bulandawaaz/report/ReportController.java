package com.bazigar.bulandawaaz.report;

import com.bazigar.bulandawaaz.util.Response;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/buland-awaaz/api/User")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(path = "/report")
    private Response uploadPost(Report report) throws IOException, JSONException {
        return reportService.report(report);
    }
}
