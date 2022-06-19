package com.bazigar.bulandawaaz.category;

import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class SearchCategoryController {

    @Autowired
    private SearchCategoryService categoryService;

    @PostMapping(value = "/addToCategory")
    public Response addCategory(String category, Long postId) {
        return categoryService.addToCategory(category,postId);
    }

    @PostMapping(value = "/createCategory")  // admin
    public Response createCategory(String category) {
        return categoryService.createCategory(category);
    }


    @GetMapping(value = "/getCategories")
    public Response getCategories() {
        return categoryService.getCategory();
    }

}
