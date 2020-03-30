package com.ddk.karthi.server;

import com.ddk.karthi.apps.AddTwoFractions;
import com.ddk.karthi.apps.AddTwoLinkedList;
import com.ddk.karthi.apps.CreditCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wordnik.swagger.annotations.ApiParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;

@Path("karthiservice/v1/")
public class Resource {
    private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    @GET
    @Path("version")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mavenVersion() throws Exception {
        LOGGER.log(Level.INFO, "Entering mavenVersion()");
        String version = null;

        // try to load from maven properties first
        Properties p = new Properties();
        InputStream is = getClass().getResourceAsStream("/META-INF/maven/karthi-service/karthi-service/pom.properties");
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
            version = "";
        }
        return Response.ok(version).build();
    }

    @GET
    @Path("addtwolinkedlist")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTwoLinkedList(
            @ApiParam(name = "num1",
            value = "Number 1",
            required = true) @QueryParam("num1") String num1,
            @ApiParam(name = "num2",
                    value = "Number 2",
                    required = true) @QueryParam("num2") String num2
    ) throws Exception {
        if ( num1 == null )
            throw new Exception("Number 1 is null");
        if ( num2 == null )
            throw new Exception("Number 2 is null");

        int maxLength = (num1.length() > num2.length()) ? num1.length() : num2.length();
        System.out.println("MaxLen: " + maxLength + ", num1: " + num1 + ", num2: " + num2);
        String format = "%0" + maxLength + "d";
        System.out.println("Format: " + format);
        num1 = String.format(format, Integer.parseInt(num1));
        System.out.println("MaxLen: " + maxLength + ", num1: " + num1 + ", num2: " + num2);
        num2 = String.format(format, Integer.parseInt(num2));
        System.out.println("MaxLen: " + maxLength + ", num1: " + num1 + ", num2: " + num2);

        int[] l1 = new int[num1.length()];
        int[] l2 = new int[num2.length()];

        for ( int i = num1.length()-1, j = 0 ; i >= 0 ; i--, j++ )
            l1[j] = num1.charAt(i) - '0';

        for ( int i = num2.length()-1, j = 0 ; i >= 0 ; i--, j++ )
            l2[j] = num2.charAt(i) - '0';

        AddTwoLinkedList addTwoLinkedList = new AddTwoLinkedList(l1, l2);

        // Add LinkedLists
        addTwoLinkedList.addTwoNumbers();
        // addTwoLinkedList.printNodes();
        return Response.ok(addTwoLinkedList.getResult()).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("addTwoFractions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTwoFractions(
            @ApiParam(name = "frac1",
                    value = "Fraction 1",
                    required = true) @QueryParam("f1") String f1,
            @ApiParam(name = "f2",
                    value = "Fraction 2",
                    required = true) @QueryParam("f2") String f2
    ) throws Exception {
        if ( f1 == null )
            throw new Exception("Fraction 1 is null");

        if ( f2 == null )
            throw new Exception("Fraction 2 is null");

        String[] f1Split = f1.split("/");
        if ( f1Split.length != 2 )
            throw new Exception("Fraction 1 is not a valid fraction");

        String[] f2Split = f2.split("/");
        if ( f2Split.length != 2 )
            throw new Exception("Fraction 2 is not a valid fraction");

        Integer[] fr1 = new Integer[2];
        fr1[0] = Integer.parseInt(f1Split[0]);
        fr1[1] = Integer.parseInt(f1Split[1]);

        Integer[] fr2 = new Integer[2];
        fr2[0] = Integer.parseInt(f2Split[0]);
        fr2[1] = Integer.parseInt(f2Split[1]);

        AddTwoFractions addTwoFractions = new AddTwoFractions(fr1, fr2);

        return Response.ok(addTwoFractions.toString()).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("validateCreditCard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateCreditCard(
            @ApiParam(name = "creditCard",
                    value = "Company User ID",
                    required = true) @QueryParam("creditCard") String creditCard
            ) throws Exception {
        if ( creditCard == null )
            throw new Exception("Pass a valid credit card in query parameter");
        CreditCard cc = new CreditCard(creditCard);
        Gson gson = new Gson();
        String json = gson.toJson(cc, new TypeToken<CreditCard>() {}.getType());
        return Response.ok(json).type(MediaType.APPLICATION_JSON).build();
    }
}
