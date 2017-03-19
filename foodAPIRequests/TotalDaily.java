public class TotalDaily
{
    private FIBTG FIBTG;

    private ENERC_KCAL ENERC_KCAL;

    private FASAT FASAT;

    private FAT FAT;

    private CHOCDF CHOCDF;

    public FIBTG getFIBTG ()
    {
        return FIBTG;
    }

    public void setFIBTG (FIBTG FIBTG)
    {
        this.FIBTG = FIBTG;
    }

    public ENERC_KCAL getENERC_KCAL ()
    {
        return ENERC_KCAL;
    }

    public void setENERC_KCAL (ENERC_KCAL ENERC_KCAL)
    {
        this.ENERC_KCAL = ENERC_KCAL;
    }

    public FASAT getFASAT ()
    {
        return FASAT;
    }

    public void setFASAT (FASAT FASAT)
    {
        this.FASAT = FASAT;
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

    @Override
    public String toString()
    {
        return "[FIBTG = "+FIBTG+", ENERC_KCAL = "+ENERC_KCAL+", FASAT = "+FASAT+", FAT = "+FAT+", CHOCDF = "+CHOCDF+"]";
    }
}
