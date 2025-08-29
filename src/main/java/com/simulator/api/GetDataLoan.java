package com.simulator.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataLoan implements HttpClient {
  @Override
  public String get() throws IOException {
    URL uRL = new URL("https://vehicle-loan.free.beeceptor.com/v3/9108b1da-beec-409e-ae14-e212003666c");
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
    httpURLConnection.setRequestMethod("GET");
    httpURLConnection.setConnectTimeout(10000);
    httpURLConnection.setReadTimeout(10000);
    
    int responseCode = httpURLConnection.getResponseCode();
    if (responseCode == 200) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
      StringBuffer stringBuffer = new StringBuffer();
      String str;
      while ((str = bufferedReader.readLine()) != null)
        stringBuffer.append(str); 
      bufferedReader.close();
      return stringBuffer.toString();
    } 
    return "ERROR";
  }
}