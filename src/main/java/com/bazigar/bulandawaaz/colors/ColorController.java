package com.bazigar.bulandawaaz.colors;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my-post-api/index.php/api/User")
public class ColorController {

    @Autowired
    private ColorService colorService;

    @PostMapping("/addColor")
    public Response addColor(String colorName, String colorCode) {
        return colorService.addColor(colorName, colorCode);
    }

    @PostMapping("/getAllColors")
    public Response getAllColors() {
        return colorService.getAllColors();
    }

    @PostMapping("/addGradientColor")
    public Response addGradientColor(String colorName, String colorCode1,
                                     String colorCode2) {
        return colorService.addGradientColor(colorName, colorCode1, colorCode2);
    }

    @PostMapping("/getAllGradientColors")
    public Response getAllGradientColors() {
        return colorService.getAllGradientColors();
    }
}
