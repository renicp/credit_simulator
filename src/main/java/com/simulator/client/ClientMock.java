package com.simulator.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientMock {
  public String GETRequest() throws IOException {
    URL uRL = new URL("https://vehicle-loan.free.beeceptor.com/v3/9108b1da-beec-409e-ae14-e212003666c");
    String str = null;
    HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
    httpURLConnection.setRequestMethod("GET");
    int i = httpURLConnection.getResponseCode();
    if (i == 200) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
      StringBuffer stringBuffer = new StringBuffer();
      while ((str = bufferedReader.readLine()) != null)
        stringBuffer.append(str); 
      bufferedReader.close();
      return stringBuffer.toString();
    } 
    return "ERROR";
  }
}