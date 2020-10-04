package com.copado.api.controller;

import com.copado.api.model.Account;
import com.copado.api.model.Request;
import com.copado.api.model.Response;
import com.copado.api.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/accountSync")
public class AccountCtrl {

    private AccountService service;


    // CONSTRUCTOR

    @Autowired
    public AccountCtrl(AccountService service) {
        this.service = service;
    }


    // PUBLIC

    @PostMapping("batch")
    public Response addPerson(@RequestBody Request request, @RequestParam(name="init", defaultValue = "false") boolean init) throws Exception {
        if(request.getAccounts() == null || request.getAccounts().isEmpty()) throw new Exception("Please provide accounts");

        if(init) {
            service.clear(request.getTaskId());
        }

        for (Account account: request.getAccounts()) {
            service.addAccount(request.getTaskId(), account);
        }

        return new Response("success");
    }


    @PostMapping("process")
    public Response process(@RequestBody Request request) throws JsonProcessingException {
        return service.process(request.getTaskId());
    }
}
