/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.custom.user.administrator.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.wso2.carbon.custom.user.administrator.CustomUserAdministrator;
import org.wso2.carbon.user.core.service.RealmService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static org.wso2.carbon.custom.user.administrator.Constants.ADD_USER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(name = "org.wso2.carbon.identity.custom.user.list.component",
           immediate = true)
public class CustomUserAdministratorServiceComponent {

	
    private static final Log log = LogFactory.getLog(CustomUserAdministratorServiceComponent.class);
   
    String userName="";
    Object credential ="";
    String roleList[]= {""};
    String email="";
    String country="";
    String mobile="";
    String givenname="";
    Map<String,String> hm = new HashMap<>();
   
    
   
   
    
    
    
   
    
    
    @Activate
    protected void activate(ComponentContext context) {

        try {
        	   log.info("************************************************");
         //   boolean addUser = Boolean.parseBoolean(System.getProperty(ADD_USER));
        	   String line = "";  
       		String splitBy = ",";  
       		int headerRow=0;
       	
       		try   
       		{  
       		//parsing a CSV file into BufferedReader class constructor  
       			CustomUserAdministrator administrator = new CustomUserAdministrator();
       		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\V310\\Desktop\\Test.csv"));  
       		while ((line = br.readLine()) != null)   //returns a Boolean value  
       		{  
       			if(headerRow==0)
       			{
       				headerRow++;
       			continue;	
       			}
       				
       		String[] employee = line.split(splitBy);    // use comma as separator  
       	//	System.out.println("Employee [LoginID=" + employee[0] + ", Password=" + employee[1] + ", GivenName=" + employee[2] + ", Mobile=" + employee[3] + ", Email= " + employee[4] + ", Country= " + employee[5] +"]");  
       		userName=employee[0];
       		credential=employee[1];
       		givenname=employee[2];
       		email=employee[4];
       		country=employee[5];
       		mobile=employee[3];
       		hm.put("http://wso2.org/claims/givenname",givenname);
 	       hm.put("http://wso2.org/claims/emailaddress",email);
 	      hm.put("http://wso2.org/claims/country",country);
 	     hm.put("http://wso2.org/claims/mobile",mobile);
  	      
       		//	}  
       		
       		   
       		
                
                administrator.addUser(userName,credential,roleList,hm,null);
                log.info("Custom component is triggered.");
       		}    
        }
       		catch (IOException e)   
       		{  
       		e.printStackTrace();  
       		}  
            if (log.isDebugEnabled()) {
                log.debug("Custom component is activated.");
            }
        } catch (Throwable e) {
            log.error("Error activating the custom component", e);
        }
        
    }

    @Deactivate
    protected void deactivate(ComponentContext cxt) {

        if (log.isDebugEnabled()) {
            log.debug("Custom component is deactivated.");
        }
    }

    @Reference(name = "realm.service",
               service = org.wso2.carbon.user.core.service.RealmService.class,
               cardinality = ReferenceCardinality.MANDATORY,
               policy = ReferencePolicy.DYNAMIC,
               unbind = "unsetRealmService")
    protected void setRealmService(RealmService realmService) {

        // Custom user administrator bundle depends on the Realm Service
        // Therefore, bind the realm service
        if (log.isDebugEnabled()) {
            log.debug("Setting the Realm Service");
        }
        CustomUserAdministratorDataHolder.getInstance().setRealmService(realmService);
    }

    protected void unsetRealmService(RealmService realmService) {

        if (log.isDebugEnabled()) {
            log.debug("Unset the Realm Service.");
        }
        CustomUserAdministratorDataHolder.getInstance().setRealmService(null);
    }
}
