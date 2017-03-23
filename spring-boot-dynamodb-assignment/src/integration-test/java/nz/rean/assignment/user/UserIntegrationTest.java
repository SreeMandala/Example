package nz.rean.assignment.user;

import nz.rean.assignment.Application;
import nz.rean.assignment.user.User.genderType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest({"server.port=0"})
public class UserIntegrationTest {

  @Value("${local.server.port}")
  private int port;

  private RestTemplate restTemplate = new TestRestTemplate();

  @Test
  public void postShouldCreateUserAndRespondWithCreated() throws Exception {

    User User = new User().withEmailId(randomUUID().toString());
    ResponseEntity<User> result = restTemplate.postForEntity(url("/v1/User"), User, User.class);
    assertThat(result.getStatusCode(), is(CREATED));
    assertThat(result.getBody(), is(equalTo(User)));
  }

  @Test
  public void postShouldNotCreateUserIfAlreadyExistsAndRespondWithConflict() throws Exception {

    User User = new User().withEmailId(randomUUID().toString());
    restTemplate.postForEntity(url("/v1/User"), User, User.class);
    ResponseEntity<User> result = restTemplate.postForEntity(url("/v1/User"), User, User.class);
    assertThat(result.getStatusCode(), is(CONFLICT));
  }

  @Test
  public void postShouldRespondWithBadRequestIfUserNameNotPassed() throws Exception {

    User User = new User().withName(randomUUID().toString());
    restTemplate.postForEntity(url("/v1/User"), User, User.class);
    ResponseEntity<User> result = restTemplate.postForEntity(url("/v1/User"), User, User.class);
    assertThat(result.getStatusCode(), is(BAD_REQUEST));
  }

  @Test
  public void getShouldReturnPreviouslyCreatedUsers() throws Exception {

    User User1 = new User().withEmailId(randomUUID().toString());
    User User2 = new User().withEmailId(randomUUID().toString());
    restTemplate.postForEntity(url("/v1/User"), User1, User.class);
    restTemplate.postForEntity(url("/v1/User"), User2, User.class);
    ResponseEntity<User[]> result = restTemplate.getForEntity(url("/v1/User"), User[].class);
    assertThat(result.getStatusCode(), is(OK));
    assertThat(asList(result.getBody()), hasItems(User1, User2));
  }

  @Test
  public void getByNameShouldRespondWithNotFoundForUserThatDoesNotExist() throws Exception {

    String EmailId = randomUUID().toString();
    ResponseEntity<User> result = restTemplate.getForEntity(url("/v1/User/" + EmailId), User.class);
    assertThat(result.getStatusCode(), is(NOT_FOUND));
  }

  @Test
  public void getByNameShouldReturnPreviouslyCreatedUser() throws Exception {

    String EMailId = randomUUID().toString();
    User User = new User().withEmailId(EMailId);
    restTemplate.postForEntity(url("/v1/User"), User, User.class);
    ResponseEntity<User> result = restTemplate.getForEntity(url("/v1/User/" + EMailId), User.class);
    assertThat(result.getStatusCode(), is(OK));
    assertThat(result.getBody(), is(equalTo(User)));
  }

  @Test
  public void putShouldReplyWithNotFoundForUserThatDoesNotExist() throws Exception {

    String EMailId = randomUUID().toString();
    User User = new User().withEmailId(EMailId);
    RequestEntity<User> request = new RequestEntity<>(User, PUT, url("/v1/User/" + EMailId));
    ResponseEntity<User> result = restTemplate.exchange(request, User.class);
    assertThat(result.getStatusCode(), is(NOT_FOUND));
  }

  @Test
  public void putShouldReplaceExistingUserValues() throws Exception {

    String EmailId = randomUUID().toString();
    User oldUserData = new User().withEmailId(EmailId).withAge(34);
    User newUserData = new User().withEmailId(EmailId).withAge(34).withName("Arthur").withGender(genderType.MALE);
    restTemplate.postForEntity(url("/v1/User"), oldUserData, User.class);
    RequestEntity<User> request = new RequestEntity<>(newUserData, PUT, url("/v1/User/" + EmailId));
    ResponseEntity<User> result = restTemplate.exchange(request, User.class);
    assertThat(result.getStatusCode(), is(OK));
    assertThat(result.getBody(), is(equalTo(newUserData)));
  }

  @Test
  public void patchShouldReplyWithNotFoundForUserThatDoesNotExist() throws Exception {

    String EMailId = randomUUID().toString();
    User User = new User().withEmailId(EMailId);
    RequestEntity<User> request = new RequestEntity<>(User, PATCH, url("/v1/User/" + EMailId));
    ResponseEntity<User> result = restTemplate.exchange(request, User.class);
    assertThat(result.getStatusCode(), is(NOT_FOUND));
  }

  @Test
  public void patchShouldAddNewValuesToExistingUserValues() throws Exception {

    String EMailId = randomUUID().toString();
    User oldUserData = new User().withEmailId(EMailId).withName("Arthur");
    User newUserData = new User().withEmailId(EMailId).withAge(34).withGender(genderType.MALE).withName("Arthur1");
    User expectedNewUserData = new User().withEmailId(EMailId).withAge(34).withGender(genderType.MALE).withName("Arthur1");
    restTemplate.postForEntity(url("/v1/User"), oldUserData, User.class);
    RequestEntity<User> request = new RequestEntity<>(newUserData, PATCH, url("/v1/User/" + EMailId));
    ResponseEntity<User> result = restTemplate.exchange(request, User.class);
    assertThat(result.getStatusCode(), is(OK));
    assertThat(result.getBody(), is(equalTo(expectedNewUserData)));
  }

  @Test
  public void deleteShouldReturnNotFoundWhenUserDoesNotExist() throws Exception {

    String EMailId = randomUUID().toString();
    RequestEntity<Void> request = new RequestEntity<>(DELETE, url("/v1/User/" + EMailId));
    ResponseEntity<Void> result = restTemplate.exchange(request, Void.class);
    assertThat(result.getStatusCode(), is(NOT_FOUND));
  }

  @Test
  public void deleteShouldRemoveExistingUserAndRespondWithNoContent() throws Exception {

    String EMailId = randomUUID().toString();
    User User = new User().withEmailId(EMailId);
    restTemplate.postForEntity(url("/v1/User"), User, User.class);
    RequestEntity<Void> request = new RequestEntity<>(DELETE, url("/v1/User/" + EMailId));
    ResponseEntity<Void> result = restTemplate.exchange(request, Void.class);
    assertThat(result.getStatusCode(), is(NO_CONTENT));
  }

  private URI url(String url) {

    return URI.create("http://localhost:" + port + url);
  }
}
