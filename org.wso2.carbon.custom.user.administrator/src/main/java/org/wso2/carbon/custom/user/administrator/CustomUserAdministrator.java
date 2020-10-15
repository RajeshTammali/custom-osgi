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

package org.wso2.carbon.custom.user.administrator;






import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.custom.user.administrator.internal.CustomUserAdministratorDataHolder;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * Custom OSGI bundle implementation
 */
public class CustomUserAdministrator {

    private static final Log log = LogFactory.getLog(CustomUserAdministrator.class);
	private String[] rolesList;

  
   
    public void addUser(String userName,Object credential,String[] rolesList,  Map<String, String> hm, String profileName) throws UserStoreException {
    	log.info("Starting the log");
    	        RealmService realmService = CustomUserAdministratorDataHolder.getInstance().getRealmService();
    	        int tenantId = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantId();
    	        UserStoreManager userStoreManager = realmService.getTenantUserRealm(tenantId).getUserStoreManager();
    	      
    	     
    	       userStoreManager.addUser(userName, credential, rolesList, hm, profileName);
    	      
    	   

    	    }

}
