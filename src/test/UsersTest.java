package test;

import data.Error;
import data.Users;
import data.UserData;
import main.ReqresAPI;
import main.ResponseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {
    public static String green = "\033[1;32m";
    public static String red = "\033[1;31m";
    public static String reset = "\033[0m";

    void verifyResponseCode(int expected, ResponseData actual){
        System.out.print("Response code verification: ");
        assertEquals(expected, actual.code, "Unexpected response code");
        System.out.println(green + "OK" + reset);
    }

    void verifyDataFields(Users expected, ResponseData response){
        System.out.print("Data fields verification: ");
        Users actual = (Users) response.obj;
        assertEquals(expected.page, actual.page, "Unexpected page");
        assertEquals(expected.per_page, actual.per_page, "Unexpected per_page");
        assertEquals(expected.total, actual.total, "Unexpected total");
        assertEquals(expected.total_pages, actual.total_pages, "Unexpected total_pages");
        assertEquals(expected.support.url, actual.support.url, "Unexpected support url");
        assertEquals(expected.support.text, actual.support.text, "Unexpected support text");
        for(int i=0; i<actual.data.length; i++) {
            assertEquals(expected.data[i].id, actual.data[i].id, "Unexpected id in " + (i + 1) + " entry");
            assertEquals(expected.data[i].email, actual.data[i].email, "Unexpected email in " + (i + 1) + " entry");
            assertEquals(expected.data[i].first_name, actual.data[i].first_name, "Unexpected first_name in " + (i + 1) + " entry");
            assertEquals(expected.data[i].last_name, actual.data[i].last_name, "Unexpected last_name in " + (i + 1) + " entry");
            assertEquals(expected.data[i].avatar, actual.data[i].avatar, "Unexpected avatar in " + (i + 1) + " entry");}
        System.out.println(green + "OK" + reset);
    }

    void verifyError(String expected, ResponseData actual){
        System.out.print("Error verification: ");
        assertEquals(expected, ((Error)actual.obj).error, "Unexpected error text");
        System.out.println(green + "OK" + reset);
    }

    void positiveCase(Users users) throws IOException {
        ResponseData response = ReqresAPI.getUsers(users.page);
        verifyResponseCode(200, response);
        verifyDataFields(users, response);
    }

    private void testNegativeNotFound(Object page) throws IOException {
        ResponseData response = ReqresAPI.getUsers(page);
        verifyResponseCode(404, response);
        verifyError(null, response);
    }
    private void testNegativeEmpty(Object page) throws IOException {
        ResponseData response = ReqresAPI.getUsers(page);
        verifyResponseCode(200, response);
        verifyDataFields(new Users((int)page, 6, 12, 2, new UserData[] {}), response);
    }
    private void testNegativeFirst(Object page) throws IOException {
        ResponseData response = ReqresAPI.getUsers(page);
        verifyResponseCode(200, response);
        verifyDataFields(TestData.userPages[0], response);
    }

    static Stream<Users> predefinedUsers() {
        return Stream.of(TestData.userPages);
    }

    @ParameterizedTest(name="page={index}")
    @DisplayName("Test existing pages")
    @MethodSource("predefinedUsers")
    void validUser(Users users) throws IOException {
        positiveCase(users);
    }

    @ParameterizedTest(name="page={0}")
    @DisplayName("Test invalid pages (expecting 404)")
    @ValueSource(strings = {"34", "5.0", "-9", "2a", "abcXYZ"})
    void missingPage(String page) throws IOException {
        testNegativeNotFound(page);
    }

    @ParameterizedTest(name="page={0}")
    @DisplayName("Test invalid pages (missing page - expecting empty page)")
    @ValueSource(ints = {3, 23, 100500})
    void missingNumPage(int page) throws IOException {
        testNegativeEmpty(page);
    }

    @ParameterizedTest(name="page={0}")
    @DisplayName("Test invalid pages (negative/zero - expecting first page)")
    @ValueSource(ints = {0, -1, -2, -100500})
    void negativeZeroPage(int page) throws IOException {
        testNegativeFirst(page);
    }

    @ParameterizedTest(name="page={0}")
    @DisplayName("Test invalid pages (float numbers - expecting first page)")
    @ValueSource(doubles = {-2.8, -0.7, 1.2, 2.0, 2.8, 4.5})
    void missingPage(double page) throws IOException {
        testNegativeFirst(page);
    }

    @ParameterizedTest(name="page={0}")
    @DisplayName("Test invalid pages (strings - expecting first page)")
    @ValueSource(strings = {"2a", "007", "101b", "abcXYZ", "абв", "_!?@#$%^&*+-=<>(){}[]:;,.'\"\\|/", "©±ÆÐßæΣℜ∀≅"})
    void stringPage(String page) throws IOException {
        testNegativeFirst(page);
    }

}
