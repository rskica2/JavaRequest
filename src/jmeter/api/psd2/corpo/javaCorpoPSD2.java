/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmeter.api.psd2.corpo;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.event.Event;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.Assert;

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
                String authorize_redirect_1 = arg0.getJMeterVariables().get("authorize_redirect_1");

                System.out.println("chkp 1 : pobrano zmienne z JMeter-a");    
                System.out.println("nik = " + nik);    
                System.out.println("authorize_redirect_1 = " + authorize_redirect_1);    
               
                // set ignore insecure ssl
                System.setProperty("jsse.enableSNIExtension", "false");
                
//                System.setProperty("javax.net.ssl.trustStore", "D:\\Certy_prod\\p26\\p26_keystore.jks");
//                System.setProperty("javax.net.ssl.trustStorePassword", "export");
//                System.setProperty("javax.net.ssl.trustStoreType", "JKS");
                System.out.println("chkp 2 : wystartowano symulację przeglądarki Firefox");    
                final WebClient webClient = new WebClient(BrowserVersion.FIREFOX);
                try {
                    
                    // set ignore insecure ssl
                    webClient.getOptions().setUseInsecureSSL(true);
                    webClient.getOptions().setThrowExceptionOnScriptError(false);
                    webClient.getOptions().setRedirectEnabled(true);
                    webClient.getOptions().setJavaScriptEnabled(true);
                    webClient.getCookieManager().setCookiesEnabled(true);
                    HtmlPage page = webClient.getPage(authorize_redirect_1);
                    
                    System.out.println("chkp 3 : załadowano stronę z wykorzystaniem redirect");    
                    //System.out.println("page.getTitleText = " + page.getTitleText()); 
                    System.out.println(page.asXml());
                    
                    HtmlForm form = page.getFormByName("ActionForm");
                    HtmlTextInput textField = form.getInputByName("loginId");
                    textField.setValueAttribute(nik);
                    //System.out.println(form.asNormalizedText());
                    HtmlSubmitInput button = (HtmlSubmitInput) form.getFirstByXPath("//input[@value='Logging into the system']");
                    // 

                    System.out.println("chkp 4 : uzupełniono dane do logowania na stronie");    
                    System.out.println(form.asNormalizedText());

                    System.out.println("chkp 5 : zainicjowano naciśnięcie klawisza logowania do systemu");    

                    form.fireEvent(Event.TYPE_SUBMIT);
                    //button.click(); 
                    Thread.sleep(15000);
                    
                    HtmlPage page2 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
                    System.out.println("chkp 6 : załadowano stronę autoryzacji przelewu");    
                    System.out.println(page2.asXml());
                    System.out.println("chkp 7");    

                    String pageAsText = page2.asNormalizedText();
                    System.out.println("chkp 8");    
                    Assert.assertTrue(pageAsText.contains("PIS - przelew krajowy"));
                    
                    
                } catch (Exception e) {
                    System.out.println("error message = " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    webClient.close();
                }
                
                //


                result.sampleEnd();

                result.setSampleLabel("JavaRequest nr 1 authorize_redirect");
                //result.setSamplerData(nik);                
                result.setSuccessful(success);

                
                return result;

        }

        @Override
        public void teardownTest(JavaSamplerContext context){
            // TODO Auto-generated method stub
            super.teardownTest(context);
        }  
        
    
}
