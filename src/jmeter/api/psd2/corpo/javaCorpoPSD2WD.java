/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmeter.api.psd2.corpo;

import java.time.Duration;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.JavascriptExecutor;

/**
 *
 * @author rober
 */
public class javaCorpoPSD2WD extends AbstractJavaSamplerClient {

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

            try {    

                // Write your test code here.

                ProfilesIni profile = new ProfilesIni();
                FirefoxProfile ffProfile = profile.getProfile("default"); 
                ffProfile.setPreference("security.default_personal_cert", "Select Automatically");
                ffProfile.setAcceptUntrustedCertificates(true);
                
                FirefoxOptions ffOptions = new FirefoxOptions ();
                ffOptions.setProfile(ffProfile);
                
                String nik = arg0.getJMeterVariables().get("nik");
                String authorize_redirect_1 = arg0.getJMeterVariables().get("authorize_redirect_1");

                System.out.println("chkp 1 : pobrano zmienne z JMeter-a");    
                System.out.println("nik = " + nik);    
                System.out.println("authorize_redirect_1 = " + authorize_redirect_1);    
               

                WebDriver driver = new FirefoxDriver(ffOptions); 
                
                for (int i=0; i<2; i++) {
                
                driver.get(authorize_redirect_1);
                
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                driver.manage().window().maximize();
                Thread.sleep(3000);   
                
                System.out.println("chkp 2 : rozpoczynam wyszukiwanie elementu do wprowadzenia nik (login) [" + (i+1) + "]");    
                WebElement _login = driver.findElement(By.id("loginId"));
                _login.sendKeys(nik);
                System.out.println("chkp 2 : ustawiono nik (login) [" + (i+1) + "]");

                System.out.println("chkp 3 : rozpoczynam wyszukiwanie klawisza submit [" + (i+1) + "]");    
                WebElement _button = driver.findElement(By.id("login"));
                _button.click();
                System.out.println("chkp 4 : zainicjowano submit formularza logowania metodą click() [" + (i+1) + "]");                    
 
                Thread.sleep(10000);                
                
                }

                System.out.println("chkp 5 : rozpoczynam autoryzację operacji");    
                JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("window.scrollBy(0,500)");
                WebElement __button = driver.findElement(By.id("actionButton_fakeExecute"));
                __button.click(); 
                System.out.println("chkp 6 : wybrano zlecenie wykonania autoryzacji operacji");    
                
                Thread.sleep(15000);                  
                //driver.quit();
                
                result.sampleEnd();
                result.setSampleLabel("JavaRequest WebDriver");
                //result.setSamplerData(nik);                
                result.setSuccessful(success);
                
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

            return result;
        }

        @Override
        public void teardownTest(JavaSamplerContext context){
            // TODO Auto-generated method stub
            super.teardownTest(context);
        }  
    
    
}
