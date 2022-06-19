package com.bazigar.bulandawaaz.colors;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ColorService {

    @Autowired
    private ColorsRepository colorsRepository;

    @Autowired
    private GradientColorRepository gradientColorRepository;

    public Response addColor(String colorName, String colorCode) {
        if (colorName == null || colorCode == null) {
            return new Response(
                    "Both colorName and colorCode are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (colorName.isEmpty()) {
            return new Response(
                    "colorName cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (colorCode.isEmpty()) {
            return new Response(
                    "colorCode cannot be empty",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (!Pattern.matches("#[A-Fa-f0-9]{6}", colorCode)) {
            return new Response(
                    "colorCode is not in proper hexColor format!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    )
            );
        }
        Optional<Colors> color = colorsRepository.findColorsByColorCode(colorCode);
        if (color.isPresent()) {
            return new Response(
                    "A color with this colorCode already exists!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    ),
                    color
            );
        }
        colorsRepository.save(new Colors(
                colorName,
                colorCode
        ));
        return new Response(
                "Color successfully added!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getAllColors() {
        List<Colors> colorsList = colorsRepository.findAll();
        return new Response(
                colorsList.size()+" colors found!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                colorsList
        );
    }

    public Response addGradientColor(String colorName, String colorCode1, String colorCode2) {
        if (colorName == null || colorCode1 == null || colorCode2 == null) {
            return new Response(
                    "colorName, colorCode1 and colorCode2 are required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (colorCode1.isEmpty() || colorCode2.isEmpty() || colorName.isEmpty()) {
            return new Response(
                    "None of the fields can be empty!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (!Pattern.matches("#[A-Fa-f0-9]{6}", colorCode1)) {
            return new Response(
                    "colorCode1 is not in proper hexColor format!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    )
            );
        }
        if (!Pattern.matches("#[A-Fa-f0-9]{6}", colorCode2)) {
            return new Response(
                    "colorCode2 is not in proper hexColor format!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    )
            );
        }
        Optional<GradientColor> gradientColor = gradientColorRepository.findGradientColorByColorCode1AAndColorCode2(
                colorCode1,
                colorCode2
        );
        if (gradientColor.isPresent()) {
            return new Response(
                    "This gradient color already exists!",
                    new ResponseStatus(
                            HttpStatus.CONFLICT.value(),
                            HttpStatus.CONFLICT.getReasonPhrase()
                    ),
                    gradientColor
            );
        }
        gradientColorRepository.save(new GradientColor(
            colorName,
            colorCode1,
            colorCode2
        ));
        return new Response(
                "Gradient color successfully added!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getAllGradientColors() {
        List<GradientColor> gradientColors = gradientColorRepository.findAll();
        return new Response(
                gradientColors.size()+" gradients found!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                gradientColors
        );
    }
}