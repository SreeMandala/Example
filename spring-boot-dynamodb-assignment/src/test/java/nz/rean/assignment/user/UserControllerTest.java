package nz.rean.assignment.user;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import nz.rean.assignment.user.UserController;
import nz.rean.assignment.user.UserService;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Mock
  private UserService service;

  @InjectMocks
  private UserController controller;
  /*
  @Test
  public void listShouldRespondWithNoContentWhenNothingInDatabase() throws Exception {

    when(service.list()).thenReturn(emptyList());
    ResponseEntity<List<User>> result = controller.list();
    assertThat(result, is(responseEntityWithStatus(NO_CONTENT)));
  }

  @Test
  public void listShouldRespondWithOkAndResultsFromService() throws Exception {

    User User1 = new User().withEmailId("rean@abc.com");
    User User2 = new User().withEmailId("Olaf@abc.com");
    when(service.list()).thenReturn(asList(User1, User2));
    ResponseEntity<List<User>> result = controller.list();
    //assertThat(result, is(allOf(responseEntityWithStatus(OK).toString(),
     //   responseEntityThat(containsInAnyOrder(User1, User2)))));
  }

  @Test
  public void readShouldReplyWithNotFoundIfNoSuchUser() throws Exception {

    when(service.read("Olaf@abc.com")).thenReturn(Optional.empty());
    ResponseEntity<User> result = controller.read("Olaf@abc.com");
    assertThat(result, is(responseEntityWithStatus(NOT_FOUND)));
  }

  @Test
  public void readShouldReplyWithUserIfUserExists() throws Exception {

    User User = new User().withEmailId("Olaf@abc.com");
    when(service.read("Olaf@abc.com")).thenReturn(Optional.of(User));
    ResponseEntity<User> result = controller.read("Olaf Stapledon");
 //  assertThat(result, is(allOf(responseEntityWithStatus(OK),responseEntityThat(equalTo(User)))));
  }

  @Test
  public void createShouldReplyWithConflictIfUserAlreadyExists() throws Exception {

	  User User = new User().withEmailId("Olaf@abc.com");
    when(service.create(User)).thenReturn(Optional.empty());
    ResponseEntity<User> result = controller.create(User);
    assertThat(result, is(responseEntityWithStatus(CONFLICT)));
  }
*/
  @Test
  public void createShouldReplyWithCreatedAndUserData() throws Exception {

	  User User = new User().withEmailId("Olaf@abc.com");
    when(service.create(User)).thenReturn(Optional.of(User));
    ResponseEntity<User> result = controller.create(User);
    //assertThat(result, is(allOf(
      //  responseEntityWithStatus(CREATED),
        //responseEntityThat(equalTo(User)))));
  }
/*
  @Test
  public void putShouldReplyWithNotFoundIfUserDoesNotExist() throws Exception {

    User newUserData = new User().withEmailId("Olaf@abc.com").withName("Olaf");
    when(service.replace(newUserData)).thenReturn(Optional.empty());
    ResponseEntity<User> result = controller.put("Olaf@abc.com", new User().withName("Olaf"));
    assertThat(result, is(responseEntityWithStatus(NOT_FOUND)));
  }

  @Test
  public void putShouldReplyWithUpdatedUserAndOkIfUserExists() throws Exception {

    User newUserData = new User().withEmailId("Olaf@abc.com").withName("Olaf");
    when(service.replace(newUserData)).thenReturn(Optional.of(newUserData));
    ResponseEntity<User> result = controller.put("Olaf@abc.com", new User().withName("Olaf"));
  //  assertThat(result, is(allOf(
    //    responseEntityWithStatus(OK),
      //  responseEntityThat(equalTo(newUserData)))));
  }
/*
  @Test
  public void patchShouldReplyWithNotFoundIfUserDoesNotExist() throws Exception {

    User newUserData = new User().withEmailId("Olaf@abc.com").withName("Olaf");
    when(service.update(newUserData)).thenReturn(Optional.empty());
    ResponseEntity<User> result = controller.patch("Olaf@abc.com", new User().withName("Olaf"));
    assertThat(result, is(responseEntityWithStatus(NOT_FOUND)));
  }

  @Test
  public void patchShouldReplyWithUpdatedUserAndOkIfUserExists() throws Exception {

    User newUserData = new User().withEmailId("Olaf@abc.com").withName("Olaf");
    when(service.update(newUserData)).thenReturn(Optional.of(newUserData));
    ResponseEntity<User> result = controller.patch("Olaf@abc.com", new User().withName("Olaf"));
    //assertThat(result, is(allOf(
  ////     responseEntityWithStatus(OK),
        //responseEntityThat(equalTo(newUserData)))));
  }

  @Test
  public void deleteShouldRespondWithNotFoundIfUserDoesNotExist() throws Exception {

    when(service.delete("Olaf@abc.com")).thenReturn(false);
    ResponseEntity<Void> result = controller.delete("Olaf@abc.com");
    assertThat(result, is(responseEntityWithStatus(NOT_FOUND)));
  }

  @Test
  public void deleteShouldRespondWithNoContentIfDeleteSuccessful() throws Exception {

    when(service.delete("Olaf@abc.com")).thenReturn(true);
    ResponseEntity<Void> result = controller.delete("Olaf@abc.com");
    assertThat(result, is(responseEntityWithStatus(NO_CONTENT)));
  }

  private Matcher<ResponseEntity> responseEntityWithStatus(HttpStatus status) {

    return new TypeSafeMatcher<ResponseEntity>() {

      @Override
      protected boolean matchesSafely(ResponseEntity item) {

        rseturn status.equals(item.getStatusCode());
      }

      @Override
      public void describeTo(Description description) {

        description.appendText("ResponseEntity with status ").appendValue(status);
      }
    };
  }

  private <T> Matcher<ResponseEntity<? extends T>> responseEntityThat(Matcher<T> categoryMatcher) {

    return new TypeSafeMatcher<ResponseEntity<? extends T>>() {
      @Override
      protected boolean matchesSafely(ResponseEntity<? extends T> item) {

        return categoryMatcher.matches(item.getBody());
      }

      @Override
      public void describeTo(Description description) {

        description.appendText("ResponseEntity with ").appendValue(categoryMatcher);
      }
    };
  }*/
}