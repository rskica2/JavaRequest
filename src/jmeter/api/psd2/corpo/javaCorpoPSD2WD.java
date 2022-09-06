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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author robert skica
 * Klasa wykorzystuje standardowy interfejs umożliwiający współpraę 
 * z narzędziem JMeter
 * 
 * 
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
            WebDriver driver = null; 
            
            try {    

                // Write your test code here.
                
                // Ustawienie odpowiedniego sposobu okrzystania z certyfikatów 
                // do autentykacji (logowanie) z wykorzystaniem przeglądarki 
                // Firefox

                ProfilesIni profile = new ProfilesIni();
                FirefoxProfile ffProfile = profile.getProfile("default"); 
                ffProfile.setPreference("security.default_personal_cert", "Select Automatically");
                ffProfile.setAcceptUntrustedCertificates(true);
                
                FirefoxOptions ffOptions = new FirefoxOptions ();
                ffOptions.setProfile(ffProfile);
                
                // Przygotowanie do realizacji testu i pobranie zmiennych 
                // z JMeter-a
                
                String nik = arg0.getJMeterVariables().get("nik");
                
                // Zmienna o nazwie authorize_redirect_1 zawiera link do przeglądarki
                // zwracany z użycia metody /euthorize na API
                
                String authorize_redirect_1 = arg0.getJMeterVariables().get("authorize_redirect_1");
                String iter = arg0.getJMeterVariables().get("iter_psd2_view_ui_for_javarequest");
                
                // uwaga: zmienna j została wprowadzona tylko i wyłącznie w celach pomocniczych
                // na testach wymagane było 2-krotne przelogowanie, aby uzyskac dostęp do 
                // zatwierdzeania transakcji - na produkcji problem nie występuje
                
                int j = Integer.valueOf(iter);
                
                // Poniższe cztery linie wykorzystywano wyłącznie na potrzeby debugowania kodu
                // System.out.println("chkp 1 : pobrano zmienne z JMeter-a");    
                // System.out.println("nik = " + nik);    
                // System.out.println("authorize_redirect_1 = " + authorize_redirect_1);    
                // System.out.println("iter_psd2_view_ui_for_javarequest = " + iter);    
               
                // Startujemy przeglądarkę Firefox
                
                driver = new FirefoxDriver(ffOptions); 
                
                // Liczba przelogowań 
                // Dla produkcji dokładnie 1 raz wykonujemy zawartość
                
                for (int i=0; i<j; i++) {
                
                // Otwieramy w przeglądarce odpowiedni link 
                // Ustawiamy też odpowiednie parametry, jak czas oczekiwania (timeout) 
                // na wyszukiwanych elementach
 
                driver.get(authorize_redirect_1);               
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
                driver.manage().window().maximize();
                Thread.sleep(3000);   
                
                // Szukamy na załadowanej stronie wskazanego elementu do wprowadzenia 
                // loginu i uzupełniamy pole edycyjne odpowiednią wartością pobraną 
                // ze zmiennych JMeter (nik)
                
                WebElement _login = driver.findElement(By.id("loginId"));
                _login.sendKeys(nik);
                
                // Wyszukujemy klawisz i aktywujemy jego naciśnięcie 
                
                WebElement _button = driver.findElement(By.id("login"));
                _button.click();
                Thread.sleep(10000);                
                
                }
                // System.out.println("chkp 2 : widzimy operację dla PSD2");
                
                // Kolejny krok operacji to zainicjowanie na ekranie operacji Wykonaj 
                // po wcześniejszym przesunięciu ekranu (widok klawisza)
                
                JavascriptExecutor js = (JavascriptExecutor)driver;
                js.executeScript("window.scrollBy(0,500)");
                WebElement __button = driver.findElement(By.id("actionButton_fakeExecute"));
                __button.click(); 
                Thread.sleep(5000);
                
                // Odczytujemy następnie parametr code z URL-a zwracanego w przeglądarce
                // wartość ta stanowi tzw. OTP i jest wykorzystywana w kolejnym kroku na API,
                // gdzie wołamy metodę /token
              
                Duration duration1 = Duration.ofSeconds(10);
                new WebDriverWait(driver, duration1).until(ExpectedConditions.urlContains("code"));
                String fullUrl = driver.getCurrentUrl();
                //String[] urlParts = fullUrl.split("=");
                
                //System.out.println("chkp 3 : fullUrl=" + fullUrl);
                
                String pattern = "code=";
                
                // Z URL-a wycinamy tylko zawartość parametru code, który musimy następnie przekazać 
                // na zewnątrz do parametrów JMeter-a i dalej wykorzystać w sposób standardowy
                
                String code = fullUrl.substring(fullUrl.indexOf("code=")+5,fullUrl.indexOf('&'));
                
                //System.out.println("code variable = " + code);
                //i = fullUrl.indexOf("&");
                //String tmp1 = tmp.substring(0, i);
                //System.out.println("code variable B = " + tmp1);
                
                //String[] urlParts2 = tmp1.split("&");
                //String tmp2 = urlParts[1];
                //System.out.println("split 3 = " + tmp2);
                
                arg0.getJMeterVariables().put("code", code); 
                 
                result.sampleEnd();
                //result.setSampleLabel("JavaRequest to obtain code variable");
                //result.setSamplerData(nik);                
                result.setSuccessful(success);
                
            } catch (Exception e) {
                System.out.println("*** error from JavaRequest***");
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                if (driver != null) {
                    
                    // Zamykamy przeglądarkę Firefox
                    
                    driver.quit();
                }
            }

            return result;
        }

        @Override
        public void teardownTest(JavaSamplerContext context){
            // TODO Auto-generated method stub
            super.teardownTest(context);
        }  
    
    
}
