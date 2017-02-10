package com.scubacode.library.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by htorres on 14/08/2016.
 */
public class ActivityFragmentFormBase extends ActivityFragmentBase
{
    protected List<IValidator> ruleSet = new ArrayList<IValidator>();

    protected  void initializeFormFields()
    {

    }

    protected void addValidator(IValidator v)	{
        ruleSet.add(v);
    }

    protected Boolean validateForm()
    {
        Boolean finalResult = true;
        for(IValidator v:ruleSet)
        {
            Boolean result = v.validate();
            if (result == false)
            {
                finalResult = false;
            }
        }
        return finalResult;
    }
}
