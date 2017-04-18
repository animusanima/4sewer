package animusanima.learning.android.stoffkiste.data;

import java.util.HashMap;

import animusanima.learning.android.stoffkiste.db.stoffe.StoffeContract;

public class CategoryHelper
{
    private static final HashMap<Integer, String> CATEGORY_MAP = new HashMap<>();

    static
    {
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_UNBEKANNT, "Unbekannt");
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_BAUMWOLLE, "Baumwolle");
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_JERSEY, "Jersey");
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_LEDER, "Leder");
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_JEANS, "Jeans");
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_SWEAT, "Sweat");
        CATEGORY_MAP.put(StoffeContract.KATEGORIE_INTERLOCK, "Interlock");
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