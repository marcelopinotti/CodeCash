package br.com.project.model;

public record Investment(
        long id,
        long tax,
        long initialFounds)
{
    @Override
    public String toString() {
        return "Investment{" +
                "id=" + id +
                ", tax=" + tax + "%"+
                ", initialFounds=" + (initialFounds/100) + "," + (initialFounds%100) +
                '}';
    }
}
