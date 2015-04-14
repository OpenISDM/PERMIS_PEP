package com.google.android.gcm.demo.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class HttpGetQuery {

	public Map<String, String> HTTPGetQuery(String hostURL) {
        // Declare a content string prepared for returning.
        String content = "";
        // Have an HTTP client to connect to the web service.
        HttpClient httpClient = new DefaultHttpClient();
        // Have an HTTP response container. 
        HttpResponse httpResponse = null;
        // Have map container to store the information.
        Map<String, String> map = new HashMap<String, String>();
        
        // This try & catch is prepared for the IO exception in case.
        try {
            // Have a post method class with the query URL.
            HttpGet httpQuery = new HttpGet(hostURL);
            // The HTTP client do the query and have the string type response.
            httpResponse = httpClient.execute(httpQuery);
            
            // Read the HTTP headers and into content.
            //for (Header header : httpResponse.getAllHeaders()) {
            //     content += "\n" + header.toString();
            //}
            // Read the HTTP response content as an encoded string.
            content += EntityUtils.toString(httpResponse.getEntity());
        }
        // Catch the HTTP exception.
        catch(ClientProtocolException ex) {
            content = "ClientProtocolException:" + ex.getMessage();
        }
        // Catch the any IO exception.
        catch(IOException ex) {
            content = "IOException:" + ex.getMessage();
        }
        // The HTTP connection must be closed any way.
        finally    {
            httpClient.getConnectionManager().shutdown();
        }
        
        // Check the HTTP connection is executed or not.
        if (httpResponse != null) {
            // Put the status code with status key.
            map.put("status", Integer.toString(httpResponse.getStatusLine().getStatusCode()));
            // Put the response content with content key
            map.put("content", content);
        }
        else {
            // Put the dummy with status key.
            map.put("status", "");
            // Put the dummy with content key
            map.put("content", "");
        }
        
        // Return result.
        return map;
    }
}
