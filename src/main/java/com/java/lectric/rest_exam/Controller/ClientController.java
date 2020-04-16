package com.java.lectric.rest_exam.Controller;

import com.java.lectric.rest_exam.model.Client;
import com.java.lectric.rest_exam.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController — говорит спрингу, что данный класс является REST контроллером.
Т.е. в данном классе будет реализована логика обработки клиентских запросов
 */
@RestController
public class ClientController {
    private final ClientService clientService;

    /*
    @Autowired — говорит спрингу, что в этом месте необходимо внедрить зависимость.
    В конструктор мы передаем интерфейс ClientService. Реализацию данного сервиса
    мы пометили аннотацией @Service ранее, и теперь спринг сможет передать
    экземпляр этой реализации в конструктор контроллера.
     */
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(value = "/clients")
    public ResponseEntity<?> create(@RequestBody Client client) {
        clientService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
