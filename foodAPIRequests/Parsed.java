public class Parsed
{
    private Nutrients nutrients;

    private String retainedWeight;

    private String foodId;

    private String weight;

    private String measure;

    private String food;

    private String quantity;

    private String foodMatch;

    public Nutrients getNutrients ()
    {
        return nutrients;
    }

    public void setNutrients (Nutrients nutrients)
    {
        this.nutrients = nutrients;
    }

    public String getRetainedWeight ()
    {
        return retainedWeight;
    }

    public void setRetainedWeight (String retainedWeight)
    {
        this.retainedWeight = retainedWeight;
    }

    public String getFoodId ()
    {
        return foodId;
    }

    public void setFoodId (String foodId)
    {
        this.foodId = foodId;
    }

    public String getWeight ()
    {
        return weight;
    }

    public void setWeight (String weight)
    {
        this.weight = weight;
    }

    public String getMeasure ()
    {
        return measure;
    }

    public void setMeasure (String measure)
    {
        this.measure = measure;
    }

    public String getFood ()
    {
        return food;
    }

    public void setFood (String food)
    {
        this.food = food;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    public String getFoodMatch ()
    {
        return foodMatch;
    }

    public void setFoodMatch (String foodMatch)
    {
        this.foodMatch = foodMatch;
    }

    @Override
    public String toString()
    {
        return "[nutrients = "+nutrients+", retainedWeight = "+retainedWeight+", foodId = "+foodId+", weight = "+weight+", measure = "+measure+", food = "+food+", quantity = "+quantity+", foodMatch = "+foodMatch+"]";
    }
}
