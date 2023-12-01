package test;

import data.Error;
import data.User;
import main.ReqresAPI;
import main.ResponseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserTest {
    public static String green = "\033[1;32m";
    public static String red = "\033[1;31m";
    public static String reset = "\033[0m";

    void verifyResponseCode(int expected, ResponseData actual){
        System.out.print("Response code verification: ");
        assertEquals(expected, actual.code, "Unexpected response code");
        System.out.println(green + "OK" + reset);
    }

    void verifyError(String expected, ResponseData actual){
        System.out.print("Error verification: ");
        assertEquals(expected, ((Error)actual.obj).error, "Unexpected error text");
        System.out.println(green + "OK" + reset);
    }

    void positiveCase(int id) throws IOException {
        ResponseData response = ReqresAPI.deleteUser(id);
        verifyResponseCode(204, response);
    }

    void negativeCase(Object id) throws IOException {
        ResponseData response = ReqresAPI.deleteUser(id);
        verifyResponseCode(404, response);
        verifyError(null, response);
    }

    static Stream<User> predefinedUsers() {
        return Stream.of(TestData.users);
    }

    @ParameterizedTest(name="id={index}")
    @DisplayName("Test delete existing user")
    @MethodSource("predefinedUsers")
    void validUser(User user) throws IOException {
        positiveCase(user.data.id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test delete invalid users (missing ids)")
    @ValueSource(ints = {23,100,23424,100500})
    void missingUser(int id) throws IOException {
        negativeCase(id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test delete invalid users (negative and zero ids)")
    @ValueSource(ints = {-100500, -23424, -1, 0})
    void negativeUser(int id) throws IOException {
        negativeCase(id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test delete invalid users (float numbers ids)")
    @ValueSource(doubles = {0.0, 1.5, -2.6, 10.0})
    void floatNumUser(double id) throws IOException {
        negativeCase(id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test delete invalid users (string ids)")
    @ValueSource(strings = {"2a", "007", "101b", "abcXYZ", "абв", "_!?@#$%^&*+-=<>(){}[]:;,.'\"\\|/", "©±ÆÐßæΣℜ∀≅"})
    void stringUser(String id) throws IOException {
        negativeCase(id);
    }
}