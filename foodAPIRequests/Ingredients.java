public class Ingredients
{
    private String text;

    private Parsed[] parsed;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public Parsed[] getParsed ()
    {
        return parsed;
    }

    public void setParsed (Parsed[] parsed)
    {
        this.parsed = parsed;
    }

    @Override
    public String toString()
    {
        return "[text = "+text+", parsed = "+parsed+"]";
    }
}
