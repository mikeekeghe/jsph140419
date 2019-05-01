/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.techline.instauserswitcher;

import static com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults.Encoding;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.client.ClientProtocolException;
import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramEditProfileRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSearchUsernameRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramGetCurrentUserProfileResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramLoginResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramSearchUsernameResult;
import org.brunocvcunha.instagram4j.requests.payload.InstagramUserGenderEnum;

/**
 *
 * @author MIKE
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private Button btnStart;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Label lblCount;
    @FXML
    private TextField txtMilliseconds;
    @FXML
    private TextField txtVictim;

    int counter = 0;
    String strCounter = "";
    String myUser = "", myPass = "", myVictim = "";
    InstagramLoginResult completeLoginResult;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleStartButtonAction(ActionEvent event) throws IOException, ClientProtocolException, InterruptedException, Exception {
        myUser = txtUser.getText();
        myPass = txtPass.getText();
        myVictim = txtVictim.getText();
        counter = Integer.parseInt(lblCount.getText());

        // Login to instagram
        Instagram4j instagram = Instagram4j.builder().username(myUser).password(myPass).build();
        instagram.setup();
        completeLoginResult = instagram.login();
        System.out.println("connected succesfully as " + myUser);

        searchByUser(instagram, myVictim);

        /*replaceUser(myVictim);*/
    }

    private void searchByUser(Instagram4j instagram, String myVictim) throws InterruptedException, IOException, Exception {
        InstagramSearchUsernameResult userResult = instagram.sendRequest(new InstagramSearchUsernameRequest(myVictim));
        String theVictim = "";
        theVictim = myVictim;
        String myMessage = userResult.getMessage();
        System.out.println("myMessage is " + myMessage);
        String myStatus = userResult.getStatus();
        if (myStatus.equalsIgnoreCase("OK")) {
            System.out.println("myStatus is " + myStatus);
            System.out.println("status is oK run again");
            //increment counter
            counter++;
            System.out.println("counter is " + counter);
            lblCount.setText(String.valueOf(counter));
            //strCounter);
            //run again
            searchByUser(instagram, myVictim);
        } else {
            System.out.println("its not ok, pick the user");
            replaceUser(theVictim);
        }

    }

    private void replaceUser(String myVictim) throws InterruptedException, Exception {
        System.out.println("inside replace user");
//        boolean finalBoolOutput = replaceUserVb(myVictim);
        boolean finalBoolOutput = newReplaceUser(myVictim);
        System.out.println("finalBoolOutput >>" + finalBoolOutput);
        System.out.println("done!");
        // i 
    }

    private boolean replaceUserVb(String myVictim) throws IOException, ClientProtocolException, InterruptedException, Exception {
        System.out.println("inside replaceUserVb");
        boolean result = false;
        String string_11 = "5ad7d6f013666cc93c88fc8af940348bd067b68f0dce3c85122a923f4f74b251";
        String str = "signed_body=";
        StringBuilder text = new StringBuilder("signed_body=");
        text.append("{\"\"gender\"\":\"\"1\"");
        text.append("\"_csrftoken\"\":\"\"missing\"");
        text.append("\"_uuid\"\":\"\"");
        String guiid = randomAlphaNumeric(16);
        System.out.println("guiid >>" + guiid);
        text.append(guiid);
        text.append("\"\",\"\"_uid\"\":\"\"3\"");
        text.append("\"external_url\"\":\"\"www.instagram.com/0k0k\"\",\"\"username\"\":\"\"");
        text.append(myVictim);
        text.append("\"");
        text.append("\"email\"\":\"\"");
        text.append("css.nigeria@gmail.com");
        text.append("\"");
        text.append("\"phone_number\"\":\"\"\"");
        text.append("\"biography\"\":\"\"\"");
        text.append("\"first_name\"\":\"\"L0N3LY\"\"}");
        System.out.println("text >>" + text.toString());
        String mainText = text.toString();
        //encrypt mainText with key string_11)
        String str2 = sMethod_4(string_11, mainText);
        String str3 = "&ig_sig_key_version=5";

        URL sendUrl = new URL("https://i.instagram.com/api/v1/accounts/edit_profile/");
        HttpURLConnection httpConnection = null;
        try {
            httpConnection = (HttpURLConnection) sendUrl.openConnection();
        } catch (Exception e) {
            System.out.println("Something went wrong.>> " + e);
        } finally {
            httpConnection.setRequestMethod("POST");
            httpConnection.setAllowUserInteraction(true);
            String cookiesHeader = httpConnection.getHeaderField("Set-Cookie");
            List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setRequestProperty("User-Agent", "Instagram 10.3.2 Android (18/4.3; 320dpi; 720x1280; Xiaomi; HM 1SW; armani; qcom; en_US)");
            httpConnection.setRequestProperty("User-Agent", "Instagram 7.3.1 Android (21/5.0.1; 480dpi; 1080x1920; samsung/Verizon; SCH-I545; jfltevzw; qcom; en_US)");
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setConnectTimeout(7500);
            httpConnection.setReadTimeout(7500);
            httpConnection.setRequestProperty("X-IG-Connection-Type", "WIFI");
            httpConnection.setRequestProperty("X-IG-Capabilities", "3ToAAA==");

            httpConnection.setDoOutput(true);

            DataOutputStream dataStreamToServer = new DataOutputStream(httpConnection.getOutputStream());
            dataStreamToServer.writeBytes(str + str2 + str3);
            dataStreamToServer.flush();
            dataStreamToServer.close();
// Here take the output value of the server.
            BufferedReader dataStreamFromUrl = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String dataFromUrl = "", dataBuffer = "";
// Writing information from the stream to the buffer
            while ((dataBuffer = dataStreamFromUrl.readLine()) != null) {
                dataFromUrl += dataBuffer;
            }
            /**
             * Now dataFromUrl variable contains the Response received from the
             * * server so we can parse the response and process it accordingly.
             */
            dataStreamFromUrl.close();
            System.out.println("Response: " + dataFromUrl);

            System.out.println("After geting and closing httpconnection");
            result = true;
        }
//        httpConnection.setDoInput(true);

        return result;
    }

    public static String sMethod_4(String key, String mainText) throws Exception {
        System.out.println("inside sMethod_4");
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        String output = Hex.encodeHexString(sha256_HMAC.doFinal(mainText.getBytes("UTF-8")));
        System.out.println("encrypted output is >>" + output);
        return output;
    }

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private boolean newReplaceUser(String myVictim) throws IOException {
        Instagram4j instagram = Instagram4j.builder().username(myUser).password(myPass).build();
        instagram.setup();
        completeLoginResult = instagram.login();
        System.out.println("connected succesfully as " + myUser);

        InstagramGetCurrentUserProfileResult userResult = instagram.sendRequest(new InstagramEditProfileRequest("","","","",myVictim,"css.nigeria@gmail.com", InstagramUserGenderEnum.MALE));
        return true;
    }

}
