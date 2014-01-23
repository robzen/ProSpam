package de.rob1n.prospam.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: robin
 * Date: 16.01.14
 */
public class StringList extends ArrayList<String>
{
    public StringList(Collection<String> c)
    {
        super(c);
    }

    public boolean containsIgnoreCase(String str)
    {
        for (String s : this)
        {
            if (str.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }
}
