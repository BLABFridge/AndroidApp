public class Nutrients
{
    private ENERC_KCAL ENERC_KCAL;

    private SUGAR SUGAR;

    private FAT FAT;

    private CHOCDF CHOCDF;

    private PROCNT PROCNT;

    public ENERC_KCAL getENERC_KCAL ()
    {
        return ENERC_KCAL;
    }

    public void setENERC_KCAL (ENERC_KCAL ENERC_KCAL)
    {
        this.ENERC_KCAL = ENERC_KCAL;
    }

    public SUGAR getSUGAR ()
    {
        return SUGAR;
    }

    public void setSUGAR (SUGAR SUGAR)
    {
        this.SUGAR = SUGAR;
    }

    public FAT getFAT ()
    {
        return FAT;
    }

    public void setFAT (FAT FAT)
    {
        this.FAT = FAT;
    }

    public CHOCDF getCHOCDF ()
    {
        return CHOCDF;
    }

    public void setCHOCDF (CHOCDF CHOCDF)
    {
        this.CHOCDF = CHOCDF;
    }

    public PROCNT getPROCNT ()
    {
        return PROCNT;
    }

    public void setPROCNT (PROCNT PROCNT)
    {
        this.PROCNT = PROCNT;
    }

    @Override
    public String toString()
    {
        return "NutrientsAPI [ENERC_KCAL = "+ENERC_KCAL+", SUGAR = "+SUGAR+", FAT = "+FAT+", CHOCDF = "+CHOCDF+", PROCNT = "+PROCNT+"]";
    }
}
