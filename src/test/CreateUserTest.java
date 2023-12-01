package test;

import data.Error;
import data.User;
import data.UserCreated;
import main.ReqresAPI;
import main.ResponseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserTest {
    public static String green = "\033[1;32m";
    public static String red = "\033[1;31m";
    public static String reset = "\033[0m";

    void verifyResponseCode(int expected, ResponseData actual){
        System.out.print("Response code verification: ");
        assertEquals(expected, actual.code, "Unexpected response code");
        System.out.println(green + "OK" + reset);
    }

    void verifyDataFields(String name, String job, ResponseData response){
        System.out.print("Data fields verification: ");
        UserCreated actual = (UserCreated) response.obj;
        assertEquals(name, actual.name, "Unexpected name");
        assertEquals(job, actual.job, "Unexpected job");

        assertTrue(actual.id.matches("(0|[1-9]\\d*)"),
                "Unexpected id: " + actual.id + " (not a positive integer number)");
        assertTrue(Integer.parseInt(actual.id)>TestData.users.length,
                "Unexpected id: " + actual.id
                        + " (should be more than max existing user id (" + TestData.users.length + "))");
        Date currentTime = new Date();
        long delay = currentTime.getTime()-actual.createdAt.getTime();
        assertTrue(Math.abs(delay)<=1000, "Unexpected createdAt (current time: " + currentTime
                + ", got: " + actual.createdAt + ", delay: " + delay + " ms");
        System.out.println(green + "OK" + reset);
    }

    void verifyError(String expected, ResponseData actual){
        System.out.print("Error verification: ");
        assertEquals(expected, ((Error)actual.obj).error, "Unexpected error text");
        System.out.println(green + "OK" + reset);
    }

    void positiveCase(String name, String job) throws IOException {
        ResponseData response = ReqresAPI.createUser(name, job);
        verifyResponseCode(201, response);
        verifyDataFields(name, job, response);
    }

    void negativeCase(String name, String job) throws IOException {
        ResponseData response = ReqresAPI.createUser(name, job);
        verifyResponseCode(400, response);
        verifyError(null, response);
    }

    static Stream<User> predefinedUsers() {
        return Stream.of(TestData.users);
    }

    @ParameterizedTest(name="name={0}, job={1}")
    @DisplayName("Test valid name and valid job")
    @CsvSource({
            "morpheus,leader",
            "ivan,durak"
    })
    void validUser(String name, String job) throws IOException {
        positiveCase(name, job);
    }

    @ParameterizedTest(name="name={0}, job={1}")
    @DisplayName("Test invalid name")
    @CsvSource({
            "0123456789,job",
            "_!?@#$%^&*+-=<>(){}[],job",
            "©±ÆÐßæΣℜ∀≅,job",
            "'',job",
            ",job"
    })
    void invalidName(String name, String job) throws IOException {
        negativeCase(name, job);
    }

    @ParameterizedTest(name="name={0}, job={1}")
    @DisplayName("Test invalid job")
    @CsvSource({
            "name,0123456789",
            "name,_!?@#$%^&*+-=<>(){}[]",
            "name,©±ÆÐßæΣℜ∀≅",
            "name,''",
            "name,"
    })
    void invalidJob(String name, String job) throws IOException {
        negativeCase(name, job);
    }
}