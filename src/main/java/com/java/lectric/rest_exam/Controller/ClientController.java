package com.java.lectric.rest_exam.Controller;

import com.java.lectric.rest_exam.model.Client;
import com.java.lectric.rest_exam.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    /**
     * здесь мы обозначаем, что данный метод обрабатывает POST запросы на адрес /clients
     * @param client значение этого параметра подставляется из тела запроса. Об этом говорит аннотация  @RequestBody
     * @return ResponseEntity — специальный класс для возврата ответов.
     *         С помощью него мы сможем в дальнейшем вернуть клиенту HTTP статус код
     */
    @PostMapping(value = "/clients")
    public ResponseEntity<?> create(@RequestBody Client client) {
        /*Внутри тела метода мы вызываем метод create у ранее созданного сервиса и
        передаем ему принятого в параметрах контроллера клиента.
         */
        clientService.create(client);
        /*
        После чего возвращаем статус 201 Created, создав новый объект ResponseEntity
        и передав в него нужное значение енума HttpStatus.
         */
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * реализуем операцию получения списка всех имеющихся клиентов
     * @GetMapping(value = "/clients") — все аналогично аннотации @PostMapping, только
     * теперь мы обрабатываем GET запросы.
     * @return ResponseEntity — специальный класс для возврата ответов.
     *         возвращаем ResponseEntity<List<Client>>, только в этот раз, помимо HTTP статуса,
     *         мы вернем еще и тело ответа, которым будет список клиентов.
     *         В REST контроллерах спринга все POJO объекты, а также коллекции POJO объектов,
     *         которые возвращаются в качестве тел ответов, автоматически сериализуются в JSON,
     *         если явно не указано иное.
     */
    @GetMapping(value = "/clients")
    public ResponseEntity<List<Client>> read() {
        /* Внутри метода, с помощью нашего сервиса мы получаем список всех клиентов. */
        final List<Client> clients = clientService.readAll();
        /*
        Далее, в случае если список не null и не пуст,
        мы возвращаем c помощью класса ResponseEntity сам список клиентов и HTTP статус 200 OK.
        Иначе мы возвращаем просто HTTP статус 404 Not Found.
         */
        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Далее реализуем возможность получать клиента по его id:
     * Данный метод будет принимать запросы на uri вида /clients/{id}, где вместо {id} может быть
     * любое численное значение. Данное значение, впоследствии, передается переменной int id — параметру метода.
     * @param id у нас тут появилась переменная пути. Переменная, которая определена в URI.
     *           value = "/clients/{id}". Мы указали ее в фигурных скобках.
     *           А в параметрах метода принимаем её в качестве int переменной,
     *           с помощью аннотации @PathVariable(name = "id").
     * @return ResponseEntity — специальный класс для возврата ответов.
     *      *         возвращаем ResponseEntity<List<Client>>, только в этот раз, помимо HTTP статуса,
     *      *         мы вернем еще и тело ответа, которым будет список клиентов.
     *      *         В REST контроллерах спринга все POJO объекты, а также коллекции POJO объектов,
     *      *         которые возвращаются в качестве тел ответов, автоматически сериализуются в JSON,
     *      *         если явно не указано иное.
     */
    @GetMapping(value = "/clients/{id}")
    public ResponseEntity<Client> read(@PathVariable(name = "id") int id) {
        /* получаем объект Client с помощью нашего сервиса и принятого id. */
        final Client client = clientService.read(id);

        /*
        И далее, по аналогии со списком, возвращаем либо статус 200 OK и сам объект Client,
        либо просто статус 404 Not Found, если клиента с таким id не оказалось в системе.
         */
        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
