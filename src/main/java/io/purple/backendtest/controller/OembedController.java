package io.purple.backendtest.controller;

import io.purple.backendtest.domain.OembedEntity;
import io.purple.backendtest.exception.NotUrlTypeException;
import io.purple.backendtest.form.SearchForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class OembedController {

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }

    @ExceptionHandler(NotUrlTypeException.class)
    public ModelAndView notUrlTypeException(NotUrlTypeException e) {
        //TODO 예외 처리
        Map<String, Object> model = new HashMap<>();
        model.put("error", e.getMessage());
        model.put("searchForm", new SearchForm());
        return new ModelAndView("index", model);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/search")
    public String searchOembed(@RequestParam("url") OembedEntity entity, Model model) {
        model.addAttribute("result", entity);
        return "index";
    }

}
