package dummy_restapi;

import base_url.BaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

public class Get extends BaseUrl {
    /*
        Given
            https://dummy.restapiexample.com/api/v1/employees
        When
            User sends Get Request to the Url
        Then
            Status code is 200
        And
            There are 24 employees
        And
            "Tiger Nixon" and "Garrett Winters" are among the employees
        And
            The greatest age is 66
        And
            The name of the lowest age is "[Tatyana Fitzpatrick]"
        And
            Total salary of all employees is 6,644,770
         */



    @Test
    public void dummyGet(){

    //Set the url

        spec.pathParam("first","employees");

    //Set the expected data
    //---------------------

    //Send the request and get the response

        Response response = given(spec).get("{first}");
        //response.prettyPrint();

    //Do assertions part_1 with .body() Method and Hamcrest Matchers

        response.then().
                statusCode(200).
                body("data",hasSize(24),
                        "data.employee_name", hasItems("Tiger Nixon","Garrett Winters"));


    //Do assertions part_2 with JsonPath and Groovy Language

        JsonPath jsonPathResponse = response.jsonPath();
        //jsonPathResponse.prettyPrint();

        //The greatest age is 66

        List<Integer> ageList = jsonPathResponse.getList("data.employee_age");
        System.out.println("ageList = " + ageList);

        Collections.sort(ageList);
        System.out.println("sortedAgeList = " + ageList);
        int theGreatestAge = ageList.get(ageList.size()-1);

        assertEquals(66,theGreatestAge);


        //The name of the lowest age is "[Tatyana Fitzpatrick]"

        int theLowestAge = ageList.get(0);
        String nameOfTheLowestAge = jsonPathResponse.getString("data.findAll{it.employee_age=="+theLowestAge+"}.employee_name");
        System.out.println("nameOfTheLowestAge = " + nameOfTheLowestAge);

        assertEquals("[Tatyana Fitzpatrick]",nameOfTheLowestAge);

        //Total salary of all employees is 6,644,770

        List<Integer> salaryList = jsonPathResponse.getList("data.employee_salary");
        System.out.println("salaryList = " + salaryList);

        int totalSalaryOfAllEmployees = 0;
        for(int w:salaryList){

             totalSalaryOfAllEmployees += w;
        }
        System.out.println("totalSalaryOfAllEmployees = " + totalSalaryOfAllEmployees);

        assertEquals(6644770, totalSalaryOfAllEmployees);


    }



}


