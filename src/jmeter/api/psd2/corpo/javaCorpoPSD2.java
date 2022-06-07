/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmeter.api.psd2.corpo;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterVariables;

/**
        After comp iling your classes package it to .jar and drop to /lib/ext folder of your JMeter installation. 
        Your class should be available from Java Request drop down.
         
        https://jmeter.apache.org/api/org/apache/jmeter/protocol/java/sampler/JavaSamplerClient.html
        https://jmeter.apache.org/api/org/apache/jmeter/protocol/java/sampler/AbstractJavaSamplerClient.html
        https://jmeter.apache.org/usermanual/jmeter_accesslog_sampler_step_by_step.html
        
**/
public class javaCorpoPSD2 extends AbstractJavaSamplerClient {
    
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

                String nik = arg0.getJMeterVariables().get("nik");
                
                
                //


                result.sampleEnd();

                result.setSampleLabel("nik");
                result.setSamplerData(nik);                
                result.setSuccessful(success);

                System.out.println("nik = " + nik);    
                return result;

        }

        @Override
        public void teardownTest(JavaSamplerContext context){
            // TODO Auto-generated method stub
            super.teardownTest(context);
        }  
        
    
}
