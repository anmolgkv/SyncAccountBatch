package com.copado.api.controller;

import com.copado.api.model.ProcessRequest;
import com.copado.api.model.Response;
import com.copado.api.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/accountSync")
public class AccountProcessController {
    private AccountService service;

    @Autowired
    public AccountProcessController(AccountService service) {
        this.service = service;
    }


    @PostMapping("process")
    public Response process(@Valid @RequestBody ProcessRequest request) throws JsonProcessingException {
        return service.process(request.getTaskId());
    }
}
