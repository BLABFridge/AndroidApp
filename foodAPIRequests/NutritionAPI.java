public class NutritionAPI 
{
    private Ingredients[] ingredients;

    private String[] dietLabels;

    private TotalDaily totalDaily;

    private String[] healthLabels;

    private String totalWeight;

    private String[] cautions;

    private TotalNutrients totalNutrients;

    private String calories;

    private String uri;

    public Ingredients[] getIngredients ()
    {
        return ingredients;
    }

    public void setIngredients (Ingredients[] ingredients)
    {
        this.ingredients = ingredients;
    }

    public String[] getDietLabels ()
    {
        return dietLabels;
    }

    public void setDietLabels (String[] dietLabels)
    {
        this.dietLabels = dietLabels;
    }

    public TotalDaily getTotalDaily ()
    {
        return totalDaily;
    }

    public void setTotalDaily (TotalDaily totalDaily)
    {
        this.totalDaily = totalDaily;
    }

    public String[] getHealthLabels ()
    {
        return healthLabels;
    }

    public void setHealthLabels (String[] healthLabels)
    {
        this.healthLabels = healthLabels;
    }

    public String getTotalWeight ()
    {
        return totalWeight;
    }

    public void setTotalWeight (String totalWeight)
    {
        this.totalWeight = totalWeight;
    }

    public String[] getCautions ()
    {
        return cautions;
    }

    public void setCautions (String[] cautions)
    {
        this.cautions = cautions;
    }

    public TotalNutrients getTotalNutrients ()
    {
        return totalNutrients;
    }

    public void setTotalNutrients (TotalNutrients totalNutrients)
    {
        this.totalNutrients = totalNutrients;
    }

    public String getCalories ()
    {
        return calories;
    }

    public void setCalories (String calories)
    {
        this.calories = calories;
    }

    public String getUri ()
    {
        return uri;
    }

    public void setUri (String uri)
    {
        this.uri = uri;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ingredients = "+ingredients+", dietLabels = "+dietLabels+", totalDaily = "+totalDaily+", healthLabels = "+healthLabels+", totalWeight = "+totalWeight+", cautions = "+cautions+", totalNutrients = "+totalNutrients+", calories = "+calories+", uri = "+uri+"]";
    }
}
			
			
