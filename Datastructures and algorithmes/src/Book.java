class Book implements Comparable<Book> {
    private String title;
    private String author;
    private double rating;
    private String pubDate;

    public Book(String title, String author, double rating, String pubDate) {
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", rating=" + rating +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }

    @Override
    public int compareTo(Book o) {
        return this.title.compareTo(o.title);
    }
}
