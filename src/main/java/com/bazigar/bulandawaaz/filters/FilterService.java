package com.bazigar.bulandawaaz.filters;

import com.bazigar.bulandawaaz.util.BunnyCDN;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FilterService {

    @Autowired
    private FilterRepository filterRepository;

    public Response addFilter(String filterName, MultipartFile filterIcon, MultipartFile filterLUT) throws JSONException, IOException {
        if (filterName == null || filterIcon == null || filterLUT == null) {
            return new Response(
                    "filterName, filterIcon and filterLUT are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        BunnyCDN bunny = new BunnyCDN();
        String uploadFilterIcon = (String)bunny.uploadFile(filterIcon.getOriginalFilename(),
                "filterIcon", "0", "USER", filterIcon);
        if (Objects.equals(uploadFilterIcon, "Failed to upload image")) {
            return new Response(
                    "Filter icon upload failed!",
                    new ResponseStatus(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
                    )
            );
        }
        String uploadFilterLUT = (String) bunny.uploadFile(filterLUT.getOriginalFilename(),
                "filterLUT", "0", "USER", filterLUT);
        if (Objects.equals(uploadFilterLUT, "Failed to upload image")) {
            return new Response(
                    "Filter LUT upload failed!",
                    new ResponseStatus(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
                    )
            );
        }
        Filter filter = new Filter(
            filterName,
            uploadFilterIcon,
            uploadFilterLUT
        );
        filterRepository.save(filter);
        return new Response(
                "Filter successfully uploaded!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                filter.getId()
        );
    }

    Response getAllFilters() {
        List<Filter> filterList = filterRepository.findAll();
        return new Response(
                filterList.size()+" results found!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                filterList
        );
    }
}
