package nz.rean.assignment.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import nz.rean.assignment.user.User.genderType;
import nz.rean.assignment.user.UserRepository;
import nz.rean.assignment.user.UserService;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsEmptyCollection.emptyCollectionOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UserRepository repository;

  @InjectMocks
  private UserService service;
  /*
  @Test
  public void readShouldReturnEmptyOptionalWhenNoUserFound() throws Exception {

    when(repository.read("Arthur@abc.com")).thenReturn(Optional.empty());
    Optional<User> result = service.read("Arthu@abc.com");
    assertThat(result, is(Optional.empty()));
  }

  @Test
  public void readShouldReturnResultWhenUserFound() throws Exception {

    User User = new User().withEmailId("Arthur@abc.com");
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.of(User));
    User result = service.read("Arthur@abc.com").get();
    assertThat(result, is(equalTo(User)));
  }
*/
  @Test
  public void createShouldReturnEmptyOptionalWhenUserAlreadyExists() throws Exception {

    User existingUser = new User().withEmailId("Arthur@abc.com").withName("Arthur");
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.of(existingUser));
    User newUser = new User().withEmailId("Arthur@abc.com");
    Optional<User> result = service.create(newUser);
    assertThat(result, is(Optional.empty()));
    verify(repository, never()).save(newUser);
  }

  @Test
  public void createShouldReturnNewUserWhenUserNotYetExists() throws Exception {

    User newUser = new User().withEmailId("Arthur@abc.com");
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.empty());
    User result = service.create(newUser).get();
    assertThat(result, is(equalTo(newUser)));
    verify(repository).save(newUser);
  }
/*
  @Test
  public void replaceShouldReturnEmptyOptionalWhenUserNotFound() throws Exception {

    User newUserData = new User().withEmailId("Arthur@abc.com").withName("Arthur");
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.empty());
    Optional<User> result = service.replace(newUserData);
    assertThat(result, is(Optional.empty()));
    verify(repository, never()).save(newUserData);
  }

  @Test
  public void replaceShouldOverwriteAndReturnNewDataWhenUserExists() throws Exception {

    User oldUserData = new User().withEmailId("Arthur@abc.com").withAge(34);
    User newUserData = new User().withEmailId("Arthur@abc.com").withAge(36);
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.of(oldUserData));
    User result = service.replace(newUserData).get();
    assertThat(result, is(equalTo(newUserData)));
    verify(repository).save(newUserData);
  }

  @Test
  public void updateShouldReturnEmptyOptionalWhenUserNotFound() throws Exception {

    User newUserData = new User().withEmailId("Arthur@abc.com").withAge(34);
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.empty());
    Optional<User> result = service.update(newUserData);
    assertThat(result, is(Optional.empty()));
    verify(repository, never()).save(newUserData);
  }

  @Test
  public void updateShouldOverwriteExistingFieldAndReturnNewDataWhenUserExists() throws Exception {

	  User oldUserData = new User().withEmailId("Arthur@abc.com").withAge(34);
	    User newUserData = new User().withEmailId("Arthur@abc.com").withAge(36);
	   when(repository.read("Arthur@abc.com")).thenReturn(Optional.of(oldUserData));
    User result = service.update(newUserData).get();
    assertThat(result, is(equalTo(newUserData)));
    verify(repository).save(newUserData);
  }

  @Test
  public void updateShouldNotOverwriteExistingFieldIfNoNewValuePassedAndShouldReturnNewDataWhenUserExists() throws Exception {

    User oldUserData = new User().withEmailId("Arthur@abc.com").withAge(34);
    User newUserData = new User().withEmailId("Arthur@abc.com").withName("Arthur");
    User expectedResult = new User().withName("Arthur C. Clarke").withName("Arthur").withAge(34).withGender(genderType.MALE);
    when(repository.read("Arthur@abc.com")).thenReturn(Optional.of(oldUserData));
    User result = service.update(newUserData).get();
    assertThat(result, is(equalTo(expectedResult)));
    verify(repository).save(expectedResult);
  }

  @Test
  public void deleteShouldReturnFalseWhenUserNotFound() throws Exception {

    when(repository.read("Arthur@abc.com")).thenReturn(Optional.empty());
    boolean result = service.delete("Arthur@abc.com");
    assertThat(result, is(false));
  }

  @Test
  public void deleteShouldReturnTrueWhenUserDeleted() throws Exception {

    when(repository.read("Arthur@abc.com")).thenReturn(Optional.of(new User().withEmailId("Arthur")));
    boolean result = service.delete("Arthur@abc.com");
    assertThat(result, is(true));
    verify(repository).delete("Arthur@abc.com");
  }

  @Test
  public void listShouldReturnEmptyListWhenNothingFound() throws Exception {

    when(repository.readAll()).thenReturn(emptyList());
    List<User> result = service.list();
    assertThat(result, is(emptyCollectionOf(User.class)));
  }

  @Test
  public void listShouldReturnAllUsers() throws Exception {

    User User1 = new User().withEmailId("Arthur@abc.com");
    User User2 = new User().withEmailId("Dale@abc.com");
    when(repository.readAll()).thenReturn(asList(User1, User2));
    List<User> result = service.list();
    assertThat(result, containsInAnyOrder(User1, User2));
  }*/
}