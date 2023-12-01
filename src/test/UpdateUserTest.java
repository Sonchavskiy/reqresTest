package test;

import data.Error;
import data.User;
import data.UserUpdated;
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

class UpdateUserTest {
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
        UserUpdated actual = (UserUpdated) response.obj;
        assertEquals(name, actual.name, "Unexpected name");
        assertEquals(job, actual.job, "Unexpected job");

        Date currentTime = new Date();
        long delay = currentTime.getTime()-actual.updatedAt.getTime();
        assertTrue(Math.abs(delay)<=1000, "Unexpected updatedAt (current time: " + currentTime
                + ", got: " + actual.updatedAt + ", delay: " + delay + " ms");
        System.out.println(green + "OK" + reset);
    }

    void verifyError(String expected, ResponseData actual){
        System.out.print("Error verification: ");
        assertEquals(expected, ((Error)actual.obj).error, "Unexpected error text");
        System.out.println(green + "OK" + reset);
    }

    void positiveCase(int id, String name, String job) throws IOException {
        ResponseData response = ReqresAPI.updateUser(id, name, job);
        verifyResponseCode(200, response);
        verifyDataFields(name, job, response);
    }

    void negativeCase(Object id ,String name, String job) throws IOException {
        ResponseData response = ReqresAPI.updateUser(id, name, job);
        verifyResponseCode(404, response);
        verifyError(null, response);
    }

    static Stream<User> predefinedUsers() {
        return Stream.of(TestData.users);
    }

    @ParameterizedTest(name="id={index}, name=\"name\", job=\"job\"")
    @DisplayName("Test update existing user")
    @MethodSource("predefinedUsers")
    void validUser(User user) throws IOException {
        positiveCase(user.data.id, "name", "job");
    }

    @ParameterizedTest(name="id={0}, name=\"name\", job=\"job\"")
    @DisplayName("Test update invalid users (missing ids)")
    @ValueSource(ints = {23,100,23424,100500})
    void missingUser(int id) throws IOException {
        negativeCase(id, "name", "job");
    }

    @ParameterizedTest(name="id={0}, name=\"name\", job=\"job\"")
    @DisplayName("Test update invalid users (negative and zero ids)")
    @ValueSource(ints = {-100500, -23424, -1, 0})
    void negativeUser(int id) throws IOException {
        negativeCase(id, "name", "job");
    }

    @ParameterizedTest(name="id={0}, name=\"name\", job=\"job\"")
    @DisplayName("Test update invalid users (float numbers ids)")
    @ValueSource(doubles = {0.0, 1.5, -2.6, 10.0})
    void floatNumUser(double id) throws IOException {
        negativeCase(id, "name", "job");
    }

    @ParameterizedTest(name="id={0}, name=\"name\", job=\"job\"")
    @DisplayName("Test update invalid users (string ids)")
    @ValueSource(strings = {"2a", "007", "101b", "abcXYZ", "абв", "_!?@#$%^&*+-=<>(){}[]:;,.'\"\\|/", "©±ÆÐßæΣℜ∀≅"})
    void stringUser(String id) throws IOException {
        negativeCase(id, "name", "job");
    }

    @ParameterizedTest(name="id={0}, name={1}, job={2}")
    @DisplayName("Test invalid name")
    @CsvSource({
            "1,0123456789,job",
            "2,_!?@#$%^&*+-=<>(){}[],job",
            "3,©±ÆÐßæΣℜ∀≅,job",
            "4,'',job",
            "5,,job"
    })
    void invalidName(int id, String name, String job) throws IOException {
        negativeCase(id, name, job);
    }

    @ParameterizedTest(name="id={0}, name={1}, job={2}")
    @DisplayName("Test invalid job")
    @CsvSource({
            "6,name,0123456789",
            "7,name,_!?@#$%^&*+-=<>(){}[]",
            "8,name,©±ÆÐßæΣℜ∀≅",
            "9,name,''",
            "10,name,"
    })
    void invalidJob(int id, String name, String job) throws IOException {
        negativeCase(id, name, job);
    }
}