package nz.rean.assignment.user;


import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1")
@Validated
public class UserController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserService service;

  @RequestMapping(path = "/user", method = RequestMethod.GET)
 // @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<List<User>> list() {

    log.trace("Entering list()");
    List<User> users = service.list();
    if (users.isEmpty()) {
      return new ResponseEntity<>(NO_CONTENT);
    }
    return new ResponseEntity<>(users, OK);
  }

  @RequestMapping(path = "/user/{emailId}", method = RequestMethod.GET)
//  @RequestMapping("/get-by-email")
  @ResponseBody
  public ResponseEntity<User> read(@PathVariable
		  //@Email
          //@Valid
    //   @RequestParam("emailId") 
  String emailId) {

    log.trace("Entering read() with {}", emailId);
    return service.read(emailId)
        .map(user -> new ResponseEntity<>(user, OK))
        .orElse(new ResponseEntity<>(NOT_FOUND));
  }

  @RequestMapping(path = "/user", method = RequestMethod.POST)
  public ResponseEntity<User> create(@RequestBody @Valid User user) {

    log.trace("Entering create() with {}", user);
    return service.create(user)
        .map(newUserData -> new ResponseEntity<>(newUserData, CREATED))
        .orElse(new ResponseEntity<>(CONFLICT));
  }

  @RequestMapping(path = "/user/{emailId}", method = RequestMethod.PUT)
  public ResponseEntity<User> put(@PathVariable @Email
          @Valid
  //       @RequestParam("/user/emailId")
  String emailId, @RequestBody User user) {

    log.trace("Entering put() with {}, {}", emailId, user);
    return service.replace(user.withEmailId(emailId))
        .map(newUserData -> new ResponseEntity<>(newUserData, OK))
        .orElse(new ResponseEntity<>(NOT_FOUND));
  }

  @RequestMapping(path = "/user/{emailId}", method = RequestMethod.PATCH)
  public ResponseEntity<User> patch(@PathVariable
		  //@Email
         // @Valid
  //        @RequestParam("/user/emailId" )
  String emailId, @RequestBody User user) {

    log.trace("Entering patch() with {}, {}", emailId, user);
    return service.update(user.withEmailId(emailId))
        .map(newUserData -> new ResponseEntity<>(newUserData, OK))
        .orElse(new ResponseEntity<>(NOT_FOUND));
  }

  @RequestMapping(path = "/user/{emailId}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable 
		  //@Email
         // @Valid
  //        @RequestParam("emailId") 
  String emailId) {

    log.trace("Entering delete() with {}", emailId);
    return service.delete(emailId) ?
        new ResponseEntity<>(NO_CONTENT) :
        new ResponseEntity<>(NOT_FOUND);
  }
}