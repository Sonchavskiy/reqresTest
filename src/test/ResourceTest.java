package test;

import data.Error;
import data.Resource;
import main.ReqresAPI;
import main.ResponseData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {
    public static String green = "\033[1;32m";
    public static String red = "\033[1;31m";
    public static String reset = "\033[0m";

    void verifyResponseCode(int expected, ResponseData actual){
        System.out.print("Response code verification: ");
        assertEquals(expected, actual.code, "Unexpected response code");
        System.out.println(green + "OK" + reset);
    }

    void verifyDataFields(Resource expected, ResponseData response){
        Resource actual = (Resource)response.obj;
        System.out.print("Data fields verification: ");
        assertEquals(expected.data.id, actual.data.id, "Unexpected id");
        assertEquals(expected.data.name, actual.data.name, "Unexpected name");
        assertEquals(expected.data.year, actual.data.year, "Unexpected year");
        assertEquals(expected.data.color, actual.data.color, "Unexpected color");
        assertEquals(expected.data.pantone_value, actual.data.pantone_value, "Unexpected pantone_value");
        assertEquals(expected.support.url, actual.support.url, "Unexpected url");
        assertEquals(expected.support.text, actual.support.text, "Unexpected text");
        System.out.println(green + "OK" + reset);
    }

    void verifyError(String expected, ResponseData actual){
        System.out.print("Error verification: ");
        assertEquals(expected, ((Error)actual.obj).error, "Unexpected error text");
        System.out.println(green + "OK" + reset);
    }

    void positiveCase(Resource resource) throws IOException {
        ResponseData response = ReqresAPI.getResource(resource.data.id);
        verifyResponseCode(200, response);
        verifyDataFields(resource, response);
    }

    void negativeCase(Object id) throws IOException {
        ResponseData response = ReqresAPI.getResource(id);
        verifyResponseCode(404, response);
        verifyError(null, response);
    }

    static Stream<Resource> predefinedResources() {
        return Stream.of(TestData.resources);
    }

    @ParameterizedTest(name="id={index}")
    @DisplayName("Test existing resources")
    @MethodSource("predefinedResources")
    void validResource(Resource resource) throws IOException {
        positiveCase(resource);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test invalid resources (missing ids)")
    @ValueSource(ints = {23,100,23424,100500})
    void missingResource(int id) throws IOException {
        negativeCase(id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test invalid resources (negative and zero ids)")
    @ValueSource(ints = {-100500, -23424, -1, 0})
    void negativeResource(int id) throws IOException {
        negativeCase(id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test invalid resources (float numbers ids)")
    @ValueSource(doubles = {0.0, 1.5, -2.6, 10.0})
    void floatNumResource(double id) throws IOException {
        negativeCase(id);
    }

    @ParameterizedTest(name="id={0}")
    @DisplayName("Test invalid resources (string ids)")
    @ValueSource(strings = {"2a", "007", "101b", "abcXYZ", "абв", "_!?@#$%^&*+-=<>(){}[]:;,.'\"\\|/", "©±ÆÐßæΣℜ∀≅"})
    void stringResource(String id) throws IOException {
        negativeCase(id);
    }
}