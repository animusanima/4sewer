package com.github.animusanima.a4sewer.data;

import java.util.HashMap;

import com.github.animusanima.a4sewer.db.stoffe.StoffeTableInformation;

public class CategoryHelper
{
    private static final HashMap<Integer, String> CATEGORY_MAP = new HashMap<>();

    static
    {
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_UNBEKANNT, "Unbekannt");
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_BAUMWOLLE, "Baumwolle");
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_JERSEY, "Jersey");
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_LEDER, "Leder");
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_JEANS, "Jeans");
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_SWEAT, "Sweat");
        CATEGORY_MAP.put(StoffeTableInformation.KATEGORIE_INTERLOCK, "Interlock");
    }

    public static String getCategoryByID(int ID)
    {
        return CATEGORY_MAP.get(ID);
    }

    public static int getIDByCategory(String category)
    {
        String value;
        for (Integer key : CATEGORY_MAP.keySet())
        {
            value = getCategoryByID(key);
            if (value.equals(category))
                return key.intValue();
        }
        return 0;
    }
}