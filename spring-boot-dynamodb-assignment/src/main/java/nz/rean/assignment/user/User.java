package nz.rean.assignment.user;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
//import com.amazonaws.services.dynamodbv2.datamodeling.AbstractEnumMarshaller;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
//import com.amazonaws.services.dynamodbv2.datamodeling.marshallers.*;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;

import java.util.Objects;


@DynamoDBTable(tableName = "User")
public class User {
	
 private String name;
  private Integer age;
 // @Email
 // @Pattern(regexp="@.", message="Please provide a valid email address")
  private String emailId;

 // @DynamoDBTypeConvertedEnum(STRING);

  // @DynamoDBTypeConverted(converter = genderType.class);
//   @DynamoDBTyped(DynamoDBAttributeType.S);
//  @Enumerated(genderType.STRING)
  public enum genderType    {

		FEMALE,MALE;
	}
  public genderType gender;

;

  
@DynamoDBAttribute(attributeName = "Name")
  public String getName() {

    return name;
  }

  public void setName(String name) {

    this.name = name;
  }

  public User withName(String name) {

    setName(name);
    return this;
  }

  @DynamoDBAttribute(attributeName = "Age")
  public Integer getAge() {

    return age;
  }

  public void setAge(Integer age) {

    this.age = age;
  }

  public User withAge(Integer age) {

    setAge(age);
    return this;
  }

  
  @DynamoDBHashKey(attributeName = "EmailId")
  @NotNull(message = "EmailId  must not be empty")
  public String getEmailId() {

    return emailId;
  }

  public void setEmailId(String emailId) {

    this.emailId = emailId;
  }

  public User withEmailId(String emailId) {

	  setEmailId(emailId);
    return this;
  }
 // @DynamoDBMarshalling(marshallerClass=)
  //@DynamoDBTyped(DynamoDBAttributeType.S)

  //@ DynamoDBTypeConvertedEnum()
 // @DynamoDBTyped(DynamoDBAttributeType.S);

  
   
  @DynamoDBAttribute(attributeName = "Gender")
  
@DynamoDBTyped(DynamoDBAttributeType.S)
 // @DynamoDBTypeConverted(converter = DimensionTypeConverter.class )
 // @NotNull(message = "EmailId  must not be empty")
  public genderType getGender() {

    return gender;
  }

  public void setGender(genderType gender) {

    this.gender = gender;
  }

  public User withGender(genderType gender) {

	  setGender(gender);
    return this;
  }

  
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(getName(), user.getName()) &&
        Objects.equals(getAge(), user.getAge()) &&
        Objects.equals(getEmailId(), user.getEmailId())&&
       Objects.equals(getGender(), user.getGender());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName(), getAge(), getEmailId(),getGender());
  }
}