package com.bazigar.bulandawaaz.bulk_sms;

import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class BulkSMSService {

    public Response sendOTP(String phoneNo, String message) throws IOException {
        try {
            String data="user=" + URLEncoder.encode("abhayshrivastav00786", StandardCharsets.UTF_8);
            data +="&password=" + URLEncoder.encode("Bazigar@786", StandardCharsets.UTF_8);
            data +="&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);
            data +="&mobile=" + URLEncoder.encode(phoneNo, StandardCharsets.UTF_8);
            data +="&type=" + URLEncoder.encode("3", StandardCharsets.UTF_8);

            // Send data
            URL url = new URL("https://login.bulksmsgateway.in/textmobilesmsapi.php?"+data);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sResult1 = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                // Process line...
                sResult1.append(line).append(" ");
            }
            wr.close();
            rd.close();
            System.out.println(sResult1);
            return new Response(
                    "OTP sms successfully sent!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    sResult1.toString()
            );
        }
        catch (Exception e) {
            return new Response(
                    "Error has occured!",
                    new ResponseStatus(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                    ),
                    e.getStackTrace()
            );
        }
//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("application/*+json");
//        Request request = new Request.Builder()
//                .url("http://api.bulksmsgateway.in/textmobilesmsapi.php?user=abhayshrivastav00786&password=Bazigar@786&mobile="+phoneNo+"&message="+message)
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/*+json")
//                .build();
//        okhttp3.Response response = client.newCall(request).execute();
//        System.out.println(response.code());
//        System.out.println(response.body());
//        System.out.println(response.message());
//        return new Response(
//                "Here is the response!",
//                new ResponseStatus(
//                        HttpStatus.OK.value(),
//                        HttpStatus.OK.getReasonPhrase()
//                ),
//                response.body()
//        );
    }
}
