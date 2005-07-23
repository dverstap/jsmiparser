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

package org.jsmiparser.parsetree.asn1;

/**
 *
 * @author  Nigel Sheridan-Smith
 */
public class ASNValueRange {

    private ASNValue lowerValue;
    private ASNValue upperValue;
    private boolean minValue;
    private boolean maxValue;
    private boolean lessThan;
    
    /** Creates a new instance of ASNValueRange */
    public ASNValueRange() {
    }
    
    public void setLowerValue (ASNValue v)
    {
        lowerValue = v;
    }
    
    public ASNValue getLowerValue ()
    {
        return lowerValue;
    }
    
    public void setUpperValue (ASNValue v)
    {
        upperValue = v;
    }
    
    public ASNValue getUpperValue ()
    {
        return upperValue;
    }
    
    public void setMinValue (boolean m)
    {
        minValue = m;
    }
    
    public boolean isMinValue ()
    {
        return minValue;
    }
    
    public void setMaxValue (boolean m)
    {
        maxValue = m;
    }
    
    public boolean isMaxValue ()
    {
        return maxValue;
    }
    
    public void setLessThan (boolean l)
    {
        lessThan = l;
    }
    
    public boolean isLessThan ()
    {
        return lessThan;
    }
}
