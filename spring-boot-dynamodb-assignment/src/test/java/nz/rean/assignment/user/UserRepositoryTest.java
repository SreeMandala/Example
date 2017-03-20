package nz.rean.assignment.user;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;

import nz.rean.assignment.user.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

  @Mock
  private DynamoDBMapper dbMapper;

  @InjectMocks
  private UserRepository repository;
  /*
  @Test
  @SuppressWarnings("unchecked")
  public void readAllShouldScanTheTable() throws Exception {

    PaginatedScanList expectedResult = mock(PaginatedScanList.class);
    when(dbMapper.scan(eq(User.class), any(DynamoDBScanExpression.class))).thenReturn(expectedResult);
    List<User> result = repository.readAll();
    assertThat(result, is(expectedResult));
    verify(expectedResult).loadAllResults();
  }

  @Test
  public void readShouldReturnEmptyOptionalWhenNoResult() throws Exception {

    when(dbMapper.load(User.class, "Dale@abc.com")).thenReturn(null);
    Optional<User> result = repository.read("Dale@abc.com");
    assertThat(result, is(Optional.empty()));
  }

  @Test
  public void readShouldWrapResultIntoOptional() throws Exception {

    User User = new User().withEmailId("Dale@abc.com");
    when(dbMapper.load(User.class, "Dale@abc.com")).thenReturn(User);
    User result = repository.read("Dale@abc.com").get();
    assertThat(result, is(equalTo(User)));
  }
*/
  @Test
  public void saveShouldPersistUser() throws Exception {

    User User = new User().withEmailId("Dale@abc.com");
    repository.save(User);
    verify(dbMapper).save(User);
  }
  /*
  @Test
  public void deleteShouldDeleteUserByName() throws Exception {

    repository.delete("Dale@abc.com");
    verify(dbMapper).delete(eq(new User().withEmailId("Dale@abc.com")), any(DynamoDBMapperConfig.class));
  }*/
}