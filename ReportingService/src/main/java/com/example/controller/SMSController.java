package com.example.controller;


import com.example.dto.API;
import com.example.dto.SMS;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SMSController {

    List<SMS> apis = new ArrayList<>();

    public SMSController() {
        apis.addAll(List.of(
                new SMS("b957a42266e0", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("b957a42000e0", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("asdfsadf", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("asdfadsdas", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("2345423523", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("sfaadsfasdf", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("52345342", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("789764t452", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("hbsf4352", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("sfgjdhfhf", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("34214214143", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("gkhghkghkgh", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("5423524524352", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("vcvcxvzcx", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("4565475768", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("ggdsfgs", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("sadfafdads", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("cbcbnxcvbxvcb", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("76896967987", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("dfgdsgdsg", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp"),
                new SMS("iuoyiouoiuyu", "image-0.3898579002140954", "container-0.3898579002140954", "0.0.0.0:8085->8000/tcp"),
                new SMS("asdfsfasd", "image-222222222222222222222", "container-0.38985445454545454", "0.0.0.0:8085->8001/tcp")

                ));

    }

    @GetMapping("/all-running-services")
    public List<SMS> getAPIs() {
        return apis;
    }


}