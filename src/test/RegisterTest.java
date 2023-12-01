package test;

import data.Error;
import data.Registered;
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

class RegisterTest {
    public static String green = "\033[1;32m";
    public static String red = "\033[1;31m";
    public static String reset = "\033[0m";

    void verifyResponseCode(int expected, ResponseData actual){
        System.out.print("Response code verification: ");
        assertEquals(expected, actual.code, "Unexpected response code");
        System.out.println(green + "OK" + reset);
    }

    void verifyDataFields(int id, ResponseData response){
        System.out.print("Data fields verification: ");
        Registered actual = (Registered) response.obj;
        assertEquals(id, actual.id, "Unexpected id");
        assertNotEquals("", actual.token, "Unexpected (empty) token");
        System.out.println(green + "OK" + reset);
    }

    void verifyError(String expected, ResponseData actual){
        System.out.print("Error verification: ");
        assertEquals(expected, ((Error)actual.obj).error, "Unexpected error text");
        System.out.println(green + "OK" + reset);
    }

    void positiveCase(int id, String email, String password) throws IOException {
        ResponseData response = ReqresAPI.register(email, password);
        verifyResponseCode(200, response);
        verifyDataFields(id, response);
    }

    void negativeCaseUndefined(String email, String password) throws IOException {
        ResponseData response = ReqresAPI.register(email, password);
        verifyResponseCode(400, response);
        verifyError("Note: Only defined users succeed registration", response);
    }

    void negativeCaseMissingEmail(String email, String password) throws IOException {
        ResponseData response = ReqresAPI.register(email, password);
        verifyResponseCode(400, response);
        verifyError("Missing email or username", response);
    }

    void negativeCaseMissingPassword(String email, String password) throws IOException {
        ResponseData response = ReqresAPI.register(email, password);
        verifyResponseCode(400, response);
        verifyError("Missing password", response);
    }

    static Stream<User> predefinedUsers() {
        return Stream.of(TestData.users);
    }

    @ParameterizedTest(name="id={index}")
    @DisplayName("Test register for existing user")
    @MethodSource("predefinedUsers")
    void validUser(User user) throws IOException {
        positiveCase(user.data.id, user.data.email, "password");
    }

    @ParameterizedTest(name="email={0}, password={1}")
    @DisplayName("Test register for undefined user")
    @CsvSource({
            "0123456789,password",
            "_!?@#$%^&*+-=<>(){}[],password",
            "©±ÆÐßæΣℜ∀≅,password",
            "ivan@durak.ru,password"
    })
    void undefinedUser(String email, String password) throws IOException {
        negativeCaseUndefined(email, password);
    }

    @ParameterizedTest(name="email={0}, password={1}")
    @DisplayName("Test register with missing email")
    @CsvSource({
            "'',password",
            ",password"
    })
    void missingEmail(String email, String password) throws IOException {
        negativeCaseMissingEmail(email, password);
    }

    @ParameterizedTest(name="email={0}, password={1}")
    @DisplayName("Test register with missing password")
    @CsvSource({
            "eve.holt@reqres.in,''",
            "george.bluth@reqres.in,"
    })
    void missingPassword(String email, String password) throws IOException {
        negativeCaseMissingPassword(email, password);
    }
}