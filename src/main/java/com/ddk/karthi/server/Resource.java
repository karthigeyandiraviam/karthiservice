package com.ddk.karthi.server;

import com.ddk.karthi.apps.AddTwoFractions;
import com.ddk.karthi.apps.AddTwoLinkedList;
import com.ddk.karthi.apps.CreditCard;
import com.ddk.karthi.apps.GradeSort;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;

@Path("ddk/v1")
public class Resource {
    private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    public Response health() {
        try {
            LOGGER.log(Level.INFO, "Entering health()");
            return Response.ok("OK").build();
        } finally {
            LOGGER.log(Level.INFO, "Exiting health()");
        }
    }

    @GET
    @Path("/version")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mavenVersion() throws Exception {
        try {
            LOGGER.log(Level.INFO, "Entering mavenVersion()");
            String version = null;

            // try to load from maven properties first
            Properties p = new Properties();
            InputStream is = getClass().getResourceAsStream("/META-INF/maven/karthi-service/karthi-service/pom.properties");
            if (is == null)
                is = getClass().getResourceAsStream("META-INF/maven/karthi-service/karthi-service/pom.properties");

            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
            }

            // fallback to using Java API
            if (version == null) {
                Package aPackage = getClass().getPackage();
                if (aPackage != null) {
                    version = aPackage.getImplementationVersion();
                    if (version == null) {
                        version = aPackage.getSpecificationVersion();
                    }
                }
            }

            if (version == null) {
                // we could not compute the version so use a blank
                version = "1.0";
            }
            return Response.ok(version).build();
        } finally {
            LOGGER.log(Level.INFO, "Exiting mavenVersion()");
        }
    }

    /*
    Sample Input:
    { "num1": "899", "num2":"901" }
     */
    @POST
    @Path("/addtwolinkedlist")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTwoLinkedList(AddTwoLinkedList addTwoLinkedList) throws Exception {
        try {
            LOGGER.log(Level.INFO, "Entering addTwoLinkedList()");
            if (addTwoLinkedList == null)
                throw new Exception("AddTwoLinkedList is null");
            if (addTwoLinkedList.getNum1() == null)
                throw new Exception("Number 1 is null");
            if (addTwoLinkedList.getNum2() == null)
                throw new Exception("Number 2 is null");
            // Add LinkedLists
            addTwoLinkedList.addTwoNumbers();
            return Response.ok(addTwoLinkedList.getResult()).type(MediaType.APPLICATION_JSON).build();
        } finally {
            LOGGER.log(Level.INFO, "Exiting addTwoLinkedList()");
        }
    }

    /*
    Sample Input:
    { "f1": "3/4", "f2":"1/4" }
     */
    @POST
    @Path("/addTwoFractions")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTwoFractions(AddTwoFractions addTwoFractions) throws Exception {
        try {
            LOGGER.log(Level.INFO, "Entering addTwoFractions()");
            if (addTwoFractions == null)
                throw new Exception("addTwoFractions is null");

            if (addTwoFractions.getFraction1() == null)
                throw new Exception("Fraction 1 is null");

            if (addTwoFractions.getFraction2() == null)
                throw new Exception("Fraction 2 is null");

            addTwoFractions.add();

            return Response.ok(addTwoFractions.getSum()).type(MediaType.APPLICATION_JSON).build();
        } finally {
            LOGGER.log(Level.INFO, "Exiting addTwoFractions()");
        }
    }

    /*
    Sample Input:
    { "creditCardStr": "xxxxx" }
     */
    @POST
    @Path("/validateCreditCard")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateCreditCard(CreditCard creditCard) throws Exception {
        try {
            LOGGER.log(Level.INFO, "Entering validateCreditCard()");
            if (creditCard == null)
                throw new Exception("Pass a valid credit card in query parameter");
            creditCard.validateCC();
            Gson gson = new Gson();
            String json = gson.toJson(creditCard.isValidCreditCard(), new TypeToken<Boolean>() {
            }.getType());
            return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        } finally {
            LOGGER.log(Level.INFO, "Exiting validateCreditCard()");
        }
    }

    /*
    Sample Input:
    { "unsortedGrades": ["A", "A+", "D", "B+", "C", "B+", "A", "E"] }
     */
    @POST
    @Path("/sortGrades")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortGrades(GradeSort grades) throws Exception {
        try {
            LOGGER.log(Level.INFO, "Entering sortGrades()");
            if (grades == null)
                new Exception("List of strArr shouldn't be null");
            grades.sortGrades();
            Gson gson = new Gson();
            String json = gson.toJson(grades.getSortedGrades(), new TypeToken<List>() {
            }.getType());
            return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
        } finally {
            LOGGER.log(Level.INFO, "Exiting sortGrades()");
        }
    }
}
