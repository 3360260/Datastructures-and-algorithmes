public class Book implements Comparable<Book>
{
    private String title;
    private String author;
    private double rating;
    private int pubDate;

    public Book(String title, String author, double rating, int pubDate)
    {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.pubDate = pubDate;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public int getPubDate()
    {
        return pubDate;
    }

    public void setPubDate(int pubDate)
    {
        this.pubDate = pubDate;
    }

    @Override
    public String toString()
    {
        return "Book{" +
                "title'" + title + '\'' +
                ", author='" + author + '\'' +
                ", ratin=" + rating +
                ", pubDate=" + pubDate +
                '}';
    }

    @Override
    public int compareTo(Book o)
    {
        return 0;
    }
}
