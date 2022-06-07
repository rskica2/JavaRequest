/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmeter.example;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.*;
import org.apache.jmeter.threads.JMeterVariables;

/**
 *
 * @author rober
 */
public class javaRequestExample extends AbstractJavaSamplerClient {

        private String _uuid;
    
        private String genarateNewAppId() {
            String uuid = "";
            uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0, 30);
            return uuid;
        }    
    
@Override
        public void setupTest(JavaSamplerContext context){
        // TODO Auto-generated method stub

        
        super.setupTest(context);
        }
        
        @Override
        public Arguments getDefaultParameters() {
        // TODO Auto-generated method stub  
            return super.getDefaultParameters();

        }

        @Override
        public SampleResult runTest(JavaSamplerContext arg0) {
            // TODO Auto-generated method stub

            SampleResult result = new SampleResult();

                boolean success = true;

                result.sampleStart();

                // Write your test code here.

                
                _uuid = genarateNewAppId();
                
/*                try {

                      URL url = new URL("http://ec2-3-126-200-228.eu-central-1.compute.amazonaws.com:8080/jaxrs-cabp-statuspage/api/messages");
                      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                      conn.setRequestMethod("GET");
                      conn.setRequestProperty("Accept", "application/json");

                      if (conn.getResponseCode() != 200) {
                              throw new RuntimeException("Failed : HTTP error code : "
                                              + conn.getResponseCode());
                      }

                      BufferedReader br = new BufferedReader(new InputStreamReader(
                              (conn.getInputStream())));

                      String output;
                      System.out.println("Output from Server .... \n");
                      while ((output = br.readLine()) != null) {
                              System.out.println(output);
                      }

                      conn.disconnect();

                } catch (MalformedURLException e) {

                      e.printStackTrace();

                } catch (IOException e) {

                      e.printStackTrace();

                }
                
                //
*/

                //SampleResult resultUUID = new SampleResult();
                //resultUUID.setSuccessful(true);
                //resultUUID.setSampleLabel("UUID");
                //resultUUID.setSamplerData(_uuid);
                //result.addSubResult(resultUUID);
                
                //result.setsetBytes example
                
                //result.setSamplerData(_uuid);
                System.out.println(_uuid);
                result.setSampleLabel("_uuid");
 
                
                result.sampleEnd();

                result.setSuccessful(success);

                return result;

        }

        @Override
        public void teardownTest(JavaSamplerContext context){
            // TODO Auto-generated method stub
            super.teardownTest(context);
        }  
}
