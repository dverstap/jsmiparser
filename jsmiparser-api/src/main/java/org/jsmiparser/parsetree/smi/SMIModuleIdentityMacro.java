/*
 * Copyright 2005 Nigel Sheridan-Smith, Davy Verstappen.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsmiparser.parsetree.smi;

import java.util.*;

import org.jsmiparser.parsetree.asn1.Context;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class SMIModuleIdentityMacro extends SMIMacro {
    
    private SMICategories categories;
    private String lastUpdated;
    private String organization;
    private String contactInfo;
    private List<SMIRevision> revisions = new ArrayList<SMIRevision>();;
    
    /** Creates a new instance of SMIModuleIdentityMacro */
    public SMIModuleIdentityMacro(Context context) {
        super(context, Enum.SMIMODULEIDENTITYMACRO);
    }
    
    public void setCategories (SMICategories c)
    {
        categories = c;
    }
    
    public SMICategories getCategories ()
    {
        return categories;
    }
    
    public void setLastUpdated (String l)
    {
        lastUpdated = l;
    }
    
    public String getLastUpdated ()
    {
        return lastUpdated;
    }
    
    public void setOrganization (String o)
    {
        organization = o;
    }
    
    public String getOrganization ()
    {
        return organization;
    }
    
    public void setContactInfo (String c)
    {
        contactInfo = c;
    }
    
    public String getContactInfo ()
    {
        return contactInfo;
    }
    
    public void setRevisions (List<SMIRevision> r)
    {
        revisions = r;
    }
    
    public List<SMIRevision> getRevisions ()
    {
        return revisions;
    }
    
}
