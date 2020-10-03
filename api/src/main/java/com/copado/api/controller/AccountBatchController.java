package com.copado.api.controller;

import com.copado.api.model.Account;
import com.copado.api.model.BatchRequest;
import com.copado.api.model.Response;
import com.copado.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@RequestMapping("/api/accountSync")
public class AccountBatchController {
    private AccountService service;

    @Autowired
    public AccountBatchController(AccountService service) {
        this.service = service;
    }


    @PostMapping("batch")
    public Response addPerson(@Valid @RequestBody BatchRequest request, @RequestParam(name="init", defaultValue = "false") boolean init) {
        if(init) {
            service.clear(request.getTaskId());
        }

        for (Account account: request.getAccounts()) {
            service.addAccount(request.getTaskId(), account);
        }

        return new Response("success");
    }
}
