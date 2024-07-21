package com.sinasheikholeslami.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinasheikholeslami.demo.model.PersonV1;
import com.sinasheikholeslami.demo.model.PersonV2;

@RestController
public class PersonController {

    @GetMapping("v1/person")
    public PersonV1 getPersonV1() {
        return new PersonV1("Sina Sheikholeslami");
    }

    @GetMapping("v2/person")
    public PersonV2 getPersonV2() {
        return new PersonV2("Sina", "Sheikholeslami");
    }

    @GetMapping(path="person", params="version=1")
    public PersonV1 getPersonV1Param() {
        return new PersonV1("Sina Sheikholeslami");
    }

    @GetMapping(path="person", params="version=2")
    public PersonV2 getPersonV2Param() {
        return new PersonV2("Sina", "Sheikholeslami");
    }

    @GetMapping(path="person", headers="X-API-VERSION=1")
    public PersonV1 getPersonV1Header() {
        return new PersonV1("Sina Sheikholeslami");
    }

    @GetMapping(path="person", headers="X-API-VERSION=2")
    public PersonV2 getPersonV2Header() {
        return new PersonV2("Sina", "Sheikholeslami");
    }
}
