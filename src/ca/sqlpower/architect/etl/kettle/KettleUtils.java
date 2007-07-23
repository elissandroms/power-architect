/*
 * Copyright (c) 2007, SQL Power Group Inc.
 * 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of SQL Power Group Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package ca.sqlpower.architect.etl.kettle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;

import ca.sqlpower.architect.ArchitectDataSource;
import ca.sqlpower.architect.ArchitectDataSourceType;

public class KettleUtils {

    private static final Logger logger = Logger.getLogger(KettleUtils.class);
    
    public static List<String> retrieveKettleConnectionTypes() {
        List<String> list = new ArrayList<String>();
        DatabaseInterface[] dbConnectionArray = DatabaseMeta.getDatabaseInterfaces();
        for (int i = 0; i < dbConnectionArray.length; i++) {
            list.add(dbConnectionArray[i].getDatabaseTypeDescLong());
        }
        return list;
    }
    
    /**
     * Creates a DatabaseMeta object based on the ArchitectDataSource given to it.
     * This will return null if an error occurred and execution should stop. 
     * 
     * @param parent The parent JFrame used for showing dialog windows.
     */
    public static DatabaseMeta createDatabaseMeta(ArchitectDataSource target) throws RuntimeException {
        DatabaseMeta databaseMeta;
        
        String databaseName = target.getName();
        String username = target.getUser();
        String password = target.getPass();
        ArchitectDataSourceType targetType = target.getParentType();
        String connectionType = targetType.getProperty(KettleOptions.KETTLE_CONNECTION_TYPE_KEY); 
        Map<String, String> map = targetType.retrieveURLParsing(target.getUrl());
        String hostname = map.get(KettleOptions.KETTLE_HOSTNAME);
        if (hostname == null) {
            hostname = target.get(KettleOptions.KETTLE_HOSTNAME_KEY);
        }
        String port = map.get(KettleOptions.KETTLE_PORT);
        if (port == null) {
            port = target.get(KettleOptions.KETTLE_PORT_KEY);
        }
        String database = map.get(KettleOptions.KETTLE_DATABASE);
        if (database == null) {
            database = target.get(KettleOptions.KETTLE_DATABASE_KEY);
        }
        
        databaseMeta = new DatabaseMeta(databaseName
                                              , connectionType
                                              , "Native"
                                              , hostname==null?"":hostname
                                              , database==null?"":database
                                              , port==null?"":port
                                              , username
                                              , password);
        

        return databaseMeta;
    }
}