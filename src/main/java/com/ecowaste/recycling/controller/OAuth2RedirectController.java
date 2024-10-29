package com.ecowaste.recycling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth2")
public class OAuth2RedirectController {

    @GetMapping("/loginSuccessWithGoogle")
    public RedirectView loginSuccessGoogle() {
        return new RedirectView("https://www.linkedin.com/in/nazar-vavrushchak-8135b1302/");//in url should be link to frontend part
    }

    //can add facebook or git-hub
}
