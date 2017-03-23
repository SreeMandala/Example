package nz.rean.assignment.user;

import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import static com.amazonaws.util.StringUtils.isNullOrEmpty;

@Service
public class UserService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UserRepository repository;

  public Optional<User> read(@Email
          @Valid
          @RequestParam("emailId")String emailId) {

    log.trace("Entering read() with {}", emailId);
    return repository.read(emailId);
  }

  public Optional<User> create(User User) {

    log.trace("Entering create() with {}", User);
    if (repository.read(User.getEmailId()).isPresent()) {
      log.warn("EmailId {} not found", User.getEmailId());
      return Optional.empty();
    }
    repository.save(User);
    return Optional.of(User);
  }

  public Optional<User> replace(User newUserData) {

    log.trace("Entering replace() with {}", newUserData);
    Optional<User> existingUser = repository.read(newUserData.getEmailId());
    if (!existingUser.isPresent()) {
      log.warn("User {} not found", newUserData.getEmailId());
      return Optional.empty();
    }
    User user = existingUser.get();
    user.setName(newUserData.getName());
    user.setAge(newUserData.getAge());
    user.setGender(newUserData.getGender());
    repository.save(user);
    return Optional.of(user);
  }

  public Optional<User> update(User newUserData) {

    log.trace("Entering update() with {}", newUserData);
    Optional<User> existingUser = repository.read(newUserData.getEmailId());
    if (!existingUser.isPresent()) {
      log.warn("User {} not found", newUserData.getEmailId());
      return Optional.empty();
    }
    User user = existingUser.get();
    if (!isNullOrEmpty(newUserData.getName())) {
      user.setName(newUserData.getName());
    }
    if (!isNullOrEmpty((newUserData.getAge().toString()))) {
      user.setAge(newUserData.getAge());
    }
    if (!isNullOrEmpty((newUserData.getGender().toString()))) {
        user.setGender(newUserData.getGender());
      }
    repository.save(user);
    return Optional.of(user);
  }

  public boolean delete(@Email
          @Valid
          @RequestParam("emailId")String emailId) {

    log.trace("Entering delete() with {}", emailId);
    if (!repository.read(emailId).isPresent()) {
      log.warn("User {} not found", emailId);
      return false;
    }
    repository.delete(emailId);
    return true;
  }

  public List<User> list() {

    log.trace("Entering list()");
    return repository.readAll();
  }
}